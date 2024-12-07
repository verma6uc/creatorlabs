package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Utility class for database operations using HikariCP connection pooling.
 * Supports JDK 22 features and modern Java practices.
 */
public final class DatabaseUtils {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    private static HikariDataSource dataSource;
    private static final Properties properties = new Properties();
    
    // Record for database configuration
    private record DbConfig(
        String url,
        String username,
        String password,
        String driver,
        int minConnections,
        int maxConnections
    ) {}
    
    static {
        try (InputStream input = DatabaseUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
            initializeDataSource(loadDbConfig());
            logger.info("Database configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load application.properties", e);
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }
    
    private static DbConfig loadDbConfig() {
        return new DbConfig(
            properties.getProperty("db.url"),
            properties.getProperty("db.username"),
            properties.getProperty("db.password"),
            properties.getProperty("db.driver"),
            Integer.parseInt(properties.getProperty("db.min.connections", "5")),
            Integer.parseInt(properties.getProperty("db.max.connections", "20"))
        );
    }
    
    private static void initializeDataSource(DbConfig config) {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.url());
        hikariConfig.setUsername(config.username());
        hikariConfig.setPassword(config.password());
        hikariConfig.setDriverClassName(config.driver());
        
        // Connection pool settings
        hikariConfig.setMinimumIdle(config.minConnections());
        hikariConfig.setMaximumPoolSize(config.maxConnections());
        hikariConfig.setAutoCommit(true);
        hikariConfig.setIdleTimeout(300000); // 5 minutes
        hikariConfig.setMaxLifetime(600000); // 10 minutes
        
        // Additional HikariCP optimizations
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        
        dataSource = new HikariDataSource(hikariConfig);
        logger.info("Database connection pool initialized successfully");
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Failed to get database connection", e);
            throw e;
        }
    }
    
    public static void closeQuietly(AutoCloseable... resources) {
        for (var resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    logger.warn("Error closing resource", e);
                }
            }
        }
    }
    
    // Enhanced database operations with modern Java features
    
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (var conn = getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            return stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error executing update: {}", sql, e);
            throw e;
        }
    }
    
    public static ResultSet executeQuery(Connection conn, String sql, Object... params) throws SQLException {
        try {
            var stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeQuery();
        } catch (SQLException e) {
            logger.error("Error executing query: {}", sql, e);
            throw e;
        }
    }
    
    public static boolean testConnection() {
        try (var conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            logger.error("Database connection test failed", e);
            return false;
        }
    }
    
    // Transaction management with enhanced error handling
    
    public static void beginTransaction(Connection conn) throws SQLException {
        if (conn != null) {
            conn.setAutoCommit(false);
            logger.debug("Transaction started");
        }
    }
    
    public static void commitTransaction(Connection conn) throws SQLException {
        if (conn != null) {
            conn.commit();
            conn.setAutoCommit(true);
            logger.debug("Transaction committed");
        }
    }
    
    public static void rollbackTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                logger.debug("Transaction rolled back");
            } catch (SQLException e) {
                logger.error("Error rolling back transaction", e);
            }
        }
    }
    
    // Batch operations with enhanced error handling
    
    public static void executeBatch(String sql, List<Object[]> paramsList) throws SQLException {
        try (var conn = getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false);
            
            try {
                for (var params : paramsList) {
                    for (int i = 0; i < params.length; i++) {
                        stmt.setObject(i + 1, params[i]);
                    }
                    stmt.addBatch();
                }
                
                stmt.executeBatch();
                conn.commit();
                logger.info("Batch execution completed successfully");
            } catch (SQLException e) {
                conn.rollback();
                logger.error("Batch execution failed", e);
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
    // Database metadata operations with modern string handling
    
    public static boolean tableExists(String tableName) throws SQLException {
        try (var conn = getConnection()) {
            var rs = conn.getMetaData().getTables(null, null, tableName.toLowerCase(), null);
            return rs.next();
        }
    }
    
    public static void createTableIfNotExists(String tableName, String createTableSQL) throws SQLException {
        if (!tableExists(tableName)) {
            executeUpdate(createTableSQL);
            logger.info("Table {} created successfully", tableName);
        }
    }
    
    // Shutdown hook with enhanced resource management
    
    public static void shutdown() {
        if (dataSource instanceof HikariDataSource ds && !ds.isClosed()) {
            ds.close();
            logger.info("Database connection pool shut down successfully");
        }
    }
    
    private DatabaseUtils() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
