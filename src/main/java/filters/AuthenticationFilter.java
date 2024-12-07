package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.SecurityUtil;
import utils.SessionManager;
import utils.ResponseUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter to handle authentication for protected routes
 */
public class AuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/login",
        "/register",
        "/forgot-password",
        "/reset-password",
        "/static",
        "/favicon.ico"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initializing AuthenticationFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Allow public paths
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check for API requests
        boolean isApiRequest = path.startsWith("/api/");

        // Get session and token
        HttpSession session = httpRequest.getSession(false);
        String token = session != null ? (String) session.getAttribute("token") : null;

        // Validate session and token
        if (session == null || token == null || !SecurityUtil.validateToken(token)) {
            logger.debug("Invalid or missing session/token for path: {}", path);
            
            if (isApiRequest) {
                ResponseUtil.sendUnauthorizedResponse(httpResponse);
            } else {
                // Store the requested URL for redirect after login
                String requestedUrl = httpRequest.getRequestURL().toString();
                String queryString = httpRequest.getQueryString();
                if (queryString != null) {
                    requestedUrl += "?" + queryString;
                }
                
                session = httpRequest.getSession(true);
                session.setAttribute("requestedUrl", requestedUrl);
                
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
            return;
        }

        // Update session access time
        SessionManager.updateSessionAccess(token);

        // Add user information to request attributes
        String userId = SecurityUtil.getUserIdFromToken(token);
        httpRequest.setAttribute("userId", userId);
        httpRequest.setAttribute("token", token);

        // Check for session timeout
        if (session.getMaxInactiveInterval() > 0 && 
            (System.currentTimeMillis() - session.getLastAccessedTime()) > 
            (session.getMaxInactiveInterval() * 1000L)) {
            
            logger.debug("Session timed out for user: {}", userId);
            session.invalidate();
            
            if (isApiRequest) {
                ResponseUtil.sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, 
                    Constants.Messages.SESSION_EXPIRED);
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
            return;
        }

        // CSRF protection for state-changing requests
        if (isStateChangingRequest(httpRequest)) {
            String csrfToken = httpRequest.getHeader("X-CSRF-TOKEN");
            String sessionCsrfToken = (String) session.getAttribute("csrfToken");

            if (csrfToken == null || !csrfToken.equals(sessionCsrfToken)) {
                logger.warn("CSRF token validation failed for user: {}", userId);
                
                if (isApiRequest) {
                    ResponseUtil.sendErrorResponse(httpResponse, HttpServletResponse.SC_FORBIDDEN, 
                        "Invalid CSRF token");
                } else {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                }
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Destroying AuthenticationFilter");
    }

    /**
     * Check if the path is public
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    /**
     * Check if the request is state-changing (non-GET)
     */
    private boolean isStateChangingRequest(HttpServletRequest request) {
        String method = request.getMethod();
        return !method.equals("GET") && !method.equals("HEAD") && !method.equals("OPTIONS");
    }
}
