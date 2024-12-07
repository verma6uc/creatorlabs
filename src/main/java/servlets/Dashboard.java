package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.DatabaseUtils;
import utils.ResponseUtil;

/**
 * Servlet for handling dashboard functionality.
 * Displays user-specific dashboard data and manages dashboard interactions.
 */
@WebServlet("/dashboard/*")
public class Dashboard extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userId = getCurrentUserId(request)
            .orElseThrow(() -> new IllegalStateException("User not authenticated"));
        
        // Check if onboarding is complete
        Boolean onboardingComplete = (Boolean) request.getSession()
            .getAttribute("onboardingComplete");
        
        if (onboardingComplete == null || !onboardingComplete) {
            logger.debug("User {} has not completed onboarding", userId);
            response.sendRedirect(request.getContextPath() + "/onboarding");
            return;
        }

        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                // Main dashboard view
                handleMainDashboard(request, response, userId);
            } else {
                // Handle specific dashboard sections
                switch (pathInfo) {
                    case "/stats":
                        handleStats(request, response, userId);
                        break;
                    case "/projects":
                        handleProjects(request, response, userId);
                        break;
                    case "/settings":
                        handleSettings(request, response, userId);
                        break;
                    default:
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            handleException(request, response, e, "Error loading dashboard");
        }
    }

    private void handleMainDashboard(HttpServletRequest request, HttpServletResponse response, 
            String userId) throws ServletException, IOException {
        try {
            Map<String, Object> dashboardData = new HashMap<>();
            
            // Get user preferences
            dashboardData.put("preferences", getUserPreferences(userId));
            
            // Get recent activity
            dashboardData.put("recentActivity", getRecentActivity(userId));
            
            // Get project summary
            dashboardData.put("projectSummary", getProjectSummary(userId));
            
            if (isApiRequest(request)) {
                ResponseUtil.sendSuccess(response, dashboardData);
            } else {
                request.setAttribute("dashboardData", dashboardData);
                forwardToView(request, response, "dashboard/index");
            }
            
        } catch (SQLException e) {
            handleException(request, response, e, "Error loading dashboard data");
        }
    }

    private void handleStats(HttpServletRequest request, HttpServletResponse response, 
            String userId) throws IOException {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalProjects", getTotalProjects(userId));
            stats.put("activeProjects", getActiveProjects(userId));
            stats.put("completedProjects", getCompletedProjects(userId));
            
            ResponseUtil.sendSuccess(response, stats);
        } catch (SQLException e) {
            handleException(request, response, e, "Error loading statistics");
        }
    }

    private void handleProjects(HttpServletRequest request, HttpServletResponse response, 
            String userId) throws IOException {
        try {
            List<Map<String, Object>> projects = getProjects(userId);
            ResponseUtil.sendSuccess(response, projects);
        } catch (SQLException e) {
            handleException(request, response, e, "Error loading projects");
        }
    }

    private void handleSettings(HttpServletRequest request, HttpServletResponse response, 
            String userId) throws ServletException, IOException {
        try {
            Map<String, Object> settings = getUserSettings(userId);
            
            if (isApiRequest(request)) {
                ResponseUtil.sendSuccess(response, settings);
            } else {
                request.setAttribute("settings", settings);
                forwardToView(request, response, "dashboard/settings");
            }
        } catch (SQLException e) {
            handleException(request, response, e, "Error loading settings");
        }
    }

    private Map<String, Object> getUserPreferences(String userId) throws SQLException {
        String sql = """
            SELECT brand_identity, development_focus, team_collaboration, ai_communication_style 
            FROM onboarding_data 
            WHERE user_id = ?
            """;
            
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                Map<String, Object> preferences = new HashMap<>();
                if (rs.next()) {
                    preferences.put("brandIdentity", rs.getString("brand_identity"));
                    preferences.put("developmentFocus", rs.getString("development_focus"));
                    preferences.put("teamCollaboration", rs.getString("team_collaboration"));
                    preferences.put("aiCommunicationStyle", rs.getString("ai_communication_style"));
                }
                return preferences;
            }
        }
    }

    private List<Map<String, Object>> getRecentActivity(String userId) throws SQLException {
        String sql = """
            SELECT action, entity_type, entity_id, created_at, details 
            FROM audit_logs 
            WHERE user_id = ? 
            ORDER BY created_at DESC 
            LIMIT 10
            """;
            
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Map<String, Object>> activities = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> activity = new HashMap<>();
                    activity.put("action", rs.getString("action"));
                    activity.put("entityType", rs.getString("entity_type"));
                    activity.put("entityId", rs.getString("entity_id"));
                    activity.put("createdAt", rs.getTimestamp("created_at"));
                    activity.put("details", gson.fromJson(rs.getString("details"), JsonObject.class));
                    activities.add(activity);
                }
                return activities;
            }
        }
    }

    private Map<String, Object> getProjectSummary(String userId) throws SQLException {
        String sql = """
            SELECT status, COUNT(*) as count 
            FROM projects 
            WHERE user_id = ? 
            GROUP BY status
            """;
            
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                Map<String, Object> summary = new HashMap<>();
                while (rs.next()) {
                    summary.put(rs.getString("status").toLowerCase(), rs.getInt("count"));
                }
                return summary;
            }
        }
    }

    private int getTotalProjects(String userId) throws SQLException {
        return getProjectCount(userId, null);
    }

    private int getActiveProjects(String userId) throws SQLException {
        return getProjectCount(userId, "ACTIVE");
    }

    private int getCompletedProjects(String userId) throws SQLException {
        return getProjectCount(userId, "COMPLETED");
    }

    private int getProjectCount(String userId, String status) throws SQLException {
        String sql = status == null 
            ? "SELECT COUNT(*) FROM projects WHERE user_id = ?"
            : "SELECT COUNT(*) FROM projects WHERE user_id = ? AND status = ?";
            
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            if (status != null) {
                stmt.setString(2, status);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    private List<Map<String, Object>> getProjects(String userId) throws SQLException {
        String sql = """
            SELECT id, name, description, status, created_at, updated_at 
            FROM projects 
            WHERE user_id = ? 
            ORDER BY updated_at DESC
            """;
            
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Map<String, Object>> projects = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> project = new HashMap<>();
                    project.put("id", rs.getString("id"));
                    project.put("name", rs.getString("name"));
                    project.put("description", rs.getString("description"));
                    project.put("status", rs.getString("status"));
                    project.put("createdAt", rs.getTimestamp("created_at"));
                    project.put("updatedAt", rs.getTimestamp("updated_at"));
                    projects.add(project);
                }
                return projects;
            }
        }
    }

    private Map<String, Object> getUserSettings(String userId) throws SQLException {
        String sql = """
            SELECT u.email, u.full_name, o.* 
            FROM users u 
            LEFT JOIN onboarding_data o ON u.id = o.user_id 
            WHERE u.id = ?
            """;
            
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                Map<String, Object> settings = new HashMap<>();
                if (rs.next()) {
                    settings.put("email", rs.getString("email"));
                    settings.put("fullName", rs.getString("full_name"));
                    settings.put("brandIdentity", rs.getString("brand_identity"));
                    settings.put("developmentFocus", rs.getString("development_focus"));
                    settings.put("teamCollaboration", rs.getString("team_collaboration"));
                    settings.put("aiCommunicationStyle", rs.getString("ai_communication_style"));
                }
                return settings;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle POST requests for dashboard actions
        doGet(request, response);
    }
}
