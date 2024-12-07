package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Constants.Onboarding;
import utils.DatabaseUtils;
import utils.ResponseUtil;
import utils.ValidationUtil.ValidationResult;

/**
 * Servlet for handling the onboarding process.
 * Manages user onboarding flow and data persistence.
 */
@WebServlet("/onboarding/*")
public class OnboardingServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        logRequest(request);
        
        if (!validateCsrfToken(request)) {
            handleException(request, response, 
                new SecurityException("Invalid CSRF token"), 
                "Security validation failed");
            return;
        }

        String pathInfo = request.getPathInfo();
        try {
            switch (pathInfo) {
                case "/save-step":
                    handleStepSave(request, response);
                    break;
                case "/validate-step":
                    handleStepValidation(request, response);
                    break;
                case "/complete":
                    handleOnboardingComplete(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            handleException(request, response, e, "Error processing onboarding request");
        }
    }

    private void handleStepSave(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String step = getRequiredParameter(request, "step");
        String data = getRequiredParameter(request, "data");
        
        ValidationResult validation = validateStepData(step, data);
        if (!validation.isValid()) {
            handleValidationErrors(request, response, validation);
            return;
        }

        // Save step data to session
        String userId = getCurrentUserId(request)
            .orElseThrow(() -> new IllegalStateException("User not authenticated"));
        
        request.getSession().setAttribute("step_" + step, data);
        
        // Update current step
        int currentStep = Optional.ofNullable(request.getSession().getAttribute("currentStep"))
            .map(obj -> (Integer) obj)
            .orElse(1);
        
        int nextStep = Integer.parseInt(step) + 1;
        request.getSession().setAttribute("currentStep", nextStep);
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", true);
        jsonResponse.addProperty("nextStep", nextStep);
        
        ResponseUtil.sendSuccess(response, jsonResponse);
        
        logger.info("Saved onboarding step {} for user {}", step, userId);
    }

    private void handleStepValidation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String step = getRequiredParameter(request, "step");
        String data = getRequiredParameter(request, "data");
        
        ValidationResult validation = validateStepData(step, data);
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("valid", validation.isValid());
        
        if (!validation.isValid()) {
            JsonObject errors = new JsonObject();
            validation.getErrors().forEach(errors::addProperty);
            jsonResponse.add("errors", errors);
        }
        
        ResponseUtil.sendSuccess(response, jsonResponse);
    }

    private void handleOnboardingComplete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userId = getCurrentUserId(request)
            .orElseThrow(() -> new IllegalStateException("User not authenticated"));
        
        // Validate all required steps are complete
        ValidationResult validation = validateAllSteps(request);
        if (!validation.isValid()) {
            handleValidationErrors(request, response, validation);
            return;
        }

        // Save onboarding data to database
        try {
            saveOnboardingData(request, userId);
            request.getSession().setAttribute("onboardingComplete", true);
            
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("redirect", "/dashboard");
            
            ResponseUtil.sendSuccess(response, jsonResponse);
            
            logger.info("Completed onboarding for user {}", userId);
        } catch (Exception e) {
            logger.error("Failed to save onboarding data for user {}", userId, e);
            handleException(request, response, e, "Failed to complete onboarding");
        }
    }

    private ValidationResult validateStepData(String step, String data) {
        ValidationResult result = new ValidationResult();
        
        switch (step) {
            case "1": // Brand Identity
                if (!Onboarding.BRAND_IDENTITIES.contains(data.toUpperCase())) {
                    result.addError("brandIdentity", "Invalid brand identity selection");
                }
                break;
            
            case "2": // Workspace Setup
                JsonObject workspaceData = gson.fromJson(data, JsonObject.class);
                String devFocus = workspaceData.get("developmentFocus").getAsString();
                String teamCollab = workspaceData.get("teamCollaboration").getAsString();
                
                if (!Onboarding.DEVELOPMENT_FOCUSES.contains(devFocus.toUpperCase())) {
                    result.addError("developmentFocus", "Invalid development focus");
                }
                if (!Onboarding.TEAM_COLLABORATIONS.contains(teamCollab.toUpperCase())) {
                    result.addError("teamCollaboration", "Invalid team collaboration style");
                }
                break;
            
            case "3": // AI Communication
                if (!Onboarding.AI_COMMUNICATION_STYLES.contains(data.toUpperCase())) {
                    result.addError("communicationStyle", "Invalid communication style");
                }
                break;
            
            default:
                result.addError("step", "Invalid step number");
        }
        
        return result;
    }

    private ValidationResult validateAllSteps(HttpServletRequest request) {
        ValidationResult result = new ValidationResult();
        
        for (int i = 1; i <= Onboarding.TOTAL_STEPS; i++) {
            String stepData = (String) request.getSession().getAttribute("step_" + i);
            if (stepData == null) {
                result.addError("step_" + i, "Step " + i + " is incomplete");
            }
        }
        
        return result;
    }

    private void saveOnboardingData(HttpServletRequest request, String userId) throws SQLException {
        Map<String, String> onboardingData = new HashMap<>();
        for (int i = 1; i <= Onboarding.TOTAL_STEPS; i++) {
            String stepData = (String) request.getSession().getAttribute("step_" + i);
            onboardingData.put("step_" + i, stepData);
        }
        
        // Save to database using DatabaseUtils
        String sql = """
            INSERT INTO onboarding_data (
                id, user_id, brand_identity, development_focus, 
                team_collaboration, ai_communication_style
            ) VALUES (?, ?, ?, ?, ?, ?)
            """;
        
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, UUID.randomUUID());
            stmt.setString(2, userId);
            stmt.setString(3, onboardingData.get("step_1"));
            
            JsonObject workspaceData = gson.fromJson(onboardingData.get("step_2"), JsonObject.class);
            stmt.setString(4, workspaceData.get("developmentFocus").getAsString());
            stmt.setString(5, workspaceData.get("teamCollaboration").getAsString());
            
            stmt.setString(6, onboardingData.get("step_3"));
            
            stmt.executeUpdate();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!isAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Return current onboarding state
        int currentStep = Optional.ofNullable(request.getSession().getAttribute("currentStep"))
            .map(obj -> (Integer) obj)
            .orElse(1);
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("currentStep", currentStep);
        
        // Add saved data for the current step if it exists
        String stepData = (String) request.getSession().getAttribute("step_" + currentStep);
        if (stepData != null) {
            jsonResponse.add("stepData", gson.fromJson(stepData, JsonObject.class));
        }
        
        ResponseUtil.sendSuccess(response, jsonResponse);
    }
}
