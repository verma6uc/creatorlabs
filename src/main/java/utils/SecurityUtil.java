package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for security operations.
 * Handles password hashing, token generation, and other security measures.
 */
public final class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = Constants.Security.SALT_LENGTH;
    private static final int ITERATIONS = Constants.Security.HASH_ITERATIONS;
    private static final int KEY_LENGTH = 256;
    
    // Cache of valid tokens with their creation timestamps
    private static final ConcurrentHashMap<String, TokenInfo> validTokens = new ConcurrentHashMap<>();

    /**
     * Token information record
     */
    private record TokenInfo(
        String userId,
        Instant createdAt,
        Instant expiresAt
    ) {}

    private SecurityUtil() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Generate a secure random salt
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Hash password using PBKDF2 with SHA-256
     */
    public static String hashPassword(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
            );
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            
            // Combine salt and hash
            byte[] combined = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hash, 0, combined, salt.length, hash.length);
            
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            logger.error("Error hashing password", e);
            throw new SecurityException("Error hashing password", e);
        }
    }

    /**
     * Verify password against stored hash
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            byte[] combined = Base64.getDecoder().decode(storedHash);
            
            // Extract salt and hash
            byte[] salt = new byte[SALT_LENGTH];
            byte[] hash = new byte[combined.length - SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);
            System.arraycopy(combined, SALT_LENGTH, hash, 0, hash.length);
            
            // Hash the input password with the same salt
            String newHash = hashPassword(password, salt);
            return MessageDigest.isEqual(
                combined,
                Base64.getDecoder().decode(newHash)
            );
        } catch (Exception e) {
            logger.error("Error verifying password", e);
            return false;
        }
    }

    /**
     * Generate a secure random token
     */
    public static String generateToken() {
        byte[] tokenBytes = new byte[Constants.Security.TOKEN_LENGTH];
        SECURE_RANDOM.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    /**
     * Generate and store a token with user ID
     */
    public static String generateToken(String userId) {
        String token = generateToken();
        Instant now = Instant.now();
        Instant expiresAt = now.plus(Constants.Security.TOKEN_VALIDITY);
        
        validTokens.put(token, new TokenInfo(userId, now, expiresAt));
        return token;
    }

    /**
     * Validate token and return associated user ID
     */
    public static boolean validateToken(String token) {
        TokenInfo info = validTokens.get(token);
        if (info == null) {
            return false;
        }
        
        if (Instant.now().isAfter(info.expiresAt())) {
            validTokens.remove(token);
            return false;
        }
        
        return true;
    }

    /**
     * Get user ID associated with token
     */
    public static String getUserIdFromToken(String token) {
        TokenInfo info = validTokens.get(token);
        return info != null ? info.userId() : null;
    }

    /**
     * Invalidate token
     */
    public static void invalidateToken(String token) {
        validTokens.remove(token);
    }

    /**
     * Generate secure session ID
     */
    public static String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate CSRF token
     */
    public static String generateCsrfToken() {
        return generateToken();
    }

    /**
     * Hash string using SHA-256
     */
    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating SHA-256 hash", e);
            throw new SecurityException("Error generating SHA-256 hash", e);
        }
    }

    /**
     * Generate API key
     */
    public static String generateApiKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Generate reset token with expiration
     */
    public static String generateResetToken() {
        String token = generateToken();
        long expiration = Instant.now().plusSeconds(
            Constants.Security.TOKEN_VALIDITY.getSeconds()
        ).getEpochSecond();
        return token + "." + expiration;
    }

    /**
     * Validate reset token
     */
    public static boolean isValidResetToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) {
                return false;
            }
            
            long expiration = Long.parseLong(parts[1]);
            return Instant.now().getEpochSecond() < expiration;
        } catch (Exception e) {
            logger.error("Error validating reset token", e);
            return false;
        }
    }

    /**
     * Sanitize user input to prevent XSS
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("[<>\"'&]", "");
    }

    /**
     * Generate random password
     */
    public static String generateRandomPassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = upper.toLowerCase();
        String digits = "0123456789";
        String special = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        String all = upper + lower + digits + special;
        
        StringBuilder password = new StringBuilder();
        // Ensure at least one of each required character type
        password.append(upper.charAt(SECURE_RANDOM.nextInt(upper.length())));
        password.append(lower.charAt(SECURE_RANDOM.nextInt(lower.length())));
        password.append(digits.charAt(SECURE_RANDOM.nextInt(digits.length())));
        password.append(special.charAt(SECURE_RANDOM.nextInt(special.length())));
        
        // Fill remaining length with random characters
        while (password.length() < Constants.Security.PASSWORD_MIN_LENGTH) {
            password.append(all.charAt(SECURE_RANDOM.nextInt(all.length())));
        }
        
        // Shuffle the password
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = SECURE_RANDOM.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }
        
        return new String(passwordArray);
    }

    /**
     * Mask sensitive data (e.g., credit card numbers, SSN)
     */
    public static String maskSensitiveData(String data, int visibleChars) {
        if (data == null || data.length() <= visibleChars) {
            return data;
        }
        
        return "*".repeat(data.length() - visibleChars) + 
               data.substring(data.length() - visibleChars);
    }

    /**
     * Generate nonce for CSP
     */
    public static String generateNonce() {
        byte[] nonceBytes = new byte[16];
        SECURE_RANDOM.nextBytes(nonceBytes);
        return Base64.getEncoder().encodeToString(nonceBytes);
    }

    /**
     * Cleanup expired tokens
     */
    public static void cleanupExpiredTokens() {
        Instant now = Instant.now();
        validTokens.entrySet().removeIf(entry -> now.isAfter(entry.getValue().expiresAt()));
    }
}
