package servlets;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.DatabaseUtils;
import utils.ResponseUtil;
import utils.SecurityUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Servlet for handling dashboard requests
 */
@WebServlet("/dashboard/*")
public class Dashboard extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(Dashboard.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!requireAuthentication(request, response)) {
            return;
        }

        String userId = getAuthenticatedUserId(request);
        String path = request.getPathInfo();

        try {
            if (path == null || path.equals("/")) {
                handleDashboardHome(request, response);
            } else if (path.equals("/stats")) {
                handleStats(request, response);
            } else if (path.equals("/activities")) {
                handleActivities(request, response);
            } else if (path.equals("/settings")) {
                handleSettings(request, response);
            } else {
                sendNotFoundResponse(response);
            }
        } catch (Exception e) {
            logger.error("Error handling dashboard request", e);
            sendInternalErrorResponse(response);
        }
    }

    /**
     * Handle dashboard home page
     */
    private void handleDashboardHome(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userId = getAuthenticatedUserId(request);
        
        try {
            Map<String, Object> dashboardData = getDashboardData(userId);
            
            if (request.getHeader("X-Requested-With") != null) {
                sendSuccessResponse(response, dashboardData);
            } else {
                request.setAttribute("dashboardData", dashboardData);
                request.getRequestDispatcher(Constants.View.DASHBOARD).forward(request, response);
            }
        } catch (SQLException e) {
            logger.error("Error fetching dashboard data", e);
            sendInternalErrorResponse(response);
        }
    }

    /**
     * Handle statistics request
     */
    private void handleStats(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userId = getAuthenticatedUserId(request);
        
        try {
            List<Map<String, Object>> stats = getStats(userId);
            sendSuccessResponse(response, stats);
        } catch (SQLException e) {
            logger.error("Error fetching stats", e);
            sendInternalErrorResponse(response);
        }
    }

    /**
     * Handle activities request
     */
    private void handleActivities(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userId = getAuthenticatedUserId(request);
        
        try {
            Map<String, Object> activities = getActivities(userId);
            sendSuccessResponse(response, activities);
        } catch (SQLException e) {
            logger.error("Error fetching activities", e);
            sendInternalErrorResponse(response);
        }
    }

    /**
     * Handle settings request
     */
    private void handleSettings(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userId = getAuthenticatedUserId(request);
        
        try {
            Map<String, Object> settings = getSettings(userId);
            
            if (request.getHeader("X-Requested-With") != null) {
                sendSuccessResponse(response, settings);
            } else {
                request.setAttribute("settings", settings);
                request.getRequestDispatcher(Constants.View.SETTINGS).forward(request, response);
            }
        } catch (SQLException e) {
            logger.error("Error fetching settings", e);
            sendInternalErrorResponse(response);
        }
    }

    /**
     * Get dashboard data for user
     */
    private Map<String, Object> getDashboardData(String userId) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        
        try (Connection conn = DatabaseUtils.getConnection()) {
            // Add user info
            String userSql = "SELECT username, email, first_name, last_name FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(userSql)) {
                stmt.setObject(1, UUID.fromString(userId));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        data.put("username", rs.getString("username"));
                        data.put("email", rs.getString("email"));
                        data.put("firstName", rs.getString("first_name"));
                        data.put("lastName", rs.getString("last_name"));
                    }
                }
            }

            // Add preferences
            String prefSql = "SELECT * FROM user_preferences WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(prefSql)) {
                stmt.setObject(1, UUID.fromString(userId));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Map<String, Object> preferences = new HashMap<>();
                        preferences.put("brandIdentity", rs.getString("brand_identity"));
                        preferences.put("developmentFocus", rs.getString("development_focus"));
                        preferences.put("teamCollaboration", rs.getString("team_collaboration"));
                        preferences.put("aiCommunicationStyle", rs.getString("ai_communication_style"));
                        preferences.put("theme", rs.getString("theme"));
                        preferences.put("notificationsEnabled", rs.getBoolean("notifications_enabled"));
                        data.put("preferences", preferences);
                    }
                }
            }
        }

        return data;
    }

    /**
     * Get user statistics
     */
    private List<Map<String, Object>> getStats(String userId) throws SQLException {
        List<Map<String, Object>> stats = new ArrayList<>();
        // TODO: Implement statistics retrieval
        return stats;
    }

    /**
     * Get user activities
     */
    private Map<String, Object> getActivities(String userId) throws SQLException {
        Map<String, Object> activities = new HashMap<>();
        
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = """
                SELECT action, entity_type, created_at 
                FROM audit_log 
                WHERE user_id = ? 
                ORDER BY created_at DESC 
                LIMIT 10
            """;
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setObject(1, UUID.fromString(userId));
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Map<String, Object>> activityList = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, Object> activity = new HashMap<>();
                        activity.put("action", rs.getString("action"));
                        activity.put("entityType", rs.getString("entity_type"));
                        activity.put("createdAt", rs.getTimestamp("created_at").toInstant());
                        activityList.add(activity);
                    }
                    activities.put("activities", activityList);
                }
            }
        }

        return activities;
    }

    /**
     * Get user settings
     */
    private Map<String, Object> getSettings(String userId) throws SQLException {
        Map<String, Object> settings = new HashMap<>();
        
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM user_preferences WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setObject(1, UUID.fromString(userId));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        settings.put("theme", rs.getString("theme"));
                        settings.put("notificationsEnabled", rs.getBoolean("notifications_enabled"));
                        settings.put("brandIdentity", rs.getString("brand_identity"));
                        settings.put("developmentFocus", rs.getString("development_focus"));
                        settings.put("teamCollaboration", rs.getString("team_collaboration"));
                        settings.put("aiCommunicationStyle", rs.getString("ai_communication_style"));
                    }
                }
            }
        }

        return settings;
    }
}
