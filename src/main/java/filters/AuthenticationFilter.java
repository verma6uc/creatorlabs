package filters;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ConfigurationManager;
import utils.ResponseUtil;
import utils.SessionManager;

/**
 * Filter for handling authentication and session validation.
 * Protects routes that require authentication.
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final SessionManager sessionManager = SessionManager.getInstance();
    private static final ConfigurationManager config = ConfigurationManager.getInstance();
    
    // Paths that don't require authentication
    private static final Set<String> PUBLIC_PATHS = Set.of(
        "/login",
        "/register",
        "/forgot-password",
        "/reset-password",
        "/static",
        "/favicon.ico",
        "/error",
        "/health"
    );

    // API paths that use token authentication instead of session
    private static final Set<String> API_PATHS = Set.of(
        "/api/v1"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initializing Authentication Filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        try {
            // Skip authentication for public paths
            if (isPublicPath(path)) {
                chain.doFilter(request, response);
                return;
            }
            
            // Handle API authentication
            if (isApiPath(path)) {
                handleApiAuthentication(httpRequest, httpResponse, chain);
                return;
            }
            
            // Handle web authentication
            handleWebAuthentication(httpRequest, httpResponse, chain);
            
        } catch (Exception e) {
            logger.error("Authentication error", e);
            handleAuthenticationError(httpRequest, httpResponse);
        }
    }

    /**
     * Check if path is public
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    /**
     * Check if path is API
     */
    private boolean isApiPath(String path) {
        return API_PATHS.stream().anyMatch(path::startsWith);
    }

    /**
     * Handle API authentication
     */
    private void handleApiAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String apiKey = request.getHeader("X-API-Key");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            ResponseUtil.sendUnauthorized(response, "API key is required");
            return;
        }
        
        // Validate API key (implement your own validation logic)
        if (!isValidApiKey(apiKey)) {
            ResponseUtil.sendUnauthorized(response, "Invalid API key");
            return;
        }
        
        chain.doFilter(request, response);
    }

    /**
     * Handle web authentication
     */
    private void handleWebAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Check if user is authenticated
        if (!sessionManager.validateSession(request)) {
            // Store original request URL for redirect after login
            String originalUrl = request.getRequestURL().toString();
            if (request.getQueryString() != null) {
                originalUrl += "?" + request.getQueryString();
            }
            request.getSession().setAttribute("redirectUrl", originalUrl);
            
            // Redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // User is authenticated, proceed with request
        chain.doFilter(request, response);
    }

    /**
     * Handle authentication error
     */
    private void handleAuthenticationError(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        if (isApiPath(request.getRequestURI())) {
            ResponseUtil.sendServerError(response, "Authentication error occurred");
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    /**
     * Validate API key
     */
    private boolean isValidApiKey(String apiKey) {
        // Implement your API key validation logic here
        // This could involve checking against a database or configuration
        String validApiKey = config.getString("api.key", "");
        return !validApiKey.isEmpty() && validApiKey.equals(apiKey);
    }

    @Override
    public void destroy() {
        logger.info("Destroying Authentication Filter");
    }
}
