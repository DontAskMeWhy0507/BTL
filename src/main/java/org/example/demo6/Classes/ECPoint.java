package org.example.demo6.Classes;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Represents a point on an elliptic curve in Weierstrass form: y² = x³ + ax + b
 * Supports point arithmetic operations including addition, doubling, and scalar multiplication
 */
public class ECPoint {
    public static final ECPoint IDENTITY = new ECPoint(); // Point at infinity
    
    private final BigInteger x;
    private final BigInteger y;
    private final boolean isInfinity;
    
    // Private constructor for point at infinity
    private ECPoint() {
        this.x = null;
        this.y = null;
        this.isInfinity = true;
    }
    
    /**
     * Creates a new point on the elliptic curve
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public ECPoint(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
        this.isInfinity = false;
    }
    
    /**
     * Creates a new point from coordinate strings
     * @param x X-coordinate as string
     * @param y Y-coordinate as string
     */
    public ECPoint(String x, String y) {
        this(new BigInteger(x, 16), new BigInteger(y, 16));
    }
    
    public BigInteger getX() {
        return x;
    }
    
    public BigInteger getY() {
        return y;
    }
    
    public boolean isInfinity() {
        return isInfinity;
    }
    
    /**
     * Adds two points on the elliptic curve
     * @param other The other point to add
     * @param curve The elliptic curve parameters
     * @return The sum of the two points
     */
    public ECPoint add(ECPoint other, EllipticCurve curve) {
        if (this.isInfinity) {
            return other;
        }
        if (other.isInfinity) {
            return this;
        }
        
        BigInteger p = curve.getP();
        
        // Same point - use doubling
        if (this.equals(other)) {
            return doublePoint(curve);
        }
        
        // Points with same x but different y - result is infinity
        if (this.x.equals(other.x)) {
            return IDENTITY;
        }
        
        // Calculate slope: s = (y2 - y1) / (x2 - x1) mod p
        BigInteger deltaY = other.y.subtract(this.y).mod(p);
        BigInteger deltaX = other.x.subtract(this.x).mod(p);
        BigInteger slope = deltaY.multiply(deltaX.modInverse(p)).mod(p);
        
        // Calculate new coordinates
        // x3 = s² - x1 - x2 mod p
        BigInteger x3 = slope.multiply(slope).subtract(this.x).subtract(other.x).mod(p);
        
        // y3 = s(x1 - x3) - y1 mod p
        BigInteger y3 = slope.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
        
        return new ECPoint(x3, y3);
    }
    
    /**
     * Doubles a point on the elliptic curve
     * @param curve The elliptic curve parameters
     * @return The doubled point
     */
    public ECPoint doublePoint(EllipticCurve curve) {
        if (this.isInfinity) {
            return IDENTITY;
        }
        
        BigInteger p = curve.getP();
        BigInteger a = curve.getA();
        
        // Check if y = 0 (tangent is vertical)
        if (this.y.equals(BigInteger.ZERO)) {
            return IDENTITY;
        }
        
        // Calculate slope: s = (3x² + a) / (2y) mod p
        BigInteger numerator = this.x.multiply(this.x).multiply(BigInteger.valueOf(3)).add(a).mod(p);
        BigInteger denominator = this.y.multiply(BigInteger.valueOf(2)).mod(p);
        BigInteger slope = numerator.multiply(denominator.modInverse(p)).mod(p);
        
        // Calculate new coordinates
        // x3 = s² - 2x mod p
        BigInteger x3 = slope.multiply(slope).subtract(this.x.multiply(BigInteger.valueOf(2))).mod(p);
        
        // y3 = s(x - x3) - y mod p
        BigInteger y3 = slope.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
        
        return new ECPoint(x3, y3);
    }
    
