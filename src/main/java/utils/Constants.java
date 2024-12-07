package utils;

import java.time.Duration;
import java.util.Set;

/**
 * Application-wide constants and configuration values.
 * Use this class to maintain consistent values across the application.
 */
public final class Constants {
    
    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class should not be instantiated");
    }

    /**
     * Application Settings
     */
    public static final class App {
        public static final String NAME = "CreatorLabs";
        public static final String VERSION = "1.0.0";
        public static final Duration SESSION_TIMEOUT = Duration.ofMinutes(30);
        public static final int MAX_LOGIN_ATTEMPTS = 5;
        public static final Duration LOGIN_LOCKOUT_DURATION = Duration.ofMinutes(15);
    }

    /**
     * Security Constants
     */
    public static final class Security {
        public static final int PASSWORD_MIN_LENGTH = 12;
        public static final int PASSWORD_MAX_LENGTH = 128;
        public static final int SALT_LENGTH = 32;
        public static final int HASH_ITERATIONS = 310000;
        public static final int TOKEN_LENGTH = 32;
        public static final Duration TOKEN_VALIDITY = Duration.ofDays(7);
        public static final String CSRF_HEADER = "X-CSRF-TOKEN";
        public static final String CSRF_FORM_FIELD = "_csrf";
        
        // Allowed file extensions for uploads
        public static final Set<String> ALLOWED_FILE_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "pdf", "doc", "docx", "xls", "xlsx"
        );
        
        // Maximum file upload sizes (in bytes)
        public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
        public static final long MAX_REQUEST_SIZE = 50 * 1024 * 1024; // 50MB
    }

    /**
     * Database Constants
     */
    public static final class Database {
        public static final int MIN_POOL_SIZE = 5;
        public static final int MAX_POOL_SIZE = 20;
        public static final Duration CONNECTION_TIMEOUT = Duration.ofSeconds(30);
        public static final Duration IDLE_TIMEOUT = Duration.ofMinutes(10);
        public static final Duration MAX_LIFETIME = Duration.ofHours(2);
        public static final int BATCH_SIZE = 100;
    }

    /**
     * API Constants
     */
    public static final class Api {
        public static final String VERSION = "v1";
        public static final String BASE_PATH = "/api/" + VERSION;
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int MAX_PAGE_SIZE = 100;
        public static final Duration RATE_LIMIT_DURATION = Duration.ofHours(1);
        public static final int RATE_LIMIT_REQUESTS = 1000;
    }

    /**
     * Validation Constants
     */
    public static final class Validation {
        public static final int MIN_USERNAME_LENGTH = 3;
        public static final int MAX_USERNAME_LENGTH = 50;
        public static final int MAX_EMAIL_LENGTH = 255;
        public static final int MAX_NAME_LENGTH = 100;
        public static final String EMAIL_PATTERN = 
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        public static final String USERNAME_PATTERN = 
            "^[A-Za-z0-9._-]{3,50}$";
    }

    /**
     * Cache Constants
     */
    public static final class Cache {
        public static final Duration DEFAULT_TTL = Duration.ofMinutes(30);
        public static final Duration USER_CACHE_TTL = Duration.ofHours(1);
        public static final Duration STATIC_CACHE_TTL = Duration.ofDays(7);
        public static final int MAX_CACHE_SIZE = 10000;
    }

    /**
     * View/Template Constants
     */
    public static final class View {
        public static final String DEFAULT_LAYOUT = "layouts/default";
        public static final String ERROR_LAYOUT = "layouts/error";
        public static final String LOGIN_PAGE = "auth/login";
        public static final String REGISTER_PAGE = "auth/register";
        public static final String DASHBOARD_PAGE = "dashboard/index";
        public static final String ERROR_404_PAGE = "error/404";
        public static final String ERROR_500_PAGE = "error/500";
    }

    /**
     * Message Constants
     */
    public static final class Messages {
        public static final String ERROR_INVALID_CREDENTIALS = 
            "Invalid email or password";
        public static final String ERROR_ACCOUNT_LOCKED = 
            "Account is locked. Please try again later";
        public static final String ERROR_INVALID_TOKEN = 
            "Invalid or expired token";
        public static final String SUCCESS_REGISTRATION = 
            "Registration successful. Please check your email to verify your account";
        public static final String SUCCESS_PASSWORD_RESET = 
            "Password has been reset successfully";
    }

    /**
     * Feature Flags
     */
    public static final class Features {
        public static final boolean ENABLE_REGISTRATION = true;
        public static final boolean ENABLE_SOCIAL_LOGIN = false;
        public static final boolean ENABLE_2FA = false;
        public static final boolean ENABLE_API = true;
        public static final boolean MAINTENANCE_MODE = false;
    }

    /**
     * Onboarding Constants
     */
    public static final class Onboarding {
        public static final int TOTAL_STEPS = 3;
        public static final Set<String> BRAND_IDENTITIES = Set.of(
            "GUIDE", "EXPERT", "ENABLER"
        );
        public static final Set<String> DEVELOPMENT_FOCUSES = Set.of(
            "WEB", "MOBILE", "DESKTOP", "API"
        );
        public static final Set<String> TEAM_COLLABORATIONS = Set.of(
            "AGILE", "KANBAN", "WATERFALL"
        );
        public static final Set<String> AI_COMMUNICATION_STYLES = Set.of(
            "DETAILED", "CONCISE"
        );
    }
}
