package org.example.demo6.Classes;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Simple test runner for ECC functionality without JUnit dependencies
 */
public class ECCTestRunner {
    private int testsRun = 0;
    private int testsPassed = 0;
    private int testsFailed = 0;
    
    public static void main(String[] args) {
        ECCTestRunner runner = new ECCTestRunner();
        runner.runAllTests();
        runner.printResults();
    }
    
    private void runAllTests() {
        System.out.println("=== ECC Mathematical Core Test Suite ===\n");
        
        testECPointBasics();
        testECPointArithmetic();
        testScalarMultiplication();
        testKeyGeneration();
        testECDH();
        testDigitalSignatures();
        testEncryptionDecryption();
        testStandardCurves();
        testPasswordKeyDerivation();
        testUtilities();
        
        System.out.println("\n=== Test Suite Complete ===");
    }
    
    private void testECPointBasics() {
        System.out.println("Testing EC Point Basics...");
        
        try {
            // Test point creation
            BigInteger x = new BigInteger("6B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C296", 16);
            BigInteger y = new BigInteger("4FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5", 16);
            ECPoint point = new ECPoint(x, y);
            
            assert point.getX().equals(x) : "X coordinate mismatch";
            assert point.getY().equals(y) : "Y coordinate mismatch";
            assert !point.isInfinity() : "Point should not be infinity";
            
            // Test point at infinity
            ECPoint infinity = ECPoint.IDENTITY;
            assert infinity.isInfinity() : "Identity point should be infinity";
            
            pass("EC Point creation and basic properties");
        } catch (Exception e) {
            fail("EC Point basics", e);
        }
    }
    
    private void testECPointArithmetic() {
        System.out.println("Testing EC Point Arithmetic...");
        
        try {
            EllipticCurve curve = ECDomainParameters.getSecp256r1();
            ECPoint g = curve.getG();
            
            // Test point doubling vs addition
            ECPoint doubled = g.doublePoint(curve);
            ECPoint added = g.add(g, curve);
            assert doubled.equals(added) : "Point doubling should equal addition";
            
            // Test identity element
            ECPoint result = g.add(ECPoint.IDENTITY, curve);
            assert result.equals(g) : "G + O should equal G";
            
            // Test inverse
            ECPoint negated = g.negate(curve);
            ECPoint sum = g.add(negated, curve);
            assert sum.isInfinity() : "G + (-G) should be infinity";
            
            pass("EC Point arithmetic operations");
        } catch (Exception e) {
            fail("EC Point arithmetic", e);
        }
    }
    
    private void testScalarMultiplication() {
        System.out.println("Testing Scalar Multiplication...");
        
        try {
            EllipticCurve curve = ECDomainParameters.getSecp256r1();
            ECPoint g = curve.getG();
            
            // Test multiplication by 0
            ECPoint zeroResult = g.multiply(BigInteger.ZERO, curve);
            assert zeroResult.isInfinity() : "0 * G should be infinity";
            
            // Test multiplication by 1
            ECPoint oneResult = g.multiply(BigInteger.ONE, curve);
            assert oneResult.equals(g) : "1 * G should equal G";
            
            // Test multiplication by order
            ECPoint orderResult = g.multiply(curve.getN(), curve);
            assert orderResult.isInfinity() : "n * G should be infinity";
            
            pass("Scalar multiplication");
        } catch (Exception e) {
            fail("Scalar multiplication", e);
        }
    }
    
    private void testKeyGeneration() {
        System.out.println("Testing Key Generation...");
        
        try {
            EllipticCurve curve = ECDomainParameters.getSecp256r1();
            ECKeyPair keyPair = ECKeyPair.generate(curve);
            
            assert keyPair.getPrivateKey() != null : "Private key should not be null";
            assert keyPair.getPublicKey() != null : "Public key should not be null";
            assert keyPair.getCurve().equals(curve) : "Curve should match";
            
            // Validate private key range
            assert keyPair.getPrivateKey().compareTo(BigInteger.ZERO) > 0 : "Private key should be > 0";
            assert keyPair.getPrivateKey().compareTo(curve.getN()) < 0 : "Private key should be < n";
            
            // Validate public key is on curve
            assert keyPair.getPublicKey().isOnCurve(curve) : "Public key should be on curve";
            
            pass("Key pair generation");
        } catch (Exception e) {
            fail("Key generation", e);
        }
    }
    
    private void testECDH() {
        System.out.println("Testing ECDH Key Agreement...");
        
        try {
            EllipticCurve curve = ECDomainParameters.getSecp256r1();
            ECKeyPair alice = ECKeyPair.generate(curve);
            ECKeyPair bob = ECKeyPair.generate(curve);
            
            BigInteger sharedSecretAlice = alice.ecdhSecret(bob.getPublicKey());
            BigInteger sharedSecretBob = bob.ecdhSecret(alice.getPublicKey());
            
            assert sharedSecretAlice.equals(sharedSecretBob) : "ECDH shared secrets should match";
            
            pass("ECDH key agreement");
        } catch (Exception e) {
            fail("ECDH", e);
        }
    }
    
