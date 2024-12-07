package utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for input validation.
 * Provides methods for validating common input patterns and data structures.
 */
public final class ValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

    // Common validation patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.Validation.EMAIL_PATTERN);
    private static final Pattern USERNAME_PATTERN = Pattern.compile(Constants.Validation.USERNAME_PATTERN);
    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?[1-9]\\d{1,14}$"
    );

    private ValidationUtil() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Validation result class
     */
    public static class ValidationResult {
        private final Map<String, String> errors = new HashMap<>();
        
        public void addError(String field, String message) {
            errors.put(field, message);
        }
        
        public boolean isValid() {
            return errors.isEmpty();
        }
        
        public Map<String, String> getErrors() {
            return new HashMap<>(errors);
        }
        
        public String getError(String field) {
            return errors.get(field);
        }
    }

    /**
     * Validate email address
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate username
     */
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Validate password strength
     */
    public static ValidationResult validatePassword(String password) {
        ValidationResult result = new ValidationResult();
        
        if (password == null || password.length() < Constants.Security.PASSWORD_MIN_LENGTH) {
            result.addError("password", "Password must be at least " + 
                Constants.Security.PASSWORD_MIN_LENGTH + " characters long");
            return result;
        }
        
        if (password.length() > Constants.Security.PASSWORD_MAX_LENGTH) {
            result.addError("password", "Password cannot exceed " + 
                Constants.Security.PASSWORD_MAX_LENGTH + " characters");
            return result;
        }
        
        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            result.addError("password", "Password must contain at least one uppercase letter");
        }
        
        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            result.addError("password", "Password must contain at least one lowercase letter");
        }
        
        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            result.addError("password", "Password must contain at least one number");
        }
        
        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            result.addError("password", "Password must contain at least one special character");
        }
        
        return result;
    }

    /**
     * Validate URL
     */
    public static boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    /**
     * Validate phone number
     */
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validate string length
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        return str != null && str.length() >= minLength && str.length() <= maxLength;
    }

    /**
     * Validate numeric range
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Validate numeric range for long values
     */
    public static boolean isInRange(long value, long min, long max) {
        return value >= min && value <= max;
    }

    /**
     * Validate numeric range for double values
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Validate collection size
     */
    public static boolean isValidCollectionSize(Collection<?> collection, int minSize, int maxSize) {
        return collection != null && collection.size() >= minSize && collection.size() <= maxSize;
    }

    /**
     * Validate file extension
     */
    public static boolean isValidFileExtension(String fileName, Collection<String> allowedExtensions) {
        if (fileName == null || allowedExtensions == null) {
            return false;
        }
        
        String extension = getFileExtension(fileName).toLowerCase();
        return allowedExtensions.contains(extension);
    }

    /**
     * Get file extension
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
    }

    /**
     * Validate with custom predicate
     */
    public static <T> boolean validate(T value, Predicate<T> validator) {
        try {
            return validator.test(value);
        } catch (Exception e) {
            logger.error("Error during custom validation", e);
            return false;
        }
    }

    /**
     * Sanitize string input
     */
    public static String sanitize(String input) {
        if (input == null) {
            return "";
        }
        
        // Remove any HTML tags
        input = input.replaceAll("<[^>]*>", "");
        
        // Convert special characters to HTML entities
        input = input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;")
                    .replace("/", "&#x2F;");
        
        return input.trim();
    }

    /**
     * Validate required fields
     */
    public static ValidationResult validateRequired(Map<String, String> fields) {
        ValidationResult result = new ValidationResult();
        
        fields.forEach((field, value) -> {
            if (value == null || value.trim().isEmpty()) {
                result.addError(field, field + " is required");
            }
        });
        
        return result;
    }

    /**
     * Validate object not null
     */
    public static <T> ValidationResult validateNotNull(String fieldName, T value) {
        ValidationResult result = new ValidationResult();
        
        if (Objects.isNull(value)) {
            result.addError(fieldName, fieldName + " cannot be null");
        }
        
        return result;
    }

    /**
     * Combine multiple validation results
     */
    public static ValidationResult combine(ValidationResult... results) {
        ValidationResult combined = new ValidationResult();
        
        for (ValidationResult result : results) {
            result.getErrors().forEach(combined::addError);
        }
        
        return combined;
    }

    /**
     * Log validation errors
     */
    public static void logValidationErrors(String context, ValidationResult result) {
        if (!result.isValid()) {
            logger.warn("Validation errors in {}: {}", context, result.getErrors());
        }
    }
}