    /**
     * Performs scalar multiplication: k * P
     * Uses the double-and-add algorithm for efficiency
     * @param scalar The scalar to multiply by
     * @param curve The elliptic curve parameters
     * @return The result of scalar multiplication
     */
    public ECPoint multiply(BigInteger scalar, EllipticCurve curve) {
        if (scalar.equals(BigInteger.ZERO) || this.isInfinity) {
            return IDENTITY;
        }
        
        if (scalar.equals(BigInteger.ONE)) {
            return this;
        }
        
        // Handle negative scalars
        if (scalar.signum() < 0) {
            return this.negate(curve).multiply(scalar.negate(), curve);
        }
        
        ECPoint result = IDENTITY;
        ECPoint addend = this;
        
        // Double-and-add algorithm
        while (!scalar.equals(BigInteger.ZERO)) {
            if (scalar.testBit(0)) { // If bit is 1
                result = result.add(addend, curve);
            }
            addend = addend.doublePoint(curve);
            scalar = scalar.shiftRight(1);
        }
        
        return result;
    }
    
    /**
     * Negates a point (reflects it across the x-axis)
     * @param curve The elliptic curve parameters
     * @return The negated point
     */
    public ECPoint negate(EllipticCurve curve) {
        if (this.isInfinity) {
            return IDENTITY;
        }
        
        BigInteger negY = curve.getP().subtract(this.y).mod(curve.getP());
        return new ECPoint(this.x, negY);
    }
    
    /**
     * Validates that this point lies on the given elliptic curve
     * @param curve The elliptic curve to validate against
     * @return true if the point is on the curve, false otherwise
     */
    public boolean isOnCurve(EllipticCurve curve) {
        if (this.isInfinity) {
            return true;
        }
        
        BigInteger p = curve.getP();
        BigInteger a = curve.getA();
        BigInteger b = curve.getB();
        
        // Check if y² ≡ x³ + ax + b (mod p)
        BigInteger leftSide = this.y.multiply(this.y).mod(p);
        BigInteger rightSide = this.x.multiply(this.x).multiply(this.x)
                                  .add(a.multiply(this.x))
                                  .add(b).mod(p);
        
        return leftSide.equals(rightSide);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ECPoint other = (ECPoint) obj;
        
        if (this.isInfinity && other.isInfinity) return true;
        if (this.isInfinity || other.isInfinity) return false;
        
        return Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y);
    }
    
    @Override
    public int hashCode() {
        if (isInfinity) {
            return 0;
        }
        return Objects.hash(x, y);
    }
    
    @Override
    public String toString() {
        if (isInfinity) {
            return "ECPoint(∞)";
        }
        return String.format("ECPoint(x=%s, y=%s)", x.toString(16), y.toString(16));
    }
    
    /**
     * Converts the point to compressed format (x-coordinate + parity bit)
     * @return Compressed point representation as byte array
     */
    public byte[] toCompressed() {
        if (isInfinity) {
            return new byte[]{0x00};
        }
        
        byte[] xBytes = x.toByteArray();
        byte[] compressed = new byte[xBytes.length + 1];
        
        // Parity bit: 0x02 for even y, 0x03 for odd y
        compressed[0] = y.testBit(0) ? (byte) 0x03 : (byte) 0x02;
        System.arraycopy(xBytes, 0, compressed, 1, xBytes.length);
        
        return compressed;
    }
    
    /**
     * Converts the point to uncompressed format (0x04 + x + y coordinates)
     * @return Uncompressed point representation as byte array
     */
    public byte[] toUncompressed() {
        if (isInfinity) {
            return new byte[]{0x00};
        }
        
        byte[] xBytes = x.toByteArray();
        byte[] yBytes = y.toByteArray();
        byte[] uncompressed = new byte[1 + xBytes.length + yBytes.length];
        
        uncompressed[0] = (byte) 0x04;
        System.arraycopy(xBytes, 0, uncompressed, 1, xBytes.length);
        System.arraycopy(yBytes, 0, uncompressed, 1 + xBytes.length, yBytes.length);
        
        return uncompressed;
    }
}