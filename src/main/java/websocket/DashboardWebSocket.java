package websocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.SecurityUtil;
import utils.SessionManager;

/**
 * WebSocket endpoint for dashboard real-time updates.
 * Handles WebSocket connections and message broadcasting.
 */
@ServerEndpoint("/websocket/dashboard")
public class DashboardWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(DashboardWebSocket.class);
    private static final Gson gson = new Gson();
    
    // Store active WebSocket sessions
    private static final Map<String, Set<Session>> userSessions = new ConcurrentHashMap<>();
    
    // Store session to user mapping
    private static final Map<Session, String> sessionUserMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        try {
            // Get user ID from HTTP session
            String userId = getUserIdFromSession(session);
            if (userId == null) {
                session.close();
                return;
            }

            // Add session to user's set of sessions
            userSessions.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
            sessionUserMap.put(session, userId);

            // Send initial data
            sendInitialData(session, userId);
            
            logger.info("WebSocket opened for user: {}", userId);
        } catch (Exception e) {
            logger.error("Error in WebSocket open", e);
            try {
                session.close();
            } catch (IOException ex) {
                logger.error("Error closing WebSocket", ex);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            String userId = sessionUserMap.get(session);
            if (userId == null) {
                return;
            }

            JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
            String type = jsonMessage.get("type").getAsString();

            switch (type) {
                case "ping":
                    handlePing(session);
                    break;
                case "subscribe":
                    handleSubscribe(session, userId, jsonMessage);
                    break;
                case "unsubscribe":
                    handleUnsubscribe(session, userId, jsonMessage);
                    break;
                default:
                    logger.warn("Unknown message type: {}", type);
            }
        } catch (Exception e) {
            logger.error("Error processing WebSocket message", e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        String userId = sessionUserMap.remove(session);
        if (userId != null) {
            Set<Session> sessions = userSessions.get(userId);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    userSessions.remove(userId);
                }
            }
            logger.info("WebSocket closed for user: {}", userId);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("WebSocket error", error);
        String userId = sessionUserMap.get(session);
        if (userId != null) {
            logger.error("WebSocket error for user: {}", userId);
        }
    }

    /**
     * Broadcast message to specific user's sessions
     */
    public static void broadcastToUser(String userId, String type, Object data) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions != null) {
            JsonObject message = new JsonObject();
            message.addProperty("type", type);
            message.add("data", gson.toJsonTree(data));
            
            String jsonMessage = gson.toJson(message);
            sessions.forEach(session -> {
                try {
                    session.getBasicRemote().sendText(jsonMessage);
                } catch (IOException e) {
                    logger.error("Error sending message to user: {}", userId, e);
                }
            });
        }
    }

    /**
     * Send initial data when connection opens
     */
    private void sendInitialData(Session session, String userId) {
        try {
            // Send project stats
            JsonObject stats = getProjectStats(userId);
            JsonObject message = new JsonObject();
            message.addProperty("type", "stats_update");
            message.add("data", stats);
            session.getBasicRemote().sendText(gson.toJson(message));

            // Send recent activities
            JsonObject activities = getRecentActivities(userId);
            message = new JsonObject();
            message.addProperty("type", "activities_update");
            message.add("data", activities);
            session.getBasicRemote().sendText(gson.toJson(message));
        } catch (Exception e) {
            logger.error("Error sending initial data", e);
        }
    }

    /**
     * Handle ping message
     */
    private void handlePing(Session session) throws IOException {
        JsonObject response = new JsonObject();
        response.addProperty("type", "pong");
        response.addProperty("timestamp", System.currentTimeMillis());
        session.getBasicRemote().sendText(gson.toJson(response));
    }

    /**
     * Handle subscribe message
     */
    private void handleSubscribe(Session session, String userId, JsonObject message) {
        String topic = message.get("topic").getAsString();
        // Implement subscription logic
        logger.debug("User {} subscribed to topic: {}", userId, topic);
    }

    /**
     * Handle unsubscribe message
     */
    private void handleUnsubscribe(Session session, String userId, JsonObject message) {
        String topic = message.get("topic").getAsString();
        // Implement unsubscription logic
        logger.debug("User {} unsubscribed from topic: {}", userId, topic);
    }

    /**
     * Get user ID from session
     */
    private String getUserIdFromSession(Session session) {
        try {
            // Get HTTP session ID from WebSocket session
            String httpSessionId = session.getRequestParameterMap()
                .get("sessionId").get(0);
            
            // Validate session and get user ID
            if (httpSessionId != null && SecurityUtil.validateToken(httpSessionId)) {
                return SessionManager.getInstance()
                    .getUserIdFromSessionId(httpSessionId)
                    .orElse(null);
            }
        } catch (Exception e) {
            logger.error("Error getting user ID from session", e);
        }
        return null;
    }

    /**
     * Get project stats for user
     */
    private JsonObject getProjectStats(String userId) {
        // Implement project stats retrieval
        JsonObject stats = new JsonObject();
        // Add stats data
        return stats;
    }

    /**
     * Get recent activities for user
     */
    private JsonObject getRecentActivities(String userId) {
        // Implement recent activities retrieval
        JsonObject activities = new JsonObject();
        // Add activities data
        return activities;
    }
}
