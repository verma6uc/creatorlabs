package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for handling HTTP responses.
 * Provides standardized methods for sending JSON responses.
 */
public final class ResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
    private static final Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .setPrettyPrinting()
        .create();

    private ResponseUtil() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Send a success response with data
     */
    public static void sendSuccess(HttpServletResponse response, Object data) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", true);
        responseMap.put("data", data);
        
        sendJsonResponse(response, HttpServletResponse.SC_OK, responseMap);
    }

    /**
     * Send a success response with message
     */
    public static void sendSuccess(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", true);
        responseMap.put("message", message);
        
        sendJsonResponse(response, HttpServletResponse.SC_OK, responseMap);
    }

    /**
     * Send an error response
     */
    public static void sendError(HttpServletResponse response, int status, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("error", message);
        
        sendJsonResponse(response, status, responseMap);
    }

    /**
     * Send a validation error response
     */
    public static void sendValidationError(HttpServletResponse response, Map<String, String> errors) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("error", "Validation failed");
        responseMap.put("validationErrors", errors);
        
        sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, responseMap);
    }

    /**
     * Send an unauthorized error response
     */
    public static void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("error", message);
        
        sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, responseMap);
    }

    /**
     * Send a forbidden error response
     */
    public static void sendForbidden(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("error", message);
        
        sendJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, responseMap);
    }

    /**
     * Send a not found error response
     */
    public static void sendNotFound(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("error", message);
        
        sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, responseMap);
    }

    /**
     * Send a conflict error response
     */
    public static void sendConflict(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("error", message);
        
        sendJsonResponse(response, HttpServletResponse.SC_CONFLICT, responseMap);
    }

    /**
     * Send a server error response
     */
    public static void sendServerError(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("error", message);
        
        sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, responseMap);
    }

    /**
     * Send a paginated response
     */
    public static void sendPaginatedResponse(HttpServletResponse response, 
            Object data, int page, int totalPages, long totalItems) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", true);
        responseMap.put("data", data);
        responseMap.put("pagination", Map.of(
            "page", page,
            "totalPages", totalPages,
            "totalItems", totalItems
        ));
        
        sendJsonResponse(response, HttpServletResponse.SC_OK, responseMap);
    }

    /**
     * Send a custom JSON response
     */
    public static void sendJsonResponse(HttpServletResponse response, int status, Object data) throws IOException {
        try {
            response.setStatus(status);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            String jsonResponse = gson.toJson(data);
            response.getWriter().write(jsonResponse);
            
            logger.debug("Sent response with status {}: {}", status, jsonResponse);
        } catch (Exception e) {
            logger.error("Error sending JSON response", e);
            throw e;
        }
    }

    /**
     * Send a file download response
     */
    public static void sendFileDownload(HttpServletResponse response, 
            byte[] fileData, String fileName, String contentType) throws IOException {
        try {
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength(fileData.length);
            
            response.getOutputStream().write(fileData);
            response.getOutputStream().flush();
            
            logger.debug("Sent file download response for: {}", fileName);
        } catch (Exception e) {
            logger.error("Error sending file download response", e);
            throw e;
        }
    }

    /**
     * Send a no content response
     */
    public static void sendNoContent(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * Send a redirect response
     */
    public static void sendRedirect(HttpServletResponse response, String location) throws IOException {
        response.sendRedirect(location);
        logger.debug("Sent redirect to: {}", location);
    }
}
