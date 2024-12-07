package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Session management utility for handling user sessions.
 * Provides methods for session creation, validation, and cleanup.
 */
public final class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final SessionManager INSTANCE = new SessionManager();
    private static final ConcurrentHashMap<String, SessionInfo> activeSessions = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleanupExecutor;

    // Cache of session IDs to user IDs for quick lookup
    private static final ConcurrentHashMap<String, String> sessionUserMap = new ConcurrentHashMap<>();

    /**
     * Session information record
     */
    private record SessionInfo(
        String userId,
        String token,
        Instant createdAt,
        Instant expiresAt,
        String ipAddress,
        String userAgent
    ) {}

    private SessionManager() {
        cleanupExecutor = Executors.newSingleThreadScheduledExecutor();
        initializeCleanupTask();
    }

    /**
     * Initialize session cleanup task
     */
    private void initializeCleanupTask() {
        cleanupExecutor.scheduleAtFixedRate(
            this::cleanupExpiredSessions,
            1,
            1,
            TimeUnit.HOURS
        );
    }

    /**
     * Create a new session
     */
    public String createSession(HttpServletRequest request, String userId) {
        String sessionToken = SecurityUtil.generateToken();
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        Instant now = Instant.now();
        Instant expiresAt = now.plus(Constants.App.SESSION_TIMEOUT);
        
        SessionInfo sessionInfo = new SessionInfo(
            userId,
            sessionToken,
            now,
            expiresAt,
            ipAddress,
            userAgent
        );
        
        try {
            persistSession(sessionInfo);
            activeSessions.put(sessionToken, sessionInfo);
            sessionUserMap.put(sessionToken, userId);
            
            // Set session attributes
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userId);
            session.setAttribute("sessionToken", sessionToken);
            session.setMaxInactiveInterval((int) Constants.App.SESSION_TIMEOUT.getSeconds());
            
            logger.info("Created new session for user: {}", userId);
            return sessionToken;
            
        } catch (SQLException e) {
            logger.error("Failed to persist session", e);
            throw new RuntimeException("Failed to create session", e);
        }
    }

    /**
     * Get user ID from session ID
     */
    public Optional<String> getUserIdFromSessionId(String sessionId) {
        return Optional.ofNullable(sessionUserMap.get(sessionId));
    }

    /**
     * Get session info
     */
    public Optional<SessionInfo> getSessionInfo(String sessionToken) {
        return Optional.ofNullable(activeSessions.get(sessionToken));
    }

    /**
     * Validate session
     */
    public boolean validateSession(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                return false;
            }
            
            String sessionToken = (String) session.getAttribute("sessionToken");
            if (sessionToken == null) {
                return false;
            }
            
            SessionInfo sessionInfo = activeSessions.get(sessionToken);
            if (sessionInfo == null) {
                return false;
            }
            
            // Check if session has expired
            if (Instant.now().isAfter(sessionInfo.expiresAt())) {
                invalidateSession(request);
                return false;
            }
            
            // Validate IP address and user agent for security
            String currentIp = getClientIpAddress(request);
            String currentUserAgent = request.getHeader("User-Agent");
            
            if (!sessionInfo.ipAddress().equals(currentIp) || 
                !sessionInfo.userAgent().equals(currentUserAgent)) {
                logger.warn("Session validation failed: IP or user agent mismatch");
                invalidateSession(request);
                return false;
            }
            
            return true;
            
        } catch (Exception e) {
            logger.error("Error validating session", e);
            return false;
        }
    }

    /**
     * Invalidate session
     */
    public void invalidateSession(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String sessionToken = (String) session.getAttribute("sessionToken");
                if (sessionToken != null) {
                    deleteSession(sessionToken);
                    activeSessions.remove(sessionToken);
                    sessionUserMap.remove(sessionToken);
                }
                session.invalidate();
            }
        } catch (Exception e) {
            logger.error("Error invalidating session", e);
        }
    }

    /**
     * Get user ID from session
     */
    public Optional<String> getUserIdFromSession(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String userId = (String) session.getAttribute("userId");
                return Optional.ofNullable(userId);
            }
        } catch (Exception e) {
            logger.error("Error getting user ID from session", e);
        }
        return Optional.empty();
    }

    /**
     * Cleanup expired sessions
     */
    private void cleanupExpiredSessions() {
        try {
            Instant now = Instant.now();
            activeSessions.entrySet().removeIf(entry -> {
                if (now.isAfter(entry.getValue().expiresAt())) {
                    try {
                        deleteSession(entry.getKey());
                        sessionUserMap.remove(entry.getKey());
                        return true;
                    } catch (SQLException e) {
                        logger.error("Error deleting expired session", e);
                    }
                }
                return false;
            });
        } catch (Exception e) {
            logger.error("Error cleaning up expired sessions", e);
        }
    }

    /**
     * Persist session to database
     */
    private void persistSession(SessionInfo sessionInfo) throws SQLException {
        String sql = """
            INSERT INTO sessions (id, user_id, token, created_at, expires_at, ip_address, user_agent)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, UUID.randomUUID());
            stmt.setString(2, sessionInfo.userId());
            stmt.setString(3, sessionInfo.token());
            stmt.setObject(4, sessionInfo.createdAt());
            stmt.setObject(5, sessionInfo.expiresAt());
            stmt.setString(6, sessionInfo.ipAddress());
            stmt.setString(7, sessionInfo.userAgent());
            
            stmt.executeUpdate();
        }
    }

    /**
     * Delete session from database
     */
    private void deleteSession(String token) throws SQLException {
        String sql = "DELETE FROM sessions WHERE token = ?";
        
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, token);
            stmt.executeUpdate();
        }
    }

    /**
     * Get client IP address
     */
    private String getClientIpAddress(HttpServletRequest request) {
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
                // If IP contains multiple addresses, take the first one
                return ip.split(",")[0].trim();
            }
        }
        
        return request.getRemoteAddr();
    }

    /**
     * Shutdown session manager
     */
    public void shutdown() {
        try {
            cleanupExecutor.shutdown();
            if (!cleanupExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                cleanupExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error shutting down session manager", e);
        }
    }

    /**
     * Get singleton instance
     */
    public static SessionManager getInstance() {
        return INSTANCE;
    }
}
