package filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter to add security headers to all responses.
 * These headers help protect against various web vulnerabilities.
 */
@WebFilter("/*")
public class SecurityHeadersFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityHeadersFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("SecurityHeadersFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        if (response instanceof HttpServletResponse httpResponse) {
            // Security Headers
            httpResponse.setHeader("X-Content-Type-Options", "nosniff");
            httpResponse.setHeader("X-Frame-Options", "DENY");
            httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
            httpResponse.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
            httpResponse.setHeader("Permissions-Policy", "geolocation=(), camera=(), microphone=()");
            
            // Content Security Policy
            StringBuilder cspBuilder = new StringBuilder();
            cspBuilder.append("default-src 'self'; ")
                     .append("script-src 'self' https://cdn.jsdelivr.net 'unsafe-inline' 'unsafe-eval'; ")
                     .append("style-src 'self' https://cdn.jsdelivr.net https://fonts.googleapis.com 'unsafe-inline'; ")
                     .append("img-src 'self' data: https:; ")
                     .append("font-src 'self' https://fonts.gstatic.com https://cdn.jsdelivr.net; ")
                     .append("connect-src 'self'; ")
                     .append("frame-ancestors 'none'; ")
                     .append("form-action 'self'; ")
                     .append("base-uri 'self'; ")
                     .append("object-src 'none'");
            
            httpResponse.setHeader("Content-Security-Policy", cspBuilder.toString());

            // HSTS - Enable only in production with proper SSL
            if (request.isSecure()) {
                httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
            }

            // Cache Control - Adjust based on content type
            String requestURI = request.getServletContext().getContextPath();
            if (requestURI.contains("/static/")) {
                // Static resources can be cached
                httpResponse.setHeader("Cache-Control", "public, max-age=31536000, immutable");
            } else {
                // Dynamic content should not be cached
                httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                httpResponse.setHeader("Pragma", "no-cache");
                httpResponse.setHeader("Expires", "0");
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("SecurityHeadersFilter destroyed");
    }
}
