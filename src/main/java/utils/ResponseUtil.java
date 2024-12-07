package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Utility class for handling HTTP responses
 */
public final class ResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private ResponseUtil() {
        throw new AssertionError("ResponseUtil class should not be instantiated");
    }

    /**
     * Send JSON response
     */
    public static void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        sendJsonResponse(response, HttpServletResponse.SC_OK, data);
    }

    /**
     * Send JSON response with status code
     */
    public static void sendJsonResponse(HttpServletResponse response, int status, Object data) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding(Constants.App.ENCODING);
        response.getWriter().write(gson.toJson(data));
    }

    /**
     * Send error response
     */
    public static void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        JsonObject error = new JsonObject();
        error.addProperty("success", false);
        error.addProperty("error", message);
        sendJsonResponse(response, status, error);
    }

    /**
     * Send error response with multiple error messages
     */
    public static void sendErrorResponse(HttpServletResponse response, int status, List<String> errors) throws IOException {
        JsonObject error = new JsonObject();
        error.addProperty("success", false);
        error.add("errors", gson.toJsonTree(errors));
        sendJsonResponse(response, status, error);
    }

    /**
     * Send success response
     */
    public static void sendSuccessResponse(HttpServletResponse response) throws IOException {
        sendSuccessResponse(response, null);
    }

    /**
     * Send success response with data
     */
    public static void sendSuccessResponse(HttpServletResponse response, Object data) throws IOException {
        JsonObject success = new JsonObject();
        success.addProperty("success", true);
        if (data != null) {
            success.add("data", gson.toJsonTree(data));
        }
        sendJsonResponse(response, success);
    }

    /**
     * Send success response with message
     */
    public static void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        JsonObject success = new JsonObject();
        success.addProperty("success", true);
        success.addProperty("message", message);
        sendJsonResponse(response, success);
    }

    /**
     * Send validation error response
     */
    public static void sendValidationErrorResponse(HttpServletResponse response, 
            ValidationUtil.ValidationResult validationResult) throws IOException {
        sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, validationResult.errors());
    }

    /**
     * Send unauthorized error response
     */
    public static void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, Constants.Messages.UNAUTHORIZED);
    }

    /**
     * Send forbidden error response
     */
    public static void sendForbiddenResponse(HttpServletResponse response) throws IOException {
        sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied");
    }

    /**
     * Send not found error response
     */
    public static void sendNotFoundResponse(HttpServletResponse response) throws IOException {
        sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Resource not found");
    }

    /**
     * Send internal server error response
     */
    public static void sendInternalErrorResponse(HttpServletResponse response) throws IOException {
        sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
            Constants.Messages.INTERNAL_ERROR);
    }

    /**
     * Send redirect response
     */
    public static void sendRedirectResponse(HttpServletResponse response, String location) throws IOException {
        response.sendRedirect(location);
    }

    /**
     * Send file download response
     */
    public static void sendFileResponse(HttpServletResponse response, String filename, String contentType, 
            byte[] data) throws IOException {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }
}
