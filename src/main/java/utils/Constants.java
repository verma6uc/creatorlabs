package utils;

import java.time.Duration;
import java.util.List;

/**
 * Application-wide constants
 */
public final class Constants {
    private Constants() {
        throw new AssertionError("Constants class should not be instantiated");
    }

    public static final class App {
        public static final String NAME = "CreatorLabs";
        public static final String VERSION = "1.0.0";
        public static final String ENCODING = "UTF-8";
        public static final String DEFAULT_TIMEZONE = "UTC";
        public static final String DEFAULT_LOCALE = "en_US";
        public static final int SESSION_TIMEOUT = 30; // minutes
    }

    public static final class Security {
        public static final int SALT_LENGTH = 32;
        public static final int HASH_ITERATIONS = 10000;
        public static final int TOKEN_LENGTH = 32;
        public static final Duration TOKEN_VALIDITY = Duration.ofHours(24);
        public static final int MAX_LOGIN_ATTEMPTS = 5;
        public static final Duration LOGIN_LOCKOUT_DURATION = Duration.ofMinutes(30);
        public static final int PASSWORD_MIN_LENGTH = 12;
        public static final int PASSWORD_MAX_LENGTH = 128;
        public static final int SESSION_TIMEOUT = 30; // minutes
    }

    public static final class Database {
        public static final int MIN_POOL_SIZE = 5;
        public static final int MAX_POOL_SIZE = 20;
        public static final int CONNECTION_TIMEOUT = 30000; // milliseconds
        public static final int IDLE_TIMEOUT = 600000; // milliseconds
        public static final String SCHEMA_VERSION_TABLE = "schema_version";
    }

    public static final class Cache {
        public static final int DEFAULT_CACHE_SIZE = 1000;
        public static final Duration DEFAULT_CACHE_DURATION = Duration.ofMinutes(30);
        public static final Duration CACHE_CLEANUP_INTERVAL = Duration.ofMinutes(15);
    }

    public static final class Api {
        public static final String BASE_PATH = "/api/v1";
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int MAX_PAGE_SIZE = 100;
        public static final Duration RATE_LIMIT_WINDOW = Duration.ofMinutes(1);
        public static final int RATE_LIMIT_REQUESTS = 60;
    }

    public static final class View {
        public static final String ERROR_404 = "/WEB-INF/error/404.jsp";
        public static final String ERROR_500 = "/WEB-INF/error/500.jsp";
        public static final String DASHBOARD = "/WEB-INF/views/dashboard/index.jsp";
        public static final String SETTINGS = "/WEB-INF/views/dashboard/settings.jsp";
    }

    public static final class Validation {
        public static final String EMAIL_PATTERN = 
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,20}$";
        public static final String PASSWORD_PATTERN = 
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$";
        public static final int MAX_USERNAME_LENGTH = 20;
        public static final int MAX_EMAIL_LENGTH = 255;
        public static final int MAX_NAME_LENGTH = 100;
    }

    public static final class Features {
        public static final boolean ENABLE_WEBSOCKET = true;
        public static final boolean ENABLE_FILE_UPLOAD = true;
        public static final boolean ENABLE_NOTIFICATIONS = true;
        public static final boolean ENABLE_ANALYTICS = true;
    }

    public static final class Messages {
        public static final String INVALID_CREDENTIALS = "Invalid username or password";
        public static final String ACCOUNT_LOCKED = "Account is locked. Please try again later";
        public static final String SESSION_EXPIRED = "Your session has expired. Please log in again";
        public static final String INVALID_TOKEN = "Invalid or expired token";
        public static final String UNAUTHORIZED = "Unauthorized access";
        public static final String INTERNAL_ERROR = "An internal error occurred";
    }

    public static final class Onboarding {
        public static final int TOTAL_STEPS = 4;
        public static final Duration STEP_TIMEOUT = Duration.ofMinutes(30);
        public static final boolean REQUIRE_EMAIL_VERIFICATION = true;
        public static final Duration EMAIL_VERIFICATION_TIMEOUT = Duration.ofHours(24);

        public static final List<String> BRAND_IDENTITIES = List.of(
            "Modern & Minimalist",
            "Bold & Creative",
            "Professional & Corporate",
            "Friendly & Approachable",
            "Luxury & Premium",
            "Tech & Innovation",
            "Eco & Sustainable",
            "Playful & Fun"
        );

        public static final List<String> DEVELOPMENT_FOCUSES = List.of(
            "Web Development",
            "Mobile Apps",
            "E-commerce",
            "Enterprise Solutions",
            "AI & Machine Learning",
            "Cloud Services",
            "IoT Applications",
            "Blockchain"
        );

        public static final List<String> TEAM_COLLABORATIONS = List.of(
            "Remote First",
            "Hybrid",
            "Office Based",
            "Flexible",
            "Project Based",
            "Global Teams",
            "Cross-functional",
            "Agile Squads"
        );

        public static final List<String> AI_COMMUNICATION_STYLES = List.of(
            "Professional & Formal",
            "Casual & Friendly",
            "Technical & Detailed",
            "Simple & Clear",
            "Creative & Inspiring",
            "Educational & Guiding",
            "Direct & Concise",
            "Collaborative & Interactive"
        );
    }
}
