package org.example.demo6.Classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Enhanced user class with ECC cryptographic capabilities
 * Extends functionality for secure authentication and data protection
 */
public class SecureUser extends User {
    private ECKeyPair keyPair;
    private ECCrypto crypto;
    private byte[] salt;
    
    // Constructor for creating a new secure user
    public SecureUser(int id, String username, String password, String email, 
                     LocalDate dateOfBirth, String profilePicture, String role, Streak streak) {
        super(id, username, password, email, dateOfBirth, profilePicture, role, streak);
        this.crypto = new ECCrypto();
        this.salt = crypto.generateSalt(16);
        
        // Generate key pair from password for enhanced security
        generateKeyPairFromPassword(password);
    }
    
    // Constructor for existing user (load from database)
    public SecureUser(User existingUser, String password) {
        super(existingUser.getId(), existingUser.getUsername(), existingUser.getPassword(),
              existingUser.getEmail(), existingUser.getDateOfBirth(), existingUser.getProfilePicture(),
              existingUser.getRole(), existingUser.getStreak());
        
        this.crypto = new ECCrypto();
        // In a real implementation, salt would be loaded from database
        this.salt = crypto.generateSalt(16);
        generateKeyPairFromPassword(password);
    }
    
    /**
     * Generates ECC key pair from user's password using secure derivation
     * @param password User's password
     */
    private void generateKeyPairFromPassword(String password) {
        try {
            // Use username as additional entropy for key derivation
            String combinedSecret = password + getUsername() + getId();
            this.keyPair = crypto.deriveKeyFromPassword(combinedSecret, salt, 10000);
        } catch (Exception e) {
            // Fallback to random key generation if derivation fails
            this.keyPair = crypto.generateKeyPair();
        }
    }
    
