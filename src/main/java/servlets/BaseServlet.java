package servlets;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ConfigurationManager;
import utils.ResponseUtil;
import utils.SecurityUtil;
import utils.SessionManager;
import utils.ValidationUtil.ValidationResult;

/**
 * Base servlet class providing common functionality for all servlets.
 * Implements common patterns and utilities for consistent behavior.
 */
public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .setPrettyPrinting()
        .create();
    
    protected final ConfigurationManager config = ConfigurationManager.getInstance();
    protected final SessionManager sessionManager = SessionManager.getInstance();

    /**
     * Get current user ID from session
     */
    protected Optional<String> getCurrentUserId(HttpServletRequest request) {
        return sessionManager.getUserIdFromSession(request);
    }

    /**
     * Check if user is authenticated
     */
    protected boolean isAuthenticated(HttpServletRequest request) {
        return sessionManager.validateSession(request);
    }

    /**
     * Get request parameter with validation
     */
    protected Optional<String> getValidParameter(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value != null && !value.trim().isEmpty()) {
            return Optional.of(SecurityUtil.sanitizeInput(value.trim()));
        }
        return Optional.empty();
    }

    /**
     * Get required parameter or throw exception
     */
    protected String getRequiredParameter(HttpServletRequest request, String paramName) 
            throws ServletException {
        return getValidParameter(request, paramName)
            .orElseThrow(() -> new ServletException("Required parameter missing: " + paramName));
    }

    /**
     * Parse JSON request body
     */
    protected <T> T parseJsonRequest(HttpServletRequest request, Class<T> type) 
            throws IOException {
        String body = request.getReader().lines()
            .reduce("", (accumulator, actual) -> accumulator + actual);
        return gson.fromJson(body, type);
    }

    /**
     * Validate request parameters
     */
    protected ValidationResult validateParameters(HttpServletRequest request, Map<String, String> requiredParams) {
        ValidationResult result = new ValidationResult();
        
        for (Map.Entry<String, String> entry : requiredParams.entrySet()) {
            String value = request.getParameter(entry.getKey());
            if (value == null || value.trim().isEmpty()) {
                result.addError(entry.getKey(), entry.getValue());
            }
        }
        
        return result;
    }

    /**
     * Set flash message in session
     */
    protected void setFlashMessage(HttpServletRequest request, String message, String type) {
        HttpSession session = request.getSession();
        session.setAttribute("flashMessage", message);
        session.setAttribute("flashType", type);
    }

    /**
     * Get and clear flash message from session
     */
    protected Optional<FlashMessage> getFlashMessage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("flashMessage");
        String type = (String) session.getAttribute("flashType");
        
        if (message != null && type != null) {
            session.removeAttribute("flashMessage");
            session.removeAttribute("flashType");
            return Optional.of(new FlashMessage(message, type));
        }
        
        return Optional.empty();
    }

    /**
     * Record for flash messages
     */
    protected record FlashMessage(String message, String type) {}

    /**
     * Forward to JSP view
     */
    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, 
            String viewPath) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + viewPath + ".jsp")
            .forward(request, response);
    }

    /**
     * Redirect with message
     */
    protected void redirectWithMessage(HttpServletRequest request, HttpServletResponse response,
            String path, String message, String type) throws IOException {
        setFlashMessage(request, message, type);
        response.sendRedirect(request.getContextPath() + path);
    }

    /**
     * Handle validation errors
     */
    protected void handleValidationErrors(HttpServletRequest request, HttpServletResponse response,
            ValidationResult validationResult) throws IOException {
        if (isApiRequest(request)) {
            ResponseUtil.sendValidationError(response, validationResult.getErrors());
        } else {
            setFlashMessage(request, "Please correct the errors below", "error");
            request.setAttribute("errors", validationResult.getErrors());
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     * Check if request is API request
     */
    protected boolean isApiRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String contentType = request.getHeader("Content-Type");
        
        return (accept != null && accept.contains("application/json")) ||
               (contentType != null && contentType.contains("application/json")) ||
               request.getRequestURI().startsWith(request.getContextPath() + "/api/");
    }

    /**
     * Get client IP address
     */
    protected String getClientIpAddress(HttpServletRequest request) {
        String[] headers = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
        };
        
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim();
            }
        }
        
        return request.getRemoteAddr();
    }

    /**
     * Log request details
     */
    protected void logRequest(HttpServletRequest request) {
        logger.debug("Request: {} {} from IP: {}", 
            request.getMethod(),
            request.getRequestURI(),
            getClientIpAddress(request)
        );
    }

    /**
     * Handle exceptions consistently
     */
    protected void handleException(HttpServletRequest request, HttpServletResponse response,
            Exception e, String userMessage) throws IOException {
        logger.error("Error processing request: {}", e.getMessage(), e);
        
        if (isApiRequest(request)) {
            ResponseUtil.sendServerError(response, userMessage);
        } else {
            redirectWithMessage(request, response, "/error", userMessage, "error");
        }
    }

    /**
     * Generate CSRF token
     */
    protected String generateCsrfToken(HttpServletRequest request) {
        String token = SecurityUtil.generateCsrfToken();
        request.getSession().setAttribute("csrfToken", token);
        return token;
    }

    /**
     * Validate CSRF token
     */
    protected boolean validateCsrfToken(HttpServletRequest request) {
        String sessionToken = (String) request.getSession().getAttribute("csrfToken");
        String requestToken = request.getParameter("_csrf");
        
        if (sessionToken == null || requestToken == null) {
            return false;
        }
        
        return sessionToken.equals(requestToken);
    }
}
