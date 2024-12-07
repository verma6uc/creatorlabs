package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration manager for handling application settings from multiple sources.
 * Supports properties files, environment variables, and system properties.
 */
public final class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
    private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    
    private final Map<String, String> configuration = new ConcurrentHashMap<>();
    private final Map<String, Function<String, ?>> typeConverters = new HashMap<>();
    
    private ConfigurationManager() {
        initializeTypeConverters();
        loadConfiguration();
    }
    
    /**
     * Initialize type converters for different data types
     */
    private void initializeTypeConverters() {
        typeConverters.put("String", value -> value);
        typeConverters.put("Integer", Integer::parseInt);
        typeConverters.put("Long", Long::parseLong);
        typeConverters.put("Double", Double::parseDouble);
        typeConverters.put("Boolean", Boolean::parseBoolean);
    }
    
    /**
     * Load configuration from all sources
     */
    private void loadConfiguration() {
        try {
            // Load from properties file
            loadPropertiesFile();
            
            // Load from environment variables
            loadEnvironmentVariables();
            
            // Load from system properties
            loadSystemProperties();
            
            logger.info("Configuration loaded successfully");
        } catch (Exception e) {
            logger.error("Failed to load configuration", e);
            throw new RuntimeException("Configuration loading failed", e);
        }
    }
    
    /**
     * Load properties from application.properties file
     */
    private void loadPropertiesFile() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
                properties.forEach((key, value) -> configuration.put(key.toString(), value.toString()));
            }
        }
    }
    
    /**
     * Load configuration from environment variables
     */
    private void loadEnvironmentVariables() {
        System.getenv().forEach((key, value) -> {
            // Convert environment variable names to property format
            String propertyKey = key.toLowerCase().replace('_', '.');
            configuration.put(propertyKey, value);
        });
    }
    
    /**
     * Load configuration from system properties
     */
    private void loadSystemProperties() {
        System.getProperties().forEach((key, value) -> 
            configuration.put(key.toString(), value.toString()));
    }
    
    /**
     * Get configuration value with type conversion
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type, T defaultValue) {
        String value = configuration.get(key);
        if (value == null) {
            return defaultValue;
        }
        
        try {
            Function<String, ?> converter = typeConverters.get(type.getSimpleName());
            if (converter == null) {
                throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
            }
            return (T) converter.apply(value);
        } catch (Exception e) {
            logger.warn("Failed to convert value for key: {}. Using default value.", key, e);
            return defaultValue;
        }
    }
    
    /**
     * Get string configuration value
     */
    public String getString(String key, String defaultValue) {
        return get(key, String.class, defaultValue);
    }
    
    /**
     * Get integer configuration value
     */
    public int getInt(String key, int defaultValue) {
        return get(key, Integer.class, defaultValue);
    }
    
    /**
     * Get long configuration value
     */
    public long getLong(String key, long defaultValue) {
        return get(key, Long.class, defaultValue);
    }
    
    /**
     * Get double configuration value
     */
    public double getDouble(String key, double defaultValue) {
        return get(key, Double.class, defaultValue);
    }
    
    /**
     * Get boolean configuration value
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return get(key, Boolean.class, defaultValue);
    }
    
    /**
     * Set configuration value
     */
    public void set(String key, String value) {
        configuration.put(key, value);
        logger.debug("Configuration updated - key: {}, value: {}", key, value);
    }
    
    /**
     * Check if configuration contains key
     */
    public boolean containsKey(String key) {
        return configuration.containsKey(key);
    }
    
    /**
     * Remove configuration value
     */
    public void remove(String key) {
        configuration.remove(key);
        logger.debug("Configuration removed - key: {}", key);
    }
    
    /**
     * Clear all configuration values
     */
    public void clear() {
        configuration.clear();
        logger.info("Configuration cleared");
    }
    
    /**
     * Reload configuration from all sources
     */
    public void reload() {
        clear();
        loadConfiguration();
        logger.info("Configuration reloaded");
    }
    
    /**
     * Get configuration value with validation
     */
    public <T> T getValidated(String key, Class<T> type, T defaultValue, Function<T, Boolean> validator) {
        T value = get(key, type, defaultValue);
        if (!validator.apply(value)) {
            logger.warn("Validation failed for key: {}. Using default value.", key);
            return defaultValue;
        }
        return value;
    }
    
    /**
     * Get all configuration keys
     */
    public Set<String> getKeys() {
        return new HashSet<>(configuration.keySet());
    }
    
    /**
     * Get configuration subset by prefix
     */
    public Map<String, String> getSubset(String prefix) {
        return configuration.entrySet().stream()
            .filter(e -> e.getKey().startsWith(prefix))
            .collect(Collectors.toMap(
                e -> e.getKey().substring(prefix.length()),
                Map.Entry::getValue
            ));
    }
    
    /**
     * Get singleton instance
     */
    public static ConfigurationManager getInstance() {
        return INSTANCE;
    }
}