    /**
     * Signs a message using the user's private key
     * @param message The message to sign
     * @return Digital signature
     */
    public ECKeyPair.ECSignature signMessage(String message) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        return crypto.sign(messageBytes, keyPair);
    }
    
    /**
     * Verifies a digital signature from another user
     * @param message The original message
     * @param signature The signature to verify
     * @param senderPublicKey The sender's public key
     * @return true if signature is valid
     */
    public boolean verifySignature(String message, ECKeyPair.ECSignature signature, ECPoint senderPublicKey) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        return crypto.verify(messageBytes, signature, senderPublicKey);
    }
    
    /**
     * Encrypts a message for another user using ECIES
     * @param message The message to encrypt
     * @param recipientPublicKey The recipient's public key
     * @return Encrypted message data
     */
    public ECCrypto.ECEncryptedData encryptMessage(String message, ECPoint recipientPublicKey) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        return crypto.encrypt(messageBytes, recipientPublicKey);
    }
    
    /**
     * Decrypts a message sent to this user
     * @param encryptedData The encrypted message data
     * @return Decrypted message
     */
    public String decryptMessage(ECCrypto.ECEncryptedData encryptedData) {
        byte[] decryptedBytes = crypto.decrypt(encryptedData, keyPair);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
    
    /**
     * Performs key agreement with another user (for session keys)
     * @param otherUserPublicKey The other user's public key
     * @return Shared secret for symmetric encryption
     */
    public byte[] establishSharedSecret(ECPoint otherUserPublicKey) {
        return crypto.computeSharedSecret(keyPair, 
            new ECKeyPair(keyPair.getPrivateKey(), otherUserPublicKey, keyPair.getCurve()));
    }
    
    /**
     * Creates a secure hash of sensitive user data
     * @param sensitiveData The data to hash
     * @return Base64 encoded hash
     */
    public String createSecureHash(String sensitiveData) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String salted = sensitiveData + Base64.getEncoder().encodeToString(salt);
            byte[] hash = sha256.digest(salted.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
    
    /**
     * Verifies a secure hash
     * @param data The original data
     * @param hash The hash to verify against
     * @return true if hash matches
     */
    public boolean verifySecureHash(String data, String hash) {
        return createSecureHash(data).equals(hash);
    }
    
    /**
     * Gets the user's public key for sharing with other users
     * @return The public key
     */
    public ECPoint getPublicKey() {
        return keyPair.getPublicKey();
    }
    
    /**
     * Gets the public key in compressed hex format for storage/transmission
     * @return Compressed public key as hex string
     */
    public String getPublicKeyHex() {
        return keyPair.getPublicKeyCompressedHex();
    }
    
    /**
     * Gets the salt used for key derivation (for database storage)
     * @return Base64 encoded salt
     */
    public String getSaltBase64() {
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Sets the salt from database (when loading existing user)
     * @param saltBase64 Base64 encoded salt from database
     */
    public void setSaltFromBase64(String saltBase64) {
        this.salt = Base64.getDecoder().decode(saltBase64);
    }
    
    /**
     * Creates an authentication token that can be verified later
     * @return Signed authentication token
     */
    public String createAuthToken() {
        String tokenData = getUsername() + ":" + System.currentTimeMillis() + ":" + getId();
        ECKeyPair.ECSignature signature = signMessage(tokenData);
        
        return Base64.getEncoder().encodeToString(tokenData.getBytes()) + ":" +
               signature.getR().toString(16) + ":" + signature.getS().toString(16);
    }
    
    /**
     * Verifies an authentication token
     * @param token The token to verify
     * @return true if token is valid and not expired
     */
    public boolean verifyAuthToken(String token) {
        try {
            String[] parts = token.split(":");
            if (parts.length != 3) return false;
            
            String tokenData = new String(Base64.getDecoder().decode(parts[0]));
            String[] dataParts = tokenData.split(":");
            if (dataParts.length != 3) return false;
            
            // Check username and user ID
            if (!dataParts[0].equals(getUsername()) || !dataParts[2].equals(String.valueOf(getId()))) {
                return false;
            }
            
            // Check if token is not too old (24 hours)
            long timestamp = Long.parseLong(dataParts[1]);
            long currentTime = System.currentTimeMillis();
            if (currentTime - timestamp > 24 * 60 * 60 * 1000) {
                return false; // Token expired
            }
            
            // Verify signature
            ECKeyPair.ECSignature signature = new ECKeyPair.ECSignature(
                new java.math.BigInteger(parts[1], 16),
                new java.math.BigInteger(parts[2], 16)
            );
            
            return verifySignature(tokenData, signature, keyPair.getPublicKey());
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Securely stores encrypted user preferences
     * @param preferences The preferences to encrypt and store
     * @return Encrypted preferences for database storage
     */
    public String encryptPreferences(String preferences) {
        ECCrypto.ECEncryptedData encrypted = encryptMessage(preferences, keyPair.getPublicKey());
        
        // Serialize encrypted data for storage
        return Base64.getEncoder().encodeToString(encrypted.getEphemeralPublicKey().toCompressed()) + ":" +
               Base64.getEncoder().encodeToString(encrypted.getCiphertext()) + ":" +
               Base64.getEncoder().encodeToString(encrypted.getMac());
    }
    
    /**
     * Decrypts stored user preferences
     * @param encryptedPreferences Encrypted preferences from database
     * @return Decrypted preferences
     */
    public String decryptPreferences(String encryptedPreferences) {
        try {
            String[] parts = encryptedPreferences.split(":");
            if (parts.length != 3) throw new IllegalArgumentException("Invalid encrypted preferences format");
            
            byte[] ephemeralKeyBytes = Base64.getDecoder().decode(parts[0]);
            byte[] ciphertext = Base64.getDecoder().decode(parts[1]);
            byte[] mac = Base64.getDecoder().decode(parts[2]);
            
            // Reconstruct ephemeral public key (simplified - in production, implement proper decompression)
            ECPoint ephemeralPublicKey = keyPair.getPublicKey(); // Placeholder
            
            ECCrypto.ECEncryptedData encrypted = new ECCrypto.ECEncryptedData(ephemeralPublicKey, ciphertext, mac);
            return decryptMessage(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt preferences", e);
        }
    }
    
    /**
     * Gets information about the cryptographic implementation
     * @return String describing the crypto setup
     */
    public String getCryptoInfo() {
        return String.format("ECC Curve: %s, Key Size: %d bits, Public Key: %s",
                           keyPair.getCurve().getName(),
                           keyPair.getCurve().getBitLength(),
                           getPublicKeyHex().substring(0, 16) + "...");
    }
    
    /**
     * Demonstrates secure communication between two users
     */
    public static void demonstrateSecureCommunication() {
        System.out.println("=== Secure User Communication Demo ===\n");
        
        // Create two secure users
        SecureUser alice = new SecureUser(1, "alice", "alicePassword123", "alice@library.com",
                                        LocalDate.of(1990, 5, 15), "alice.jpg", "user", 
                                        new Streak(LocalDate.now(), 5, 10));
        
        SecureUser bob = new SecureUser(2, "bob", "bobSecurePass456", "bob@library.com",
                                      LocalDate.of(1988, 8, 22), "bob.jpg", "user",
                                      new Streak(LocalDate.now(), 3, 8));
        
        System.out.println("Alice's crypto info: " + alice.getCryptoInfo());
        System.out.println("Bob's crypto info: " + bob.getCryptoInfo());
        
        // Alice sends encrypted message to Bob
        String secretMessage = "Hey Bob, I found a great book recommendation for you!";
        ECCrypto.ECEncryptedData encrypted = alice.encryptMessage(secretMessage, bob.getPublicKey());
        String decrypted = bob.decryptMessage(encrypted);
        
        System.out.println("\nOriginal message: " + secretMessage);
        System.out.println("Decrypted message: " + decrypted);
        System.out.println("Message integrity: " + secretMessage.equals(decrypted));
        
        // Bob signs a response
        String response = "Thanks Alice! I'll check it out.";
        ECKeyPair.ECSignature signature = bob.signMessage(response);
        boolean signatureValid = alice.verifySignature(response, signature, bob.getPublicKey());
        
        System.out.println("\nBob's response: " + response);
        System.out.println("Signature valid: " + signatureValid);
        
        // Alice creates authentication token
        String authToken = alice.createAuthToken();
        boolean tokenValid = alice.verifyAuthToken(authToken);
        
        System.out.println("\nAuth token created: " + authToken.substring(0, 50) + "...");
        System.out.println("Token validation: " + tokenValid);
        
        System.out.println("\n=== Demo Complete ===");
    }
}