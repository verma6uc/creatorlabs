package utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Database utility class for managing connections and common database operations
 */
public final class DatabaseUtils {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    private static HikariDataSource dataSource;

    private DatabaseUtils() {
        throw new AssertionError("DatabaseUtils class should not be instantiated");
    }

    /**
     * Database configuration record
     */
    public record DbConfig(
        String url,
        String username,
        String password,
        String driver,
        int minConnections,
        int maxConnections
    ) {}

    /**
     * Initialize the database connection pool
     */
    public static void initializePool(DbConfig config) {
        if (dataSource != null) {
            logger.warn("Database pool already initialized");
            return;
        }

        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(config.url());
            hikariConfig.setUsername(config.username());
            hikariConfig.setPassword(config.password());
            hikariConfig.setDriverClassName(config.driver());
            hikariConfig.setMinimumIdle(config.minConnections());
            hikariConfig.setMaximumPoolSize(config.maxConnections());
            hikariConfig.setConnectionTimeout(Constants.Database.CONNECTION_TIMEOUT);
            hikariConfig.setIdleTimeout(Constants.Database.IDLE_TIMEOUT);
            hikariConfig.setAutoCommit(true);
            hikariConfig.setPoolName("CreatorLabsPool");

            // Additional properties for better performance
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");

            dataSource = new HikariDataSource(hikariConfig);
            logger.info("Database connection pool initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize database pool", e);
            throw new RuntimeException("Failed to initialize database pool", e);
        }
    }

    /**
     * Get a connection from the pool
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            logger.error("Database pool not initialized");
            throw new SQLException("Database pool not initialized");
        }
        return dataSource.getConnection();
    }

    /**
     * Close database resources safely
     */
    public static void closeQuietly(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    logger.warn("Error closing resource", e);
                }
            }
        }
    }

    /**
     * Execute a query and return a single result
     */
    public static <T> Optional<T> queryForObject(String sql, ResultSetMapper<T> mapper, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            setParameters(stmt, params);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.ofNullable(mapper.map(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Error executing query: {}", sql, e);
            throw new RuntimeException("Database query failed", e);
        } finally {
            closeQuietly(rs, stmt, conn);
        }
    }

    /**
     * Execute an update statement
     */
    public static int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            setParameters(stmt, params);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error executing update: {}", sql, e);
            throw new RuntimeException("Database update failed", e);
        } finally {
            closeQuietly(stmt, conn);
        }
    }

    /**
     * Set parameters for a prepared statement
     */
    private static void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * Shutdown the connection pool
     */
    public static void shutdown() {
        if (dataSource != null) {
            logger.info("Shutting down database connection pool");
            dataSource.close();
            dataSource = null;
        }
    }

    /**
     * Functional interface for mapping ResultSet to objects
     */
    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}
