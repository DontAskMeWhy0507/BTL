package org.example.demo6.Classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * High-level elliptic curve cryptography operations
 * Provides encryption, decryption, signing, verification, and key derivation
 */
public class ECCrypto {
    private final EllipticCurve curve;
    private final SecureRandom random;
    
    /**
     * Creates a new ECCrypto instance with the specified curve
     * @param curve The elliptic curve to use
     */
    public ECCrypto(EllipticCurve curve) {
        this.curve = curve;
        this.random = new SecureRandom();
    }
    
    /**
     * Creates a new ECCrypto instance with the default recommended curve (secp256r1)
     */
    public ECCrypto() {
        this(ECDomainParameters.getRecommendedCurve());
    }
    
    /**
     * Generates a new key pair
     * @return A new ECKeyPair
     */
    public ECKeyPair generateKeyPair() {
        return ECKeyPair.generate(curve);
    }
    
    /**
     * Encrypts data using ECIES (Elliptic Curve Integrated Encryption Scheme)
     * This is a simplified implementation for demonstration purposes
     * @param data The data to encrypt
     * @param recipientPublicKey The recipient's public key
     * @return Encrypted data structure
     */
    public ECEncryptedData encrypt(byte[] data, ECPoint recipientPublicKey) {
        try {
            // Generate ephemeral key pair
            ECKeyPair ephemeralKeyPair = ECKeyPair.generate(curve);
            
            // Perform ECDH to get shared secret
            ECPoint sharedPoint = ephemeralKeyPair.ecdh(recipientPublicKey);
            byte[] sharedSecret = sharedPoint.getX().toByteArray();
            
            // Derive encryption key from shared secret using SHA-256
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] encryptionKey = sha256.digest(sharedSecret);
            
            // Simple XOR encryption (in production, use AES)
            byte[] encryptedData = xorEncrypt(data, encryptionKey);
            
            // Create MAC for integrity (simplified)
            byte[] mac = sha256.digest(concat(encryptedData, sharedSecret));
            
            return new ECEncryptedData(
                ephemeralKeyPair.getPublicKey(),
                encryptedData,
                Arrays.copyOf(mac, 16) // Use first 16 bytes as MAC
            );
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Decrypts data using ECIES
     * @param encryptedData The encrypted data structure
     * @param recipientKeyPair The recipient's key pair
     * @return The decrypted data
     */
    public byte[] decrypt(ECEncryptedData encryptedData, ECKeyPair recipientKeyPair) {
        try {
            // Perform ECDH with ephemeral public key
            ECPoint sharedPoint = recipientKeyPair.ecdh(encryptedData.getEphemeralPublicKey());
            byte[] sharedSecret = sharedPoint.getX().toByteArray();
            
            // Derive encryption key from shared secret
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] encryptionKey = sha256.digest(sharedSecret);
            
            // Verify MAC
            byte[] expectedMac = sha256.digest(concat(encryptedData.getCiphertext(), sharedSecret));
            byte[] actualMac = encryptedData.getMac();
            
            if (!Arrays.equals(Arrays.copyOf(expectedMac, 16), actualMac)) {
                throw new RuntimeException("MAC verification failed - data may be corrupted");
            }
            
            // Decrypt data
            return xorEncrypt(encryptedData.getCiphertext(), encryptionKey);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Signs a message using ECDSA
     * @param message The message to sign
     * @param keyPair The signing key pair
     * @return The signature
     */
    public ECKeyPair.ECSignature sign(byte[] message, ECKeyPair keyPair) {
        try {
            // Hash the message
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(message);
            BigInteger messageHash = new BigInteger(1, hash);
            
            return keyPair.sign(messageHash);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Verifies a signature
     * @param message The original message
     * @param signature The signature to verify
     * @param publicKey The signer's public key
     * @return true if signature is valid, false otherwise
     */
    public boolean verify(byte[] message, ECKeyPair.ECSignature signature, ECPoint publicKey) {
        try {
            // Hash the message
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(message);
            BigInteger messageHash = new BigInteger(1, hash);
            
            return verifySignature(messageHash, signature, publicKey);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Verifies an ECDSA signature directly
     * @param messageHash The hash of the message
     * @param signature The signature to verify
     * @param publicKey The signer's public key
     * @return true if signature is valid, false otherwise
     */
    private boolean verifySignature(BigInteger messageHash, ECKeyPair.ECSignature signature, ECPoint publicKey) {
        BigInteger r = signature.getR();
        BigInteger s = signature.getS();
        
        // Check signature components are in valid range
        if (r.equals(BigInteger.ZERO) || r.compareTo(curve.getN()) >= 0) {
            return false;
        }
        if (s.equals(BigInteger.ZERO) || s.compareTo(curve.getN()) >= 0) {
            return false;
        }
        
        // Calculate verification values
        BigInteger sInv = s.modInverse(curve.getN());
        BigInteger u1 = messageHash.multiply(sInv).mod(curve.getN());
        BigInteger u2 = r.multiply(sInv).mod(curve.getN());
        
        // Calculate point: u1 * G + u2 * public_key
        ECPoint point1 = curve.getG().multiply(u1, curve);
        ECPoint point2 = publicKey.multiply(u2, curve);
        ECPoint result = point1.add(point2, curve);
        
        if (result.isInfinity()) {
            return false;
        }
        
        // Check if r ≡ result.x (mod n)
        return r.equals(result.getX().mod(curve.getN()));
    }
    
    /**
     * Derives a key from a password using PBKDF2-like approach with ECC
     * @param password The password
     * @param salt The salt value
     * @param iterations Number of iterations
     * @return Derived key pair
     */
    public ECKeyPair deriveKeyFromPassword(String password, byte[] salt, int iterations) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            
            // Initial hash of password + salt
            byte[] passwordBytes = password.getBytes("UTF-8");
            byte[] combined = concat(passwordBytes, salt);
            byte[] hash = sha256.digest(combined);
            
            // Perform iterations
            for (int i = 0; i < iterations; i++) {
                hash = sha256.digest(concat(hash, salt));
            }
            
            // Convert to private key
            BigInteger privateKey = new BigInteger(1, hash).mod(curve.getN());
            if (privateKey.equals(BigInteger.ZERO)) {
                privateKey = BigInteger.ONE; // Ensure non-zero
            }
            
            return ECKeyPair.fromPrivateKey(privateKey, curve);
            
        } catch (Exception e) {
            throw new RuntimeException("Error deriving key from password", e);
        }
    }
    
    /**
     * Generates a secure random salt
     * @param length The length of the salt in bytes
     * @return Random salt
     */
    public byte[] generateSalt(int length) {
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * Computes ECDH shared secret between two key pairs
     * @param keyPair1 First key pair
     * @param keyPair2 Second key pair
     * @return Shared secret as byte array
     */
    public byte[] computeSharedSecret(ECKeyPair keyPair1, ECKeyPair keyPair2) {
        BigInteger secret = keyPair1.ecdhSecret(keyPair2.getPublicKey());
        return secret.toByteArray();
    }
    
    /**
     * Creates a deterministic key pair from a seed
     * @param seed The seed value
     * @return Deterministic key pair
     */
    public ECKeyPair keyPairFromSeed(byte[] seed) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(seed);
            
            BigInteger privateKey = new BigInteger(1, hash).mod(curve.getN());
            if (privateKey.equals(BigInteger.ZERO)) {
                privateKey = BigInteger.ONE;
            }
            
            return ECKeyPair.fromPrivateKey(privateKey, curve);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Validates that a public key is valid for the current curve
     * @param publicKey The public key to validate
     * @return true if valid, false otherwise
     */
    public boolean validatePublicKey(ECPoint publicKey) {
        return publicKey.isOnCurve(curve) && !publicKey.isInfinity();
    }
    
    /**
     * Gets the curve being used by this crypto instance
     * @return The elliptic curve
     */
    public EllipticCurve getCurve() {
        return curve;
    }
    
    // Utility methods
    
    /**
     * Simple XOR encryption/decryption
     * In production, use proper symmetric encryption like AES
     */
    private byte[] xorEncrypt(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }
    
    /**
     * Concatenates two byte arrays
     */
    private byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    
    /**
     * Converts hex string to byte array
     */
    public static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
    
    /**
     * Converts byte array to hex string
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
    
    /**
     * Data structure for encrypted data using ECIES
     */
    public static class ECEncryptedData {
        private final ECPoint ephemeralPublicKey;
        private final byte[] ciphertext;
        private final byte[] mac;
        
        public ECEncryptedData(ECPoint ephemeralPublicKey, byte[] ciphertext, byte[] mac) {
            this.ephemeralPublicKey = ephemeralPublicKey;
            this.ciphertext = ciphertext.clone();
            this.mac = mac.clone();
        }
        
        public ECPoint getEphemeralPublicKey() {
            return ephemeralPublicKey;
        }
        
        public byte[] getCiphertext() {
            return ciphertext.clone();
        }
        
        public byte[] getMac() {
            return mac.clone();
        }
        
        @Override
        public String toString() {
            return String.format("ECEncryptedData{ephemeralKey=%s, dataLength=%d}",
                               ephemeralPublicKey.toString(), ciphertext.length);
        }
    }
}