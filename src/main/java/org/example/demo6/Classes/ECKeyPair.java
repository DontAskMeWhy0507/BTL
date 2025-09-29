package org.example.demo6.Classes;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Represents an elliptic curve key pair consisting of a private key (scalar) 
 * and public key (point on the curve)
 */
public class ECKeyPair {
    private final BigInteger privateKey;
    private final ECPoint publicKey;
    private final EllipticCurve curve;
    
    /**
     * Creates a new key pair with the given private and public keys
     * @param privateKey The private key (scalar)
     * @param publicKey The public key (curve point)
     * @param curve The elliptic curve
     */
    public ECKeyPair(BigInteger privateKey, ECPoint publicKey, EllipticCurve curve) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.curve = curve;
        
        // Validate key pair
        validateKeyPair();
    }
    
    /**
     * Generates a new random key pair for the given curve
     * @param curve The elliptic curve to generate keys for
     * @return A new random key pair
     */
    public static ECKeyPair generate(EllipticCurve curve) {
        SecureRandom random = new SecureRandom();
        BigInteger privateKey;
        
        // Generate random private key in range [1, n-1]
        do {
            privateKey = new BigInteger(curve.getN().bitLength(), random);
        } while (privateKey.equals(BigInteger.ZERO) || privateKey.compareTo(curve.getN()) >= 0);
        
        // Compute public key: Q = d * G
        ECPoint publicKey = curve.getG().multiply(privateKey, curve);
        
        return new ECKeyPair(privateKey, publicKey, curve);
    }
    
    /**
     * Creates a key pair from a private key (computes the public key)
     * @param privateKey The private key
     * @param curve The elliptic curve
     * @return A new key pair
     */
    public static ECKeyPair fromPrivateKey(BigInteger privateKey, EllipticCurve curve) {
        if (privateKey.equals(BigInteger.ZERO) || privateKey.compareTo(curve.getN()) >= 0) {
            throw new IllegalArgumentException("Private key must be in range [1, n-1]");
        }
        
        ECPoint publicKey = curve.getG().multiply(privateKey, curve);
        return new ECKeyPair(privateKey, publicKey, curve);
    }
    
    /**
     * Creates a key pair from a private key hex string
     * @param privateKeyHex The private key as hex string
     * @param curve The elliptic curve
     * @return A new key pair
     */
    public static ECKeyPair fromPrivateKeyHex(String privateKeyHex, EllipticCurve curve) {
        BigInteger privateKey = new BigInteger(privateKeyHex, 16);
        return fromPrivateKey(privateKey, curve);
    }
    
    /**
     * Validates that the key pair is mathematically correct
     */
    private void validateKeyPair() {
        // Check private key is in valid range
        if (privateKey.equals(BigInteger.ZERO) || privateKey.compareTo(curve.getN()) >= 0) {
            throw new IllegalArgumentException("Private key must be in range [1, n-1]");
        }
        
        // Check public key is on the curve
        if (!publicKey.isOnCurve(curve)) {
            throw new IllegalArgumentException("Public key point is not on the curve");
        }
        
        // Check that public key = private key * generator
        ECPoint computedPublic = curve.getG().multiply(privateKey, curve);
        if (!publicKey.equals(computedPublic)) {
            throw new IllegalArgumentException("Public key does not match private key");
        }
    }
    
    public BigInteger getPrivateKey() {
        return privateKey;
    }
    
    public ECPoint getPublicKey() {
        return publicKey;
    }
    
    public EllipticCurve getCurve() {
        return curve;
    }
    
    /**
     * Gets the private key as a hex string
     * @return Private key hex representation
     */
    public String getPrivateKeyHex() {
        return privateKey.toString(16).toUpperCase();
    }
    
    /**
     * Gets the public key as a hex string (uncompressed format)
     * @return Public key hex representation
     */
    public String getPublicKeyHex() {
        return bytesToHex(publicKey.toUncompressed()).toUpperCase();
    }
    
    /**
     * Gets the public key as a hex string (compressed format)
     * @return Compressed public key hex representation
     */
    public String getPublicKeyCompressedHex() {
        return bytesToHex(publicKey.toCompressed()).toUpperCase();
    }
    
    /**
     * Performs ECDH (Elliptic Curve Diffie-Hellman) key agreement
     * @param otherPublicKey The other party's public key
     * @return The shared secret point
     */
    public ECPoint ecdh(ECPoint otherPublicKey) {
        if (!otherPublicKey.isOnCurve(curve)) {
            throw new IllegalArgumentException("Other public key is not on the same curve");
        }
        
        // Shared secret = private_key * other_public_key
        return otherPublicKey.multiply(privateKey, curve);
    }
    
    /**
     * Performs ECDH and returns the x-coordinate as shared secret
     * @param otherPublicKey The other party's public key
     * @return The shared secret as BigInteger (x-coordinate)
     */
    public BigInteger ecdhSecret(ECPoint otherPublicKey) {
        ECPoint sharedPoint = ecdh(otherPublicKey);
        return sharedPoint.getX();
    }
    
    /**
     * Derives a new key pair using a deterministic method (for hierarchical deterministic wallets)
     * This is a simplified version - full BIP32 implementation would be more complex
     * @param index The derivation index
     * @return A new derived key pair
     */
    public ECKeyPair derive(int index) {
        // Simple derivation: add index to private key (mod n)
        // In production, use proper HMAC-based derivation (BIP32)
        BigInteger derivedPrivate = privateKey.add(BigInteger.valueOf(index)).mod(curve.getN());
        return fromPrivateKey(derivedPrivate, curve);
    }
    
    /**
     * Creates a digital signature of a hash using ECDSA
     * Note: This is a basic implementation. Production code should use proper ECDSA with proper hash functions
     * @param messageHash The hash of the message to sign
     * @return ECSignature containing r and s values
     */
    public ECSignature sign(BigInteger messageHash) {
        SecureRandom random = new SecureRandom();
        BigInteger k, r, s = BigInteger.ZERO;
        
        do {
            // Generate random k in range [1, n-1]
            do {
                k = new BigInteger(curve.getN().bitLength(), random);
            } while (k.equals(BigInteger.ZERO) || k.compareTo(curve.getN()) >= 0);
            
            // Calculate r = (k * G).x mod n
            ECPoint kG = curve.getG().multiply(k, curve);
            r = kG.getX().mod(curve.getN());
            
            if (r.equals(BigInteger.ZERO)) {
                continue; // Try again with new k
            }
            
            // Calculate s = k^(-1) * (hash + r * private_key) mod n
            BigInteger kInv = k.modInverse(curve.getN());
            s = kInv.multiply(messageHash.add(r.multiply(privateKey))).mod(curve.getN());
            
        } while (s.equals(BigInteger.ZERO));
        
        return new ECSignature(r, s);
    }
    
    /**
     * Verifies a digital signature
     * @param messageHash The hash of the original message
     * @param signature The signature to verify
     * @return true if signature is valid, false otherwise
     */
    public boolean verify(BigInteger messageHash, ECSignature signature) {
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
     * Utility method to convert bytes to hex string
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ECKeyPair other = (ECKeyPair) obj;
        return Objects.equals(privateKey, other.privateKey) &&
               Objects.equals(publicKey, other.publicKey) &&
               Objects.equals(curve, other.curve);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(privateKey, publicKey, curve);
    }
    
    @Override
    public String toString() {
        return String.format("ECKeyPair{curve=%s, privateKey=%s, publicKey=%s}",
                           curve.getName(),
                           privateKey.toString(16),
                           publicKey.toString());
    }
    
    /**
     * Inner class representing an ECDSA signature
     */
    public static class ECSignature {
        private final BigInteger r;
        private final BigInteger s;
        
        public ECSignature(BigInteger r, BigInteger s) {
            this.r = r;
            this.s = s;
        }
        
        public BigInteger getR() {
            return r;
        }
        
        public BigInteger getS() {
            return s;
        }
        
        @Override
        public String toString() {
            return String.format("ECSignature{r=%s, s=%s}", r.toString(16), s.toString(16));
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            
            ECSignature other = (ECSignature) obj;
            return Objects.equals(r, other.r) && Objects.equals(s, other.s);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(r, s);
        }
    }
}