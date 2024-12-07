package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.ResponseUtil;
import utils.SecurityUtil;
import utils.ValidationUtil.ValidationResult;

import java.io.IOException;
import java.util.List;

/**
 * Base servlet providing common functionality for all servlets
 */
public abstract class BaseServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);
    protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Record for flash messages
     */
    protected record FlashMessage(String type, String message) {}

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Set common headers
            response.setCharacterEncoding(Constants.App.ENCODING);
            super.service(request, response);
        } catch (Exception e) {
            logger.error("Unhandled error in servlet", e);
            sendInternalErrorResponse(response);
        }
    }

    /**
     * Send error response with a single message
     */
    protected void sendErrorResponse(HttpServletResponse response, int status, String message) 
            throws IOException {
        ResponseUtil.sendErrorResponse(response, status, message);
    }

    /**
     * Send error response with multiple error messages
     */
    protected void sendErrorResponse(HttpServletResponse response, int status, List<String> errors) 
            throws IOException {
        ResponseUtil.sendErrorResponse(response, status, errors);
    }

    /**
     * Send validation error response
     */
    protected void sendValidationErrorResponse(HttpServletResponse response, ValidationResult validationResult) 
            throws IOException {
        ResponseUtil.sendValidationErrorResponse(response, validationResult);
    }

    /**
     * Send success response
     */
    protected void sendSuccessResponse(HttpServletResponse response) throws IOException {
        ResponseUtil.sendSuccessResponse(response);
    }

    /**
     * Send success response with data
     */
    protected void sendSuccessResponse(HttpServletResponse response, Object data) throws IOException {
        ResponseUtil.sendSuccessResponse(response, data);
    }

    /**
     * Send unauthorized error response
     */
    protected void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        ResponseUtil.sendUnauthorizedResponse(response);
    }

    /**
     * Send forbidden error response
     */
    protected void sendForbiddenResponse(HttpServletResponse response) throws IOException {
        ResponseUtil.sendForbiddenResponse(response);
    }

    /**
     * Send not found error response
     */
    protected void sendNotFoundResponse(HttpServletResponse response) throws IOException {
        ResponseUtil.sendNotFoundResponse(response);
    }

    /**
     * Send internal server error response
     */
    protected void sendInternalErrorResponse(HttpServletResponse response) throws IOException {
        ResponseUtil.sendInternalErrorResponse(response);
    }

    /**
     * Get request body as string
     */
    protected String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Parse request body as JSON
     */
    protected <T> T parseRequestBody(HttpServletRequest request, Class<T> type) throws IOException {
        String body = getRequestBody(request);
        return gson.fromJson(body, type);
    }

    /**
     * Set flash message
     */
    protected void setFlashMessage(HttpServletRequest request, String type, String message) {
        request.getSession().setAttribute("flash", new FlashMessage(type, message));
    }

    /**
     * Get and remove flash message
     */
    protected FlashMessage getFlashMessage(HttpServletRequest request) {
        FlashMessage flash = (FlashMessage) request.getSession().getAttribute("flash");
        request.getSession().removeAttribute("flash");
        return flash;
    }

    /**
     * Check if user is authenticated
     */
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        return token != null && SecurityUtil.validateToken(token);
    }

    /**
     * Get authenticated user ID
     */
    protected String getAuthenticatedUserId(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        return SecurityUtil.getUserIdFromToken(token);
    }

    /**
     * Require authentication
     */
    protected boolean requireAuthentication(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        if (!isAuthenticated(request)) {
            sendUnauthorizedResponse(response);
            return false;
        }
        return true;
    }
}
