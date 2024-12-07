package websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SecurityUtil;
import utils.SessionManager;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * WebSocket endpoint for dashboard real-time updates
 */
@ServerEndpoint(value = "/ws/dashboard")
public class DashboardWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(DashboardWebSocket.class);
    private static final Gson gson = new Gson();

    // Connected sessions by user ID
    private static final Map<String, Queue<Session>> userSessions = new ConcurrentHashMap<>();
    
    @OnOpen
    public void onOpen(Session session) {
        String token = getTokenFromSession(session);
        if (token == null || !SecurityUtil.validateToken(token)) {
            closeSession(session, "Unauthorized");
            return;
        }

        String userId = SecurityUtil.getUserIdFromToken(token);
        if (userId == null) {
            closeSession(session, "Invalid user");
            return;
        }

        // Add session to user's queue
        userSessions.computeIfAbsent(userId, k -> new ConcurrentLinkedQueue<>()).add(session);
        logger.info("WebSocket opened for user: {}", userId);

        // Send initial dashboard data
        sendDashboardData(session, userId);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String token = getTokenFromSession(session);
        if (token == null || !SecurityUtil.validateToken(token)) {
            closeSession(session, "Unauthorized");
            return;
        }

        String userId = SecurityUtil.getUserIdFromToken(token);
        if (userId == null) {
            closeSession(session, "Invalid user");
            return;
        }

        try {
            JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
            String action = jsonMessage.get("action").getAsString();

            switch (action) {
                case "refresh" -> handleRefreshRequest(session, userId);
                case "update" -> handleUpdateRequest(session, userId, jsonMessage);
                case "subscribe" -> handleSubscribeRequest(session, userId, jsonMessage);
                case "unsubscribe" -> handleUnsubscribeRequest(session, userId, jsonMessage);
                default -> sendError(session, "Unknown action: " + action);
            }
        } catch (Exception e) {
            logger.error("Error processing WebSocket message", e);
            sendError(session, "Error processing message");
        }
    }

    @OnClose
    public void onClose(Session session) {
        String token = getTokenFromSession(session);
        if (token != null) {
            String userId = SecurityUtil.getUserIdFromToken(token);
            if (userId != null) {
                Queue<Session> sessions = userSessions.get(userId);
                if (sessions != null) {
                    sessions.remove(session);
                    if (sessions.isEmpty()) {
                        userSessions.remove(userId);
                    }
                }
                logger.info("WebSocket closed for user: {}", userId);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("WebSocket error", error);
        closeSession(session, "Internal error");
    }

    /**
     * Send dashboard data to session
     */
    private void sendDashboardData(Session session, String userId) {
        try {
            JsonObject data = new JsonObject();
            data.addProperty("type", "dashboard");
            // TODO: Add actual dashboard data
            data.add("data", new JsonObject());
            session.getBasicRemote().sendText(gson.toJson(data));
        } catch (IOException e) {
            logger.error("Error sending dashboard data", e);
            closeSession(session, "Error sending data");
        }
    }

    /**
     * Handle refresh request
     */
    private void handleRefreshRequest(Session session, String userId) {
        sendDashboardData(session, userId);
    }

    /**
     * Handle update request
     */
    private void handleUpdateRequest(Session session, String userId, JsonObject message) {
        // TODO: Implement update handling
        JsonObject response = new JsonObject();
        response.addProperty("type", "update");
        response.addProperty("status", "success");
        sendMessage(session, response);
    }

    /**
     * Handle subscribe request
     */
    private void handleSubscribeRequest(Session session, String userId, JsonObject message) {
        // TODO: Implement subscription handling
        JsonObject response = new JsonObject();
        response.addProperty("type", "subscribe");
        response.addProperty("status", "success");
        sendMessage(session, response);
    }

    /**
     * Handle unsubscribe request
     */
    private void handleUnsubscribeRequest(Session session, String userId, JsonObject message) {
        // TODO: Implement unsubscription handling
        JsonObject response = new JsonObject();
        response.addProperty("type", "unsubscribe");
        response.addProperty("status", "success");
        sendMessage(session, response);
    }

    /**
     * Send error message to session
     */
    private void sendError(Session session, String error) {
        JsonObject response = new JsonObject();
        response.addProperty("type", "error");
        response.addProperty("message", error);
        sendMessage(session, response);
    }

    /**
     * Send message to session
     */
    private void sendMessage(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(gson.toJson(message));
        } catch (IOException e) {
            logger.error("Error sending message", e);
            closeSession(session, "Error sending message");
        }
    }

    /**
     * Close session with reason
     */
    private void closeSession(Session session, String reason) {
        try {
            session.close(new CloseReason(
                CloseReason.CloseCodes.NORMAL_CLOSURE,
                reason
            ));
        } catch (IOException e) {
            logger.error("Error closing WebSocket session", e);
        }
    }

    /**
     * Get token from session
     */
    private String getTokenFromSession(Session session) {
        Map<String, String> params = session.getRequestParameterMap()
            .entrySet()
            .stream()
            .collect(java.util.stream.Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().get(0)
            ));
        return params.get("token");
    }

    /**
     * Broadcast message to all sessions of a user
     */
    public static void broadcastToUser(String userId, JsonObject message) {
        Queue<Session> sessions = userSessions.get(userId);
        if (sessions != null) {
            String messageStr = gson.toJson(message);
            sessions.forEach(session -> {
                try {
                    session.getBasicRemote().sendText(messageStr);
                } catch (IOException e) {
                    logger.error("Error broadcasting message to user: {}", userId, e);
                }
            });
        }
    }

    /**
     * Broadcast message to all connected sessions
     */
    public static void broadcastToAll(JsonObject message) {
        String messageStr = gson.toJson(message);
        userSessions.values().forEach(sessions ->
            sessions.forEach(session -> {
                try {
                    session.getBasicRemote().sendText(messageStr);
                } catch (IOException e) {
                    logger.error("Error broadcasting message", e);
                }
            })
        );
    }
}
