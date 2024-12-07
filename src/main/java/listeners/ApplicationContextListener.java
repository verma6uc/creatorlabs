package listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import utils.DatabaseMigrationUtil;
import utils.DatabaseUtils;

/**
 * Application context listener for initialization and cleanup tasks.
 * Handles database initialization, migrations, and other startup/shutdown operations.
 */
@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing application context");
        
        try {
            // Initialize database connection
            if (DatabaseUtils.testConnection()) {
                logger.info("Database connection established successfully");
            } else {
                logger.error("Failed to establish database connection");
                throw new RuntimeException("Database connection failed");
            }

            // Run database migrations
            DatabaseMigrationUtil.initialize();
            
            if (DatabaseMigrationUtil.validateSchema()) {
                logger.info("Database schema validation successful");
            } else {
                logger.warn("Database schema validation failed - schema might be out of sync");
            }

            // Set application-wide attributes
            sce.getServletContext().setAttribute("appStartTime", System.currentTimeMillis());
            sce.getServletContext().setAttribute("environment", 
                System.getProperty("app.environment", "development"));

            logger.info("Application context initialized successfully");
            
        } catch (Exception e) {
            logger.error("Failed to initialize application context", e);
            throw new RuntimeException("Application initialization failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down application context");
        
        try {
            // Cleanup database connections
            DatabaseUtils.shutdown();
            
            // Remove application attributes
            sce.getServletContext().removeAttribute("appStartTime");
            sce.getServletContext().removeAttribute("environment");
            
            logger.info("Application context shutdown completed");
            
        } catch (Exception e) {
            logger.error("Error during application shutdown", e);
        }
    }
}
