package org.example.demo6.Classes;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Standard elliptic curve domain parameters as defined by various standards
 * (NIST FIPS 186-4, SEC 2, etc.)
 */
public class ECDomainParameters {
    private static final Map<String, EllipticCurve> STANDARD_CURVES = new HashMap<>();
    
    static {
        // Initialize standard curves
        initializeStandardCurves();
    }
    
    private static void initializeStandardCurves() {
        // secp256r1 (P-256) - NIST P-256
        addSecp256r1();
        
        // secp384r1 (P-384) - NIST P-384  
        addSecp384r1();
        
        // secp521r1 (P-521) - NIST P-521
        addSecp521r1();
        
        // secp256k1 - Bitcoin curve
        addSecp256k1();
    }
    
    /**
     * secp256r1 (P-256) curve parameters
     * This is the most commonly used curve for general cryptographic applications
     */
    private static void addSecp256r1() {
        String name = "secp256r1";
        
        // Prime modulus p
        BigInteger p = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF", 16);
        
        // Curve parameters
        BigInteger a = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC", 16);
        BigInteger b = new BigInteger("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B", 16);
        
        // Generator point coordinates
        BigInteger gx = new BigInteger("6B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C296", 16);
        BigInteger gy = new BigInteger("4FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5", 16);
        ECPoint g = new ECPoint(gx, gy);
        
        // Order of generator point
        BigInteger n = new BigInteger("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551", 16);
        
        // Cofactor
        int h = 1;
        
        EllipticCurve curve = new EllipticCurve(p, a, b, g, n, h, name);
        STANDARD_CURVES.put(name, curve);
        STANDARD_CURVES.put("P-256", curve);  // Alias
        STANDARD_CURVES.put("prime256v1", curve);  // OpenSSL alias
    }
    
    /**
     * secp384r1 (P-384) curve parameters
     * Provides higher security level than P-256
     */
    private static void addSecp384r1() {
        String name = "secp384r1";
        
        // Prime modulus p
        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF", 16);
        
        // Curve parameters
        BigInteger a = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC", 16);
        BigInteger b = new BigInteger("B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF", 16);
        
        // Generator point coordinates
        BigInteger gx = new BigInteger("AA87CA22BE8B05378EB1C71EF320AD746E1D3B628BA79B9859F741E082542A385502F25DBF55296C3A545E3872760AB7", 16);
        BigInteger gy = new BigInteger("3617DE4A96262C6F5D9E98BF9292DC29F8F41DBD289A147CE9DA3113B5F0B8C00A60B1CE1D7E819D7A431D7C90EA0E5F", 16);
        ECPoint g = new ECPoint(gx, gy);
        
        // Order of generator point
        BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973", 16);
        
        // Cofactor
        int h = 1;
        
        EllipticCurve curve = new EllipticCurve(p, a, b, g, n, h, name);
        STANDARD_CURVES.put(name, curve);
        STANDARD_CURVES.put("P-384", curve);  // Alias
    }
    
