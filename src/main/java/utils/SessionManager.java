package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages user sessions and their associated data
 */
public final class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final Map<String, SessionInfo> activeSessions = new ConcurrentHashMap<>();

    private SessionManager() {
        throw new AssertionError("SessionManager class should not be instantiated");
    }

    /**
     * Session information record
     */
    public record SessionInfo(
        String userId,
        String token,
        Instant createdAt,
        Instant lastAccessedAt,
        Instant expiresAt
    ) {}

    /**
     * Create a new session
     */
    public static void createSession(HttpSession session, String userId) {
        String token = SecurityUtil.generateToken();
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(Constants.Security.SESSION_TIMEOUT * 60L);

        SessionInfo sessionInfo = new SessionInfo(
            userId,
            token,
            now,
            now,
            expiresAt
        );

        session.setAttribute("userId", userId);
        session.setAttribute("token", token);
        session.setMaxInactiveInterval(Constants.Security.SESSION_TIMEOUT * 60);

        activeSessions.put(token, sessionInfo);
        logger.info("Created session for user: {}", userId);
    }

    /**
     * Get session information
     */
    public static Optional<SessionInfo> getSessionInfo(String token) {
        if (token == null) {
            return Optional.empty();
        }

        SessionInfo info = activeSessions.get(token);
        if (info == null) {
            return Optional.empty();
        }

        if (Instant.now().isAfter(info.expiresAt())) {
            invalidateSession(token);
            return Optional.empty();
        }

        return Optional.of(info);
    }

    /**
     * Update session access time
     */
    public static void updateSessionAccess(String token) {
        SessionInfo currentInfo = activeSessions.get(token);
        if (currentInfo != null) {
            Instant now = Instant.now();
            SessionInfo updatedInfo = new SessionInfo(
                currentInfo.userId(),
                currentInfo.token(),
                currentInfo.createdAt(),
                now,
                now.plusSeconds(Constants.Security.SESSION_TIMEOUT * 60L)
            );
            activeSessions.put(token, updatedInfo);
        }
    }

    /**
     * Invalidate session
     */
    public static void invalidateSession(String token) {
        SessionInfo removed = activeSessions.remove(token);
        if (removed != null) {
            logger.info("Invalidated session for user: {}", removed.userId());
        }
    }

    /**
     * Clean up expired sessions
     */
    public static void cleanupExpiredSessions() {
        Instant now = Instant.now();
        activeSessions.entrySet().removeIf(entry -> 
            now.isAfter(entry.getValue().expiresAt()));
        logger.debug("Cleaned up expired sessions");
    }

    /**
     * Get active session count
     */
    public static int getActiveSessionCount() {
        return activeSessions.size();
    }

    /**
     * Check if session is valid
     */
    public static boolean isValidSession(String token) {
        return getSessionInfo(token).isPresent();
    }

    /**
     * Get user ID from session
     */
    public static Optional<String> getUserId(String token) {
        return getSessionInfo(token).map(SessionInfo::userId);
    }
}