    private void testDigitalSignatures() {
        System.out.println("Testing Digital Signatures...");
        
        try {
            ECCrypto crypto = new ECCrypto();
            ECKeyPair keyPair = crypto.generateKeyPair();
            
            String message = "Test message for signing";
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            
            ECKeyPair.ECSignature signature = crypto.sign(messageBytes, keyPair);
            boolean isValid = crypto.verify(messageBytes, signature, keyPair.getPublicKey());
            
            assert isValid : "Signature should be valid";
            
            // Test with tampered message
            String tamperedMessage = "Tampered message";
            byte[] tamperedBytes = tamperedMessage.getBytes(StandardCharsets.UTF_8);
            boolean isTamperedValid = crypto.verify(tamperedBytes, signature, keyPair.getPublicKey());
            
            assert !isTamperedValid : "Tampered signature should be invalid";
            
            pass("Digital signatures");
        } catch (Exception e) {
            fail("Digital signatures", e);
        }
    }
    
    private void testEncryptionDecryption() {
        System.out.println("Testing ECIES Encryption/Decryption...");
        
        try {
            ECCrypto crypto = new ECCrypto();
            ECKeyPair recipient = crypto.generateKeyPair();
            
            String plaintext = "Secret message for encryption test";
            byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
            
            ECCrypto.ECEncryptedData encrypted = crypto.encrypt(plaintextBytes, recipient.getPublicKey());
            byte[] decryptedBytes = crypto.decrypt(encrypted, recipient);
            String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
            
            assert plaintext.equals(decryptedText) : "Decrypted text should match original";
            
            pass("ECIES encryption/decryption");
        } catch (Exception e) {
            fail("Encryption/decryption", e);
        }
    }
    
    private void testStandardCurves() {
        System.out.println("Testing Standard Curves...");
        
        try {
            EllipticCurve secp256r1 = ECDomainParameters.getSecp256r1();
            assert secp256r1 != null : "secp256r1 should be available";
            assert "secp256r1".equals(secp256r1.getName()) : "Curve name should be secp256r1";
            
            EllipticCurve secp256k1 = ECDomainParameters.getSecp256k1();
            assert secp256k1 != null : "secp256k1 should be available";
            assert "secp256k1".equals(secp256k1.getName()) : "Curve name should be secp256k1";
            
            // Test alias lookup
            EllipticCurve p256 = ECDomainParameters.getCurve("P-256");
            assert secp256r1.equals(p256) : "P-256 should be alias for secp256r1";
            
            pass("Standard curves");
        } catch (Exception e) {
            fail("Standard curves", e);
        }
    }
    
    private void testPasswordKeyDerivation() {
        System.out.println("Testing Password Key Derivation...");
        
        try {
            ECCrypto crypto = new ECCrypto();
            String password = "testPassword123";
            byte[] salt = crypto.generateSalt(16);
            
            ECKeyPair derived1 = crypto.deriveKeyFromPassword(password, salt, 1000);
            ECKeyPair derived2 = crypto.deriveKeyFromPassword(password, salt, 1000);
            
            assert derived1.equals(derived2) : "Same password should derive same key";
            
            ECKeyPair differentKey = crypto.deriveKeyFromPassword("differentPassword", salt, 1000);
            assert !derived1.equals(differentKey) : "Different password should derive different key";
            
            pass("Password key derivation");
        } catch (Exception e) {
            fail("Password key derivation", e);
        }
    }
    
    private void testUtilities() {
        System.out.println("Testing Utility Functions...");
        
        try {
            // Test hex conversion
            String testHex = "DEADBEEF";
            byte[] bytes = ECCrypto.hexToBytes(testHex);
            String reconverted = ECCrypto.bytesToHex(bytes);
            assert testHex.equals(reconverted) : "Hex conversion should be reversible";
            
            // Test public key validation
            ECCrypto crypto = new ECCrypto();
            ECKeyPair keyPair = crypto.generateKeyPair();
            assert crypto.validatePublicKey(keyPair.getPublicKey()) : "Valid public key should validate";
            assert !crypto.validatePublicKey(ECPoint.IDENTITY) : "Identity point should not validate as public key";
            
            pass("Utility functions");
        } catch (Exception e) {
            fail("Utility functions", e);
        }
    }
    
    private void pass(String testName) {
        testsRun++;
        testsPassed++;
        System.out.println("  ✓ " + testName);
    }
    
    private void fail(String testName, Exception e) {
        testsRun++;
        testsFailed++;
        System.out.println("  ✗ " + testName + ": " + e.getMessage());
        e.printStackTrace();
    }
    
    private void printResults() {
        System.out.println("\n=== Test Results ===");
        System.out.println("Tests run: " + testsRun);
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        System.out.println("Success rate: " + (testsPassed * 100 / testsRun) + "%");
        
        if (testsFailed == 0) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.println("❌ Some tests failed.");
        }
    }
}