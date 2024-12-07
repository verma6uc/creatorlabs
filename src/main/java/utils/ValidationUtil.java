package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class for input validation
 */
public final class ValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

    private ValidationUtil() {
        throw new AssertionError("ValidationUtil class should not be instantiated");
    }

    /**
     * Validation result record
     */
    public record ValidationResult(
        boolean isValid,
        List<String> errors
    ) {
        public static ValidationResult success() {
            return new ValidationResult(true, new ArrayList<>());
        }

        public static ValidationResult failure(String error) {
            List<String> errors = new ArrayList<>();
            errors.add(error);
            return new ValidationResult(false, errors);
        }

        public static ValidationResult failure(List<String> errors) {
            return new ValidationResult(false, errors);
        }
    }

    /**
     * Validate email address
     */
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.failure("Email is required");
        }

        if (email.length() > Constants.Validation.MAX_EMAIL_LENGTH) {
            return ValidationResult.failure("Email is too long");
        }

        if (!Pattern.matches(Constants.Validation.EMAIL_PATTERN, email)) {
            return ValidationResult.failure("Invalid email format");
        }

        return ValidationResult.success();
    }

    /**
     * Validate username
     */
    public static ValidationResult validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ValidationResult.failure("Username is required");
        }

        if (username.length() > Constants.Validation.MAX_USERNAME_LENGTH) {
            return ValidationResult.failure("Username is too long");
        }

        if (!Pattern.matches(Constants.Validation.USERNAME_PATTERN, username)) {
            return ValidationResult.failure("Username must be 3-20 characters long and contain only letters, numbers, underscores, and hyphens");
        }

        return ValidationResult.success();
    }

    /**
     * Validate password
     */
    public static ValidationResult validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.trim().isEmpty()) {
            errors.add("Password is required");
            return ValidationResult.failure(errors);
        }

        if (password.length() < Constants.Security.PASSWORD_MIN_LENGTH) {
            errors.add("Password must be at least " + Constants.Security.PASSWORD_MIN_LENGTH + " characters long");
        }

        if (password.length() > Constants.Security.PASSWORD_MAX_LENGTH) {
            errors.add("Password cannot exceed " + Constants.Security.PASSWORD_MAX_LENGTH + " characters");
        }

        if (!Pattern.matches(Constants.Validation.PASSWORD_PATTERN, password)) {
            errors.add("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }

        return errors.isEmpty() ? ValidationResult.success() : ValidationResult.failure(errors);
    }

    /**
     * Validate name
     */
    public static ValidationResult validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ValidationResult.failure("Name is required");
        }

        if (name.length() > Constants.Validation.MAX_NAME_LENGTH) {
            return ValidationResult.failure("Name is too long");
        }

        return ValidationResult.success();
    }

    /**
     * Sanitize input to prevent XSS
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("[<>\"'&]", "");
    }

    /**
     * Validate onboarding step
     */
    public static ValidationResult validateOnboardingStep(int step) {
        if (step < 1 || step > Constants.Onboarding.TOTAL_STEPS) {
            return ValidationResult.failure("Invalid onboarding step");
        }
        return ValidationResult.success();
    }

    /**
     * Validate onboarding selection
     */
    public static ValidationResult validateOnboardingSelection(String selection, List<String> validOptions) {
        if (selection == null || selection.trim().isEmpty()) {
            return ValidationResult.failure("Selection is required");
        }

        if (!validOptions.contains(selection)) {
            return ValidationResult.failure("Invalid selection");
        }

        return ValidationResult.success();
    }
}