    /**
     * secp521r1 (P-521) curve parameters
     * Provides the highest standard security level
     */
    private static void addSecp521r1() {
        String name = "secp521r1";
        
        // Prime modulus p
        BigInteger p = new BigInteger("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);
        
        // Curve parameters
        BigInteger a = new BigInteger("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC", 16);
        BigInteger b = new BigInteger("0051953EB9618E1C9A1F929A21A0B68540EEA2DA725B99B315F3B8B489918EF109E156193951EC7E937B1652C0BD3BB1BF073573DF883D2C34F1EF451FD46B503F00", 16);
        
        // Generator point coordinates
        BigInteger gx = new BigInteger("00C6858E06B70404E9CD9E3ECB662395B4429C648139053FB521F828AF606B4D3DBAA14B5E77EFE75928FE1DC127A2FFA8DE3348B3C1856A429BF97E7E31C2E5BD66", 16);
        BigInteger gy = new BigInteger("011839296A789A3BC0045C8A5FB42C7D1BD998F54449579B446817AFBD17273E662C97EE72995EF42640C550B9013FAD0761353C7086A272C24088BE94769FD16650", 16);
        ECPoint g = new ECPoint(gx, gy);
        
        // Order of generator point
        BigInteger n = new BigInteger("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFA51868783BF2F966B7FCC0148F709A5D03BB5C9B8899C47AEBB6FB71E91386409", 16);
        
        // Cofactor
        int h = 1;
        
        EllipticCurve curve = new EllipticCurve(p, a, b, g, n, h, name);
        STANDARD_CURVES.put(name, curve);
        STANDARD_CURVES.put("P-521", curve);  // Alias
    }
    
    /**
     * secp256k1 curve parameters
     * Used by Bitcoin and other cryptocurrencies
     */
    private static void addSecp256k1() {
        String name = "secp256k1";
        
        // Prime modulus p
        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);
        
        // Curve parameters (note: a = 0 for secp256k1)
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.valueOf(7);
        
        // Generator point coordinates
        BigInteger gx = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16);
        BigInteger gy = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16);
        ECPoint g = new ECPoint(gx, gy);
        
        // Order of generator point
        BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16);
        
        // Cofactor
        int h = 1;
        
        EllipticCurve curve = new EllipticCurve(p, a, b, g, n, h, name);
        STANDARD_CURVES.put(name, curve);
        STANDARD_CURVES.put("bitcoin", curve);  // Alias
    }
    
    /**
     * Gets a standard curve by name
     * @param curveName The name of the curve (case-insensitive)
     * @return The elliptic curve, or null if not found
     */
    public static EllipticCurve getCurve(String curveName) {
        return STANDARD_CURVES.get(curveName.toLowerCase());
    }
    
    /**
     * Gets the secp256r1 (P-256) curve - most commonly used
     * @return The secp256r1 curve
     */
    public static EllipticCurve getSecp256r1() {
        return STANDARD_CURVES.get("secp256r1");
    }
    
    /**
     * Gets the secp384r1 (P-384) curve
     * @return The secp384r1 curve
     */
    public static EllipticCurve getSecp384r1() {
        return STANDARD_CURVES.get("secp384r1");
    }
    
    /**
     * Gets the secp521r1 (P-521) curve
     * @return The secp521r1 curve
     */
    public static EllipticCurve getSecp521r1() {
        return STANDARD_CURVES.get("secp521r1");
    }
    
    /**
     * Gets the secp256k1 curve (Bitcoin curve)
     * @return The secp256k1 curve
     */
    public static EllipticCurve getSecp256k1() {
        return STANDARD_CURVES.get("secp256k1");
    }
    
    /**
     * Gets all available curve names
     * @return Array of curve names
     */
    public static String[] getAvailableCurves() {
        return STANDARD_CURVES.keySet().toArray(new String[0]);
    }
    
    /**
     * Checks if a curve name is supported
     * @param curveName The curve name to check
     * @return true if the curve is supported, false otherwise
     */
    public static boolean isSupported(String curveName) {
        return STANDARD_CURVES.containsKey(curveName.toLowerCase());
    }
    
    /**
     * Gets the recommended curve for general use
     * Currently returns secp256r1 as it provides good security and performance
     * @return The recommended elliptic curve
     */
    public static EllipticCurve getRecommendedCurve() {
        return getSecp256r1();
    }
    
    /**
     * Gets a curve appropriate for the specified security level
     * @param securityBits The desired security level in bits
     * @return An appropriate elliptic curve
     */
    public static EllipticCurve getCurveForSecurityLevel(int securityBits) {
        if (securityBits <= 128) {
            return getSecp256r1();  // ~128-bit security
        } else if (securityBits <= 192) {
            return getSecp384r1();  // ~192-bit security
        } else {
            return getSecp521r1();  // ~256-bit security
        }
    }
}