import org.example.demo6.Classes.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Comprehensive test suite for ECC mathematical operations
 */
public class ECCMathematicalTest {

    @Test
    public void testECPointCreationAndBasicOperations() {
        // Test point creation
        BigInteger x = new BigInteger("6B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C296", 16);
        BigInteger y = new BigInteger("4FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5", 16);
        ECPoint point = new ECPoint(x, y);
        
        assertEquals(x, point.getX());
        assertEquals(y, point.getY());
        assertFalse(point.isInfinity());
        
        // Test point at infinity
        ECPoint infinity = ECPoint.IDENTITY;
        assertTrue(infinity.isInfinity());
    }
    
    @Test
    public void testECPointOnCurveValidation() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        
        // Test that generator point is on curve
        assertTrue(curve.getG().isOnCurve(curve));
        
        // Test random point generation and validation
        ECPoint randomPoint = curve.generateRandomPoint();
        assertTrue(randomPoint.isOnCurve(curve));
    }
    
    @Test
    public void testECPointArithmetic() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        ECPoint g = curve.getG();
        
        // Test point doubling: 2G = G + G
        ECPoint doubled = g.doublePoint(curve);
        ECPoint added = g.add(g, curve);
        assertEquals(doubled, added);
        
        // Test point addition with identity: P + O = P
        ECPoint result = g.add(ECPoint.IDENTITY, curve);
        assertEquals(g, result);
        
        // Test point negation: P + (-P) = O
        ECPoint negated = g.negate(curve);
        ECPoint sum = g.add(negated, curve);
        assertTrue(sum.isInfinity());
    }
    
    @Test
    public void testScalarMultiplication() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        ECPoint g = curve.getG();
        
        // Test scalar multiplication by 0: 0 * G = O
        ECPoint zeroResult = g.multiply(BigInteger.ZERO, curve);
        assertTrue(zeroResult.isInfinity());
        
        // Test scalar multiplication by 1: 1 * G = G
        ECPoint oneResult = g.multiply(BigInteger.ONE, curve);
        assertEquals(g, oneResult);
        
        // Test scalar multiplication by 2: 2 * G = G + G
        ECPoint twoResult = g.multiply(BigInteger.valueOf(2), curve);
        ECPoint doubleResult = g.add(g, curve);
        assertEquals(twoResult, doubleResult);
        
        // Test scalar multiplication by order: n * G = O
        ECPoint orderResult = g.multiply(curve.getN(), curve);
        assertTrue(orderResult.isInfinity());
    }
    
    @Test
    public void testEllipticCurveParameters() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        
        assertNotNull(curve.getP());
        assertNotNull(curve.getA());
        assertNotNull(curve.getB());
        assertNotNull(curve.getG());
        assertNotNull(curve.getN());
        assertEquals(1, curve.getH());
        assertEquals("secp256r1", curve.getName());
        assertEquals(256, curve.getBitLength());
    }
    
    @Test
    public void testStandardCurves() {
        // Test secp256r1
        EllipticCurve secp256r1 = ECDomainParameters.getSecp256r1();
        assertNotNull(secp256r1);
        assertEquals("secp256r1", secp256r1.getName());
        
        // Test secp384r1
        EllipticCurve secp384r1 = ECDomainParameters.getSecp384r1();
        assertNotNull(secp384r1);
        assertEquals("secp384r1", secp384r1.getName());
        
        // Test secp256k1 (Bitcoin curve)
        EllipticCurve secp256k1 = ECDomainParameters.getSecp256k1();
        assertNotNull(secp256k1);
        assertEquals("secp256k1", secp256k1.getName());
        
        // Test curve lookup by name
        EllipticCurve p256 = ECDomainParameters.getCurve("P-256");
        assertEquals(secp256r1, p256);
    }
    
    @Test
    public void testKeyPairGeneration() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        
        // Generate random key pair
        ECKeyPair keyPair = ECKeyPair.generate(curve);
        
        assertNotNull(keyPair.getPrivateKey());
        assertNotNull(keyPair.getPublicKey());
        assertEquals(curve, keyPair.getCurve());
        
        // Validate private key is in correct range
        assertTrue(keyPair.getPrivateKey().compareTo(BigInteger.ZERO) > 0);
        assertTrue(keyPair.getPrivateKey().compareTo(curve.getN()) < 0);
        
        // Validate public key is on curve
        assertTrue(keyPair.getPublicKey().isOnCurve(curve));
    }
    
    @Test
    public void testKeyPairFromPrivateKey() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        BigInteger privateKey = new BigInteger("C28A9F80738EDE59A5EDE529989BAE6B5A0E9CA6FABA4A5508DF8BF3E8F72D3D", 16);
        
        ECKeyPair keyPair = ECKeyPair.fromPrivateKey(privateKey, curve);
        
        assertEquals(privateKey, keyPair.getPrivateKey());
        assertTrue(keyPair.getPublicKey().isOnCurve(curve));
    }
    
    @Test
    public void testECDH() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        
        // Generate two key pairs
        ECKeyPair alice = ECKeyPair.generate(curve);
        ECKeyPair bob = ECKeyPair.generate(curve);
        
        // Perform ECDH from both sides
        BigInteger sharedSecretAlice = alice.ecdhSecret(bob.getPublicKey());
        BigInteger sharedSecretBob = bob.ecdhSecret(alice.getPublicKey());
        
        // Both should compute the same shared secret
        assertEquals(sharedSecretAlice, sharedSecretBob);
    }
    
    @Test
    public void testDigitalSignature() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        ECKeyPair keyPair = ECKeyPair.generate(curve);
        
        String message = "Hello, ECC World!";
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        
        ECCrypto crypto = new ECCrypto(curve);
        
        // Sign the message
        ECKeyPair.ECSignature signature = crypto.sign(messageBytes, keyPair);
        
        // Verify the signature
        boolean isValid = crypto.verify(messageBytes, signature, keyPair.getPublicKey());
        assertTrue(isValid);
        
        // Test with wrong message
        String wrongMessage = "Wrong message";
        byte[] wrongMessageBytes = wrongMessage.getBytes(StandardCharsets.UTF_8);
        boolean isInvalid = crypto.verify(wrongMessageBytes, signature, keyPair.getPublicKey());
        assertFalse(isInvalid);
    }
    
    @Test
    public void testECIESEncryption() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        ECCrypto crypto = new ECCrypto(curve);
        
        // Generate recipient key pair
        ECKeyPair recipient = ECKeyPair.generate(curve);
        
        String plaintext = "This is a secret message for ECC encryption test.";
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        
        // Encrypt the message
        ECCrypto.ECEncryptedData encryptedData = crypto.encrypt(plaintextBytes, recipient.getPublicKey());
        
        assertNotNull(encryptedData.getEphemeralPublicKey());
        assertNotNull(encryptedData.getCiphertext());
        assertNotNull(encryptedData.getMac());
        
        // Decrypt the message
        byte[] decryptedBytes = crypto.decrypt(encryptedData, recipient);
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
        
        assertEquals(plaintext, decryptedText);
    }
    
    @Test
    public void testPasswordBasedKeyDerivation() {
        ECCrypto crypto = new ECCrypto();
        
        String password = "mySecurePassword123";
        byte[] salt = crypto.generateSalt(16);
        int iterations = 1000;
        
        // Derive key from password
        ECKeyPair derivedKey = crypto.deriveKeyFromPassword(password, salt, iterations);
        
        assertNotNull(derivedKey);
        assertTrue(derivedKey.getPrivateKey().compareTo(BigInteger.ZERO) > 0);
        assertTrue(derivedKey.getPublicKey().isOnCurve(crypto.getCurve()));
        
        // Same password should produce same key
        ECKeyPair derivedKey2 = crypto.deriveKeyFromPassword(password, salt, iterations);
        assertEquals(derivedKey, derivedKey2);
        
        // Different password should produce different key
        ECKeyPair differentKey = crypto.deriveKeyFromPassword("differentPassword", salt, iterations);
        assertNotEquals(derivedKey, differentKey);
    }
    
    @Test
    public void testKeyPairSerialization() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        ECKeyPair keyPair = ECKeyPair.generate(curve);
        
        // Test private key hex serialization
        String privateKeyHex = keyPair.getPrivateKeyHex();
        assertNotNull(privateKeyHex);
        assertTrue(privateKeyHex.length() > 0);
        
        // Test public key hex serialization
        String publicKeyHex = keyPair.getPublicKeyHex();
        assertNotNull(publicKeyHex);
        assertTrue(publicKeyHex.length() > 0);
        
        // Test compressed public key
        String compressedHex = keyPair.getPublicKeyCompressedHex();
        assertNotNull(compressedHex);
        assertTrue(compressedHex.length() < publicKeyHex.length());
        
        // Test recreation from private key hex
        ECKeyPair recreated = ECKeyPair.fromPrivateKeyHex(privateKeyHex, curve);
        assertEquals(keyPair, recreated);
    }
    
    @Test
    public void testPointCompression() {
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        ECPoint point = curve.generateRandomPoint();
        
        // Test compressed format
        byte[] compressed = point.toCompressed();
        assertNotNull(compressed);
        assertTrue(compressed.length > 1);
        assertTrue(compressed[0] == 0x02 || compressed[0] == 0x03);
        
        // Test uncompressed format
        byte[] uncompressed = point.toUncompressed();
        assertNotNull(uncompressed);
        assertTrue(uncompressed.length > compressed.length);
        assertEquals(0x04, uncompressed[0]);
    }
    
    @Test
    public void testCurveSecurityLevels() {
        // Test security level mapping
        EllipticCurve curve128 = ECDomainParameters.getCurveForSecurityLevel(128);
        assertEquals("secp256r1", curve128.getName());
        
        EllipticCurve curve192 = ECDomainParameters.getCurveForSecurityLevel(192);
        assertEquals("secp384r1", curve192.getName());
        
        EllipticCurve curve256 = ECDomainParameters.getCurveForSecurityLevel(256);
        assertEquals("secp521r1", curve256.getName());
    }
    
    @Test
    public void testECCryptoUtilities() {
        ECCrypto crypto = new ECCrypto();
        
        // Test random salt generation
        byte[] salt = crypto.generateSalt(32);
        assertEquals(32, salt.length);
        
        // Test shared secret computation
        ECKeyPair alice = crypto.generateKeyPair();
        ECKeyPair bob = crypto.generateKeyPair();
        
        byte[] sharedSecret = crypto.computeSharedSecret(alice, bob);
        assertNotNull(sharedSecret);
        assertTrue(sharedSecret.length > 0);
        
        // Test public key validation
        assertTrue(crypto.validatePublicKey(alice.getPublicKey()));
        assertFalse(crypto.validatePublicKey(ECPoint.IDENTITY));
    }
    
    @Test
    public void testHexConversion() {
        String testHex = "DEADBEEF";
        byte[] bytes = ECCrypto.hexToBytes(testHex);
        String reconverted = ECCrypto.bytesToHex(bytes);
        
        assertEquals(testHex, reconverted);
    }
    
    @Test
    public void testECCryptoWithDifferentCurves() {
        // Test with secp256k1 (Bitcoin curve)
        ECCrypto bitcoinCrypto = new ECCrypto(ECDomainParameters.getSecp256k1());
        ECKeyPair bitcoinKeys = bitcoinCrypto.generateKeyPair();
        
        assertNotNull(bitcoinKeys);
        assertEquals("secp256k1", bitcoinKeys.getCurve().getName());
        
        // Test encryption/decryption with secp384r1
        ECCrypto highSecCrypto = new ECCrypto(ECDomainParameters.getSecp384r1());
        ECKeyPair recipient = highSecCrypto.generateKeyPair();
        
        String message = "High security message";
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        
        ECCrypto.ECEncryptedData encrypted = highSecCrypto.encrypt(messageBytes, recipient.getPublicKey());
        byte[] decrypted = highSecCrypto.decrypt(encrypted, recipient);
        
        assertEquals(message, new String(decrypted, StandardCharsets.UTF_8));
    }
}