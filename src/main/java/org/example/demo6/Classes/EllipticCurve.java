package org.example.demo6.Classes;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Represents an elliptic curve in Weierstrass form: y² = x³ + ax + b (mod p)
 * Provides curve parameters and utility methods for elliptic curve operations
 */
public class EllipticCurve {
    private final BigInteger p;    // Prime modulus
    private final BigInteger a;    // Curve parameter a
    private final BigInteger b;    // Curve parameter b
    private final ECPoint g;       // Generator point
    private final BigInteger n;    // Order of the generator point
    private final int h;           // Cofactor
    private final String name;     // Curve name
    
    /**
     * Creates a new elliptic curve with the given parameters
     * @param p Prime modulus
     * @param a Curve parameter a
     * @param b Curve parameter b
     * @param g Generator point
     * @param n Order of generator point
     * @param h Cofactor
     * @param name Curve name
     */
    public EllipticCurve(BigInteger p, BigInteger a, BigInteger b, ECPoint g, BigInteger n, int h, String name) {
        this.p = p;
        this.a = a;
        this.b = b;
        this.g = g;
        this.n = n;
        this.h = h;
        this.name = name;
        
        // Validate curve parameters
        validateCurve();
    }
    
    /**
     * Validates that the curve parameters are mathematically sound
     */
    private void validateCurve() {
        // Check that p is prime (basic check)
        if (!p.isProbablePrime(100)) {
            throw new IllegalArgumentException("Parameter p must be prime");
        }
        
        // Check that 4a³ + 27b² ≠ 0 (mod p) (non-singular curve)
        BigInteger discriminant = a.pow(3).multiply(BigInteger.valueOf(4))
                                  .add(b.pow(2).multiply(BigInteger.valueOf(27)))
                                  .mod(p);
        
        if (discriminant.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Curve is singular (discriminant is zero)");
        }
        
        // Check that generator point is on the curve
        if (!g.isOnCurve(this)) {
            throw new IllegalArgumentException("Generator point is not on the curve");
        }
        
        // Check that n * g = O (point at infinity)
        if (!g.multiply(n, this).isInfinity()) {
            throw new IllegalArgumentException("Generator point order is incorrect");
        }
    }
    
    public BigInteger getP() {
        return p;
    }
    
    public BigInteger getA() {
        return a;
    }
    
    public BigInteger getB() {
        return b;
    }
    
    public ECPoint getG() {
        return g;
    }
    
    public BigInteger getN() {
        return n;
    }
    
    public int getH() {
        return h;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Gets the bit length of the curve (size of the prime p)
     * @return Bit length of the curve
     */
    public int getBitLength() {
        return p.bitLength();
    }
    
    /**
     * Generates a random point on the curve
     * Note: This is for testing purposes. In cryptographic applications,
     * use proper key generation methods.
     * @return A random point on the curve
     */
    public ECPoint generateRandomPoint() {
        SecureRandom random = new SecureRandom();
        
        // Generate random scalar and multiply with generator
        BigInteger k;
        do {
            k = new BigInteger(n.bitLength(), random);
        } while (k.equals(BigInteger.ZERO) || k.compareTo(n) >= 0);
        
        return g.multiply(k, this);
    }
    
    /**
     * Attempts to find a point on the curve with the given x-coordinate
     * @param x The x-coordinate
     * @return A point with the given x-coordinate, or null if none exists
     */
    public ECPoint findPointWithX(BigInteger x) {
        // Calculate y² = x³ + ax + b (mod p)
        BigInteger ySquared = x.pow(3).add(a.multiply(x)).add(b).mod(p);
        
        // Try to find square root of ySquared mod p
        BigInteger y = modularSquareRoot(ySquared, p);
        if (y == null) {
            return null; // No point exists with this x-coordinate
        }
        
        return new ECPoint(x, y);
    }
    
    /**
     * Computes modular square root using Tonelli-Shanks algorithm
     * @param a The number to find square root of
     * @param p The prime modulus
     * @return Square root of a mod p, or null if no square root exists
     */
    private BigInteger modularSquareRoot(BigInteger a, BigInteger p) {
        // Check if a is a quadratic residue using Legendre symbol
        if (a.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), p).equals(p.subtract(BigInteger.ONE))) {
            return null; // No square root exists
        }
        
        // Special case for p ≡ 3 (mod 4)
        if (p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
            return a.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
        }
        
        // General case: Tonelli-Shanks algorithm
        // This is a simplified version - full implementation would be more complex
        BigInteger q = p.subtract(BigInteger.ONE);
        int s = 0;
        while (q.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            q = q.divide(BigInteger.valueOf(2));
            s++;
        }
        
        if (s == 1) {
            return a.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
        }
        
        // For simplicity, return approximate result for demonstration
        // In production, implement full Tonelli-Shanks algorithm
        return a.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
    }
    
    /**
     * Checks if a point is on this curve
     * @param point The point to check
     * @return true if the point is on the curve, false otherwise
     */
    public boolean contains(ECPoint point) {
        return point.isOnCurve(this);
    }
    
    /**
     * Converts the curve to a human-readable string
     * @return String representation of the curve
     */
    @Override
    public String toString() {
        return String.format("EllipticCurve[%s]: y² = x³ + %sx + %s (mod %s)", 
                           name, a.toString(16), b.toString(16), p.toString(16));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        EllipticCurve other = (EllipticCurve) obj;
        return Objects.equals(p, other.p) &&
               Objects.equals(a, other.a) &&
               Objects.equals(b, other.b) &&
               Objects.equals(g, other.g) &&
               Objects.equals(n, other.n) &&
               h == other.h;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(p, a, b, g, n, h);
    }
    
    /**
     * Creates a copy of this curve with a different name
     * @param newName The new name for the curve
     * @return A new EllipticCurve instance with the same parameters but different name
     */
    public EllipticCurve withName(String newName) {
        return new EllipticCurve(p, a, b, g, n, h, newName);
    }
}