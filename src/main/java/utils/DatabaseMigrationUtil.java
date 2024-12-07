package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class for handling database migrations
 */
public final class DatabaseMigrationUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationUtil.class);
    private static final String MIGRATIONS_PATH = "db/migrations";
    private static final Pattern MIGRATION_FILE_PATTERN = Pattern.compile("V(\\d+)__.*\\.sql");

    private DatabaseMigrationUtil() {
        throw new AssertionError("DatabaseMigrationUtil class should not be instantiated");
    }

    /**
     * Migration version record
     */
    public record MigrationVersion(
        int version,
        String description,
        Instant appliedAt
    ) {}

    /**
     * Run database migrations
     */
    public static void migrateDatabase() {
        logger.info("Starting database migration");

        try {
            createVersionTableIfNeeded();
            List<MigrationVersion> appliedMigrations = getAppliedMigrations();
            List<String> pendingMigrations = findPendingMigrations(appliedMigrations);

            if (pendingMigrations.isEmpty()) {
                logger.info("No pending migrations found");
                return;
            }

            for (String migrationFile : pendingMigrations) {
                applyMigration(migrationFile);
            }

            logger.info("Database migration completed successfully");
        } catch (Exception e) {
            logger.error("Error during database migration", e);
            throw new RuntimeException("Database migration failed", e);
        }
    }

    /**
     * Create version table if it doesn't exist
     */
    private static void createVersionTableIfNeeded() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS schema_version (
                version INTEGER PRIMARY KEY,
                description VARCHAR(200) NOT NULL,
                applied_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
            )
        """;

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            logger.debug("Schema version table created or verified");
        }
    }

    /**
     * Get list of applied migrations
     */
    private static List<MigrationVersion> getAppliedMigrations() throws SQLException {
        String sql = "SELECT version, description, applied_at FROM schema_version ORDER BY version";
        List<MigrationVersion> migrations = new ArrayList<>();

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                migrations.add(new MigrationVersion(
                    rs.getInt("version"),
                    rs.getString("description"),
                    rs.getTimestamp("applied_at").toInstant()
                ));
            }
        }

        return migrations;
    }

    /**
     * Find pending migrations
     */
    private static List<String> findPendingMigrations(List<MigrationVersion> appliedMigrations) {
        List<String> pendingMigrations = new ArrayList<>();
        
        try {
            ClassLoader classLoader = DatabaseMigrationUtil.class.getClassLoader();
            try (InputStream in = classLoader.getResourceAsStream(MIGRATIONS_PATH)) {
                if (in == null) {
                    logger.warn("Migrations directory not found: {}", MIGRATIONS_PATH);
                    return pendingMigrations;
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.endsWith(".sql")) {
                            var matcher = MIGRATION_FILE_PATTERN.matcher(line);
                            if (matcher.matches()) {
                                int version = Integer.parseInt(matcher.group(1));
                                if (appliedMigrations.stream()
                                        .noneMatch(m -> m.version() == version)) {
                                    pendingMigrations.add(line);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error finding pending migrations", e);
            throw new RuntimeException("Error finding pending migrations", e);
        }

        return pendingMigrations;
    }

    /**
     * Apply a single migration
     */
    private static void applyMigration(String migrationFile) {
        logger.info("Applying migration: {}", migrationFile);

        try {
            String sql = loadMigrationSql(migrationFile);
            int version = extractVersion(migrationFile);
            String description = extractDescription(migrationFile);

            try (Connection conn = DatabaseUtils.getConnection()) {
                conn.setAutoCommit(false);

                try {
                    // Execute migration
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.executeUpdate();
                    }

                    // Record migration
                    String insertSql = """
                        INSERT INTO schema_version (version, description)
                        VALUES (?, ?)
                    """;
                    try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                        stmt.setInt(1, version);
                        stmt.setString(2, description);
                        stmt.executeUpdate();
                    }

                    conn.commit();
                    logger.info("Migration {} applied successfully", migrationFile);
                } catch (Exception e) {
                    conn.rollback();
                    throw e;
                } finally {
                    conn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            logger.error("Error applying migration: {}", migrationFile, e);
            throw new RuntimeException("Migration failed: " + migrationFile, e);
        }
    }

    /**
     * Load migration SQL from file
     */
    private static String loadMigrationSql(String migrationFile) throws Exception {
        try (InputStream in = DatabaseMigrationUtil.class.getClassLoader()
                .getResourceAsStream(MIGRATIONS_PATH + "/" + migrationFile)) {
            if (in == null) {
                throw new RuntimeException("Migration file not found: " + migrationFile);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    /**
     * Extract version number from migration filename
     */
    private static int extractVersion(String migrationFile) {
        var matcher = MIGRATION_FILE_PATTERN.matcher(migrationFile);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Invalid migration filename: " + migrationFile);
    }

    /**
     * Extract description from migration filename
     */
    private static String extractDescription(String migrationFile) {
        String name = migrationFile.substring(migrationFile.indexOf("__") + 2);
        name = name.substring(0, name.lastIndexOf("."));
        return name.replace("_", " ");
    }
}
