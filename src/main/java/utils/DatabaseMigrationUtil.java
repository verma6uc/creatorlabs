package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for managing database migrations.
 * Handles schema versioning and updates.
 */
public class DatabaseMigrationUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationUtil.class);
    private static final String MIGRATION_TABLE = "schema_migrations";
    private static final Pattern SQL_DELIMITER = Pattern.compile(";\\s*$", Pattern.MULTILINE);

    /**
     * Record to represent a migration version
     */
    private record MigrationVersion(
        String version,
        String description,
        LocalDateTime appliedAt
    ) {}

    /**
     * Initialize the migration system
     */
    public static void initialize() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            createMigrationTableIfNeeded(conn);
            runPendingMigrations(conn);
        } catch (SQLException e) {
            logger.error("Failed to initialize database migrations", e);
            throw new RuntimeException("Database migration initialization failed", e);
        }
    }

    /**
     * Create the migration tracking table if it doesn't exist
     */
    private static void createMigrationTableIfNeeded(Connection conn) throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS %s (
                version VARCHAR(50) PRIMARY KEY,
                description TEXT NOT NULL,
                applied_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                checksum VARCHAR(64) NOT NULL
            )
            """.formatted(MIGRATION_TABLE);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            logger.info("Migration table check completed");
        }
    }

    /**
     * Run any pending migrations
     */
    private static void runPendingMigrations(Connection conn) throws SQLException {
        List<String> appliedMigrations = getAppliedMigrations(conn);
        List<String> pendingMigrations = findPendingMigrations(appliedMigrations);

        for (String migrationFile : pendingMigrations) {
            applyMigration(conn, migrationFile);
        }
    }

    /**
     * Get list of already applied migrations
     */
    private static List<String> getAppliedMigrations(Connection conn) throws SQLException {
        List<String> applied = new ArrayList<>();
        String sql = "SELECT version FROM " + MIGRATION_TABLE + " ORDER BY applied_at";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                applied.add(rs.getString("version"));
            }
        }
        return applied;
    }

    /**
     * Find migrations that haven't been applied yet
     */
    private static List<String> findPendingMigrations(List<String> appliedMigrations) {
        List<String> pending = new ArrayList<>();
        try {
            // List all migration files in the db/migrations directory
            try (InputStream is = DatabaseMigrationUtil.class.getResourceAsStream("/db/migrations")) {
                if (is == null) {
                    logger.warn("No migrations directory found");
                    return pending;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String fileName;
                while ((fileName = reader.readLine()) != null) {
                    if (fileName.endsWith(".sql") && !appliedMigrations.contains(getVersionFromFileName(fileName))) {
                        pending.add(fileName);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read migration files", e);
            throw new RuntimeException("Failed to read migration files", e);
        }
        return pending;
    }

    /**
     * Apply a single migration
     */
    private static void applyMigration(Connection conn, String migrationFile) throws SQLException {
        logger.info("Applying migration: {}", migrationFile);
        String version = getVersionFromFileName(migrationFile);
        String description = getDescriptionFromFileName(migrationFile);

        try {
            conn.setAutoCommit(false);

            // Read and execute migration SQL
            String migrationSql = readMigrationFile(migrationFile);
            String[] statements = SQL_DELIMITER.split(migrationSql);

            try (Statement stmt = conn.createStatement()) {
                for (String sql : statements) {
                    if (!sql.trim().isEmpty()) {
                        stmt.execute(sql.trim());
                    }
                }
            }

            // Record the migration
            recordMigration(conn, version, description, migrationSql);

            conn.commit();
            logger.info("Successfully applied migration: {}", migrationFile);
        } catch (Exception e) {
            conn.rollback();
            logger.error("Failed to apply migration: {}", migrationFile, e);
            throw new SQLException("Migration failed: " + migrationFile, e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /**
     * Record a successfully applied migration
     */
    private static void recordMigration(Connection conn, String version, String description, String sql) 
            throws SQLException {
        String insertSql = "INSERT INTO " + MIGRATION_TABLE + 
                          " (version, description, checksum) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, version);
            pstmt.setString(2, description);
            pstmt.setString(3, calculateChecksum(sql));
            pstmt.executeUpdate();
        }
    }

    /**
     * Calculate checksum for migration SQL
     */
    private static String calculateChecksum(String sql) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(sql.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            logger.error("Failed to calculate checksum", e);
            throw new RuntimeException("Failed to calculate checksum", e);
        }
    }

    /**
     * Read migration file content
     */
    private static String readMigrationFile(String fileName) throws IOException {
        try (InputStream is = DatabaseMigrationUtil.class.getResourceAsStream("/db/migrations/" + fileName)) {
            if (is == null) {
                throw new IOException("Migration file not found: " + fileName);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    /**
     * Extract version number from migration filename
     */
    private static String getVersionFromFileName(String fileName) {
        return fileName.split("_")[0];
    }

    /**
     * Extract description from migration filename
     */
    private static String getDescriptionFromFileName(String fileName) {
        String nameWithoutVersion = fileName.substring(fileName.indexOf('_') + 1);
        return nameWithoutVersion.substring(0, nameWithoutVersion.lastIndexOf('.')).replace('_', ' ');
    }

    /**
     * Validate database schema
     */
    public static boolean validateSchema() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            List<String> appliedMigrations = getAppliedMigrations(conn);
            
            // Verify each applied migration's checksum
            for (String version : appliedMigrations) {
                if (!validateMigrationChecksum(conn, version)) {
                    return false;
                }
            }
            
            return true;
        } catch (SQLException e) {
            logger.error("Schema validation failed", e);
            return false;
        }
    }

    /**
     * Validate a single migration's checksum
     */
    private static boolean validateMigrationChecksum(Connection conn, String version) throws SQLException {
        String sql = "SELECT checksum FROM " + MIGRATION_TABLE + " WHERE version = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, version);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedChecksum = rs.getString("checksum");
                    String fileName = findMigrationFileByVersion(version);
                    if (fileName == null) {
                        logger.error("Migration file not found for version: {}", version);
                        return false;
                    }
                    
                    String currentSql = readMigrationFile(fileName);
                    String currentChecksum = calculateChecksum(currentSql);
                    
                    return storedChecksum.equals(currentChecksum);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to validate migration checksum", e);
            return false;
        }
        
        return false;
    }

    /**
     * Find migration file by version number
     */
    private static String findMigrationFileByVersion(String version) throws IOException {
        try (InputStream is = DatabaseMigrationUtil.class.getResourceAsStream("/db/migrations")) {
            if (is == null) return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String fileName;
            while ((fileName = reader.readLine()) != null) {
                if (fileName.startsWith(version + "_")) {
                    return fileName;
                }
            }
        }
        return null;
    }
}
