package servlets;

import com.google.gson.JsonObject;
import utils.Constants.Onboarding;
import utils.ValidationUtil;
import utils.ValidationUtil.ValidationResult;
import utils.ResponseUtil;
import utils.SecurityUtil;
import utils.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet for handling the onboarding process
 */
@WebServlet("/onboarding/*")
public class OnboardingServlet extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(OnboardingServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!requireAuthentication(request, response)) {
            return;
        }

        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Show first step by default
            showOnboardingStep(1, request, response);
            return;
        }

        try {
            int step = Integer.parseInt(pathInfo.substring(1));
            ValidationResult validation = ValidationUtil.validateOnboardingStep(step);
            
            if (!validation.isValid()) {
                response.sendRedirect("/onboarding/1");
                return;
            }

            showOnboardingStep(step, request, response);
        } catch (NumberFormatException e) {
            sendNotFoundResponse(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!requireAuthentication(request, response)) {
            return;
        }

        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }

        try {
            int step = Integer.parseInt(pathInfo.substring(1));
            ValidationResult validation = ValidationUtil.validateOnboardingStep(step);
            
            if (!validation.isValid()) {
                sendValidationErrorResponse(response, validation);
                return;
            }

            processOnboardingStep(step, request, response);
        } catch (NumberFormatException e) {
            sendNotFoundResponse(response);
        }
    }

    private void showOnboardingStep(int step, HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userId = getAuthenticatedUserId(request);
        logger.info("Showing onboarding step {} for user {}", step, userId);

        switch (step) {
            case 1 -> {
                request.setAttribute("brandIdentities", Onboarding.BRAND_IDENTITIES);
                request.getRequestDispatcher("/WEB-INF/views/onboarding/step1.jsp").forward(request, response);
            }
            case 2 -> {
                request.setAttribute("developmentFocuses", Onboarding.DEVELOPMENT_FOCUSES);
                request.getRequestDispatcher("/WEB-INF/views/onboarding/step2.jsp").forward(request, response);
            }
            case 3 -> {
                request.setAttribute("teamCollaborations", Onboarding.TEAM_COLLABORATIONS);
                request.getRequestDispatcher("/WEB-INF/views/onboarding/step3.jsp").forward(request, response);
            }
            case 4 -> {
                request.setAttribute("aiCommunicationStyles", Onboarding.AI_COMMUNICATION_STYLES);
                request.getRequestDispatcher("/WEB-INF/views/onboarding/step4.jsp").forward(request, response);
            }
            default -> sendNotFoundResponse(response);
        }
    }

    private void processOnboardingStep(int step, HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String userId = getAuthenticatedUserId(request);
        JsonObject jsonResponse = new JsonObject();

        try {
            ValidationResult validation = switch (step) {
                case 1 -> validateBrandIdentity(request);
                case 2 -> validateDevelopmentFocus(request);
                case 3 -> validateTeamCollaboration(request);
                case 4 -> validateAiCommunication(request);
                default -> ValidationResult.failure("Invalid step");
            };

            if (!validation.isValid()) {
                sendValidationErrorResponse(response, validation);
                return;
            }

            saveOnboardingProgress(step, request);
            
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("nextStep", step < Onboarding.TOTAL_STEPS ? step + 1 : -1);
            
            logger.info("Successfully processed step {} for user {}", step, userId);
            sendSuccessResponse(response, jsonResponse);
        } catch (Exception e) {
            logger.error("Error processing onboarding step {} for user {}", step, userId, e);
            sendInternalErrorResponse(response);
        }
    }

    private ValidationResult validateBrandIdentity(HttpServletRequest request) {
        String brandIdentity = request.getParameter("brandIdentity");
        return ValidationUtil.validateOnboardingSelection(brandIdentity, Onboarding.BRAND_IDENTITIES);
    }

    private ValidationResult validateDevelopmentFocus(HttpServletRequest request) {
        String developmentFocus = request.getParameter("developmentFocus");
        return ValidationUtil.validateOnboardingSelection(developmentFocus, Onboarding.DEVELOPMENT_FOCUSES);
    }

    private ValidationResult validateTeamCollaboration(HttpServletRequest request) {
        String teamCollaboration = request.getParameter("teamCollaboration");
        return ValidationUtil.validateOnboardingSelection(teamCollaboration, Onboarding.TEAM_COLLABORATIONS);
    }

    private ValidationResult validateAiCommunication(HttpServletRequest request) {
        String aiCommunication = request.getParameter("aiCommunication");
        return ValidationUtil.validateOnboardingSelection(aiCommunication, Onboarding.AI_COMMUNICATION_STYLES);
    }

    private void saveOnboardingProgress(int step, HttpServletRequest request) {
        String userId = getAuthenticatedUserId(request);
        String selection = request.getParameter(getParameterName(step));
        
        // TODO: Implement actual database storage
        logger.info("Saving onboarding progress - Step: {}, User: {}, Selection: {}", 
            step, userId, selection);
    }

    private String getParameterName(int step) {
        return switch (step) {
            case 1 -> "brandIdentity";
            case 2 -> "developmentFocus";
            case 3 -> "teamCollaboration";
            case 4 -> "aiCommunication";
            default -> throw new IllegalArgumentException("Invalid step: " + step);
        };
    }
}
