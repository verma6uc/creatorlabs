package listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigurationManager;
import utils.Constants;
import utils.DatabaseUtils;
import utils.DatabaseUtils.DbConfig;
import utils.DatabaseMigrationUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Application lifecycle listener
 */
@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing application context");
        ServletContext context = sce.getServletContext();

        try {
            // Initialize configuration
            ConfigurationManager.initialize();
            logger.info("Configuration initialized");

            // Initialize database connection pool
            initializeDatabase();
            logger.info("Database connection pool initialized");

            // Run database migrations
            DatabaseMigrationUtil.migrateDatabase();
            logger.info("Database migrations completed");

            // Initialize scheduler for background tasks
            initializeScheduler();
            logger.info("Background task scheduler initialized");

            // Set application attributes
            context.setAttribute("appName", Constants.App.NAME);
            context.setAttribute("appVersion", Constants.App.VERSION);
            
            logger.info("Application context initialized successfully");
        } catch (Exception e) {
            logger.error("Error initializing application context", e);
            throw new RuntimeException("Application initialization failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down application context");

        try {
            // Shutdown scheduler
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdown();
                try {
                    if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                        scheduler.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    scheduler.shutdownNow();
                    Thread.currentThread().interrupt();
                }
                logger.info("Background task scheduler shutdown completed");
            }

            // Close database connections
            DatabaseUtils.shutdown();
            logger.info("Database connections closed");

            logger.info("Application context shutdown completed");
        } catch (Exception e) {
            logger.error("Error during application context shutdown", e);
        }
    }

    /**
     * Initialize database connection pool
     */
    private void initializeDatabase() {
        DbConfig config = new DbConfig(
            ConfigurationManager.getProperty("db.url"),
            ConfigurationManager.getProperty("db.username"),
            ConfigurationManager.getProperty("db.password"),
            ConfigurationManager.getProperty("db.driver"),
            Constants.Database.MIN_POOL_SIZE,
            Constants.Database.MAX_POOL_SIZE
        );
        DatabaseUtils.initializePool(config);
    }

    /**
     * Initialize scheduler for background tasks
     */
    private void initializeScheduler() {
        scheduler = Executors.newScheduledThreadPool(2);

        // Schedule session cleanup
        scheduler.scheduleAtFixedRate(
            () -> {
                try {
                    utils.SessionManager.cleanupExpiredSessions();
                } catch (Exception e) {
                    logger.error("Error during session cleanup", e);
                }
            },
            15,
            15,
            TimeUnit.MINUTES
        );

        // Schedule cache cleanup
        scheduler.scheduleAtFixedRate(
            () -> {
                try {
                    // TODO: Implement cache cleanup
                    logger.debug("Cache cleanup task executed");
                } catch (Exception e) {
                    logger.error("Error during cache cleanup", e);
                }
            },
            Constants.Cache.CACHE_CLEANUP_INTERVAL.toMinutes(),
            Constants.Cache.CACHE_CLEANUP_INTERVAL.toMinutes(),
            TimeUnit.MINUTES
        );
    }
}
