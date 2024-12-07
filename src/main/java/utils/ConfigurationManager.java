package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Manages application configuration properties
 */
public final class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
    private static final Map<String, String> properties = new ConcurrentHashMap<>();
    private static boolean initialized = false;

    private ConfigurationManager() {
        throw new AssertionError("ConfigurationManager class should not be instantiated");
    }

    /**
     * Initialize configuration manager
     */
    public static synchronized void initialize() throws IOException {
        if (initialized) {
            logger.warn("ConfigurationManager already initialized");
            return;
        }

        try {
            // Load application.properties
            Properties appProps = new Properties();
            try (InputStream input = ConfigurationManager.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (input == null) {
                    throw new IOException("application.properties not found");
                }
                appProps.load(input);
            }

            // Convert Properties to Map
            appProps.forEach((key, value) -> properties.put(key.toString(), value.toString()));

            // Add environment-specific overrides if present
            String env = System.getProperty("app.environment", "development");
            String envPropsFile = "application-" + env + ".properties";

            try (InputStream envInput = ConfigurationManager.class.getClassLoader()
                    .getResourceAsStream(envPropsFile)) {
                if (envInput != null) {
                    Properties envProps = new Properties();
                    envProps.load(envInput);
                    envProps.forEach((key, value) -> properties.put(key.toString(), value.toString()));
                    logger.info("Loaded environment-specific properties from: {}", envPropsFile);
                }
            }

            // Add system property overrides
            System.getProperties().forEach((key, value) -> {
                String keyStr = key.toString();
                if (keyStr.startsWith("app.")) {
                    properties.put(keyStr.substring(4), value.toString());
                }
            });

            initialized = true;
            logger.info("ConfigurationManager initialized successfully");
        } catch (Exception e) {
            logger.error("Error initializing ConfigurationManager", e);
            throw e;
        }
    }

    /**
     * Get property value
     */
    public static String getProperty(String key) {
        if (!initialized) {
            throw new IllegalStateException("ConfigurationManager not initialized");
        }
        return properties.get(key);
    }

    /**
     * Get property value with default
     */
    public static String getProperty(String key, String defaultValue) {
        if (!initialized) {
            throw new IllegalStateException("ConfigurationManager not initialized");
        }
        return properties.getOrDefault(key, defaultValue);
    }

    /**
     * Get integer property value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer property value for key: {}", key);
            return defaultValue;
        }
    }

    /**
     * Get boolean property value
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    /**
     * Check if property exists
     */
    public static boolean hasProperty(String key) {
        if (!initialized) {
            throw new IllegalStateException("ConfigurationManager not initialized");
        }
        return properties.containsKey(key);
    }

    /**
     * Get all properties
     */
    public static Map<String, String> getAllProperties() {
        if (!initialized) {
            throw new IllegalStateException("ConfigurationManager not initialized");
        }
        return new ConcurrentHashMap<>(properties);
    }

    /**
     * Reload configuration
     */
    public static synchronized void reload() throws IOException {
        logger.info("Reloading configuration");
        properties.clear();
        initialized = false;
        initialize();
    }
}
