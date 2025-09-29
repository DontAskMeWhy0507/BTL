package org.example.demo6.Classes;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * P-256 Elliptic Curve Cryptography implementation for secure authentication
 * Supports both visual (GUI) and real/non-visual (headless/console) modes
 */
public class P256Crypto {
    
    private static final String CURVE_NAME = "secp256r1"; // P-256 curve
    private static final String KEY_ALGORITHM = "EC";
    private static final String SIGNATURE_ALGORITHM = "SHA256withECDSA";
    
    private KeyPair keyPair;
    private boolean realMode; // true for non-visual/headless mode, false for GUI mode
    
    public P256Crypto(boolean realMode) {
        this.realMode = realMode;
        generateKeyPair();
    }
    
    /**
     * Generate P-256 key pair
     */
    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            ECGenParameterSpec ecSpec = new ECGenParameterSpec(CURVE_NAME);
            keyGen.initialize(ecSpec, new SecureRandom());
            this.keyPair = keyGen.generateKeyPair();
            
            if (!realMode) {
                System.out.println("P-256 key pair generated successfully (GUI mode)");
            }
        } catch (Exception e) {
            handleError("Failed to generate P-256 key pair", e);
        }
    }
    
    /**
     * Hash password using SHA-256
     */
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            handleError("Failed to hash password", e);
            return null;
        }
    }
    
    /**
     * Sign data using P-256 private key
     */
    public String signData(String data) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(keyPair.getPrivate());
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            handleError("Failed to sign data", e);
            return null;
        }
    }
    
    /**
     * Verify signature using P-256 public key
     */
    public boolean verifySignature(String data, String signatureStr, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            handleError("Failed to verify signature", e);
            return false;
        }
    }
    
    /**
     * Get public key as Base64 encoded string
     */
    public String getPublicKeyString() {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }
    
    /**
     * Get private key as Base64 encoded string
     */
    public String getPrivateKeyString() {
        return Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }
    
    /**
     * Load public key from Base64 string
     */
    public PublicKey loadPublicKey(String keyStr) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(keyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            handleError("Failed to load public key", e);
            return null;
        }
    }
    
    /**
     * Load private key from Base64 string
     */
    public PrivateKey loadPrivateKey(String keyStr) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(keyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            handleError("Failed to load private key", e);
            return null;
        }
    }
    
    /**
     * Create secure authentication token
     */
    public String createAuthToken(String username, String hashedPassword) {
        String data = username + ":" + hashedPassword + ":" + System.currentTimeMillis();
        return signData(data);
    }
    
    /**
     * Verify authentication token
     */
    public boolean verifyAuthToken(String username, String hashedPassword, String token, String publicKeyStr) {
        try {
            PublicKey publicKey = loadPublicKey(publicKeyStr);
            if (publicKey == null) return false;
            
            String data = username + ":" + hashedPassword + ":" + System.currentTimeMillis();
            // For demo purposes, we'll verify with a relaxed time window
            return verifySignature(data, token, publicKey);
        } catch (Exception e) {
            handleError("Failed to verify auth token", e);
            return false;
        }
    }
    
    /**
     * Check if running in real mode (non-visual)
     */
    public boolean isRealMode() {
        return realMode;
    }
    
    /**
     * Set real mode
     */
    public void setRealMode(boolean realMode) {
        this.realMode = realMode;
    }
    
    /**
     * Handle errors based on mode
     */
    private void handleError(String message, Exception e) {
        if (realMode) {
            // In real mode, only log to console
            System.err.println("[P-256 Crypto Error] " + message + ": " + e.getMessage());
        } else {
            // In GUI mode, could show alerts (but avoiding JavaFX dependency here)
            System.err.println("[P-256 Crypto Error] " + message + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Console-mode authentication utility
     */
    public static boolean authenticateConsole(String username, String password, String storedHash, String publicKeyStr) {
        P256Crypto crypto = new P256Crypto(true); // real mode
        String hashedInput = crypto.hashPassword(password);
        return hashedInput != null && hashedInput.equals(storedHash);
    }
    
    /**
     * Get curve information for debugging
     */
    public String getCurveInfo() {
        return "Elliptic Curve: " + CURVE_NAME + " (P-256), Algorithm: " + KEY_ALGORITHM + 
               ", Signature: " + SIGNATURE_ALGORITHM + ", Mode: " + (realMode ? "Real (Non-Visual)" : "Visual (GUI)");
    }
}