package org.example.demo6.Classes;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Demo class showing ECC mathematical operations in action
 * Can be used to test the ECC implementation
 */
public class ECCDemo {
    
    public static void main(String[] args) {
        System.out.println("=== ECC Mathematical Core Demo ===\n");
        
        // Demo 1: Basic curve operations
        demonstrateCurveOperations();
        
        // Demo 2: Key generation and ECDH
        demonstrateKeyOperations();
        
        // Demo 3: Digital signatures
        demonstrateDigitalSignatures();
        
        // Demo 4: Encryption/Decryption
        demonstrateEncryption();
        
        System.out.println("=== Demo Complete ===");
    }
    
    private static void demonstrateCurveOperations() {
        System.out.println("1. Elliptic Curve Operations");
        System.out.println("----------------------------");
        
        // Get standard curve
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        System.out.println("Using curve: " + curve.getName());
        System.out.println("Curve equation: " + curve.toString());
        
        // Test point operations
        ECPoint g = curve.getG();
        System.out.println("Generator point: " + g);
        
        ECPoint doubled = g.doublePoint(curve);
        System.out.println("2G = " + doubled);
        
        ECPoint tripled = g.add(doubled, curve);
        System.out.println("3G = " + tripled);
        
        // Test scalar multiplication
        ECPoint result = g.multiply(BigInteger.valueOf(123), curve);
        System.out.println("123G = " + result);
        
        System.out.println();
    }
    
    private static void demonstrateKeyOperations() {
        System.out.println("2. Key Generation and ECDH");
        System.out.println("--------------------------");
        
        EllipticCurve curve = ECDomainParameters.getSecp256r1();
        
        // Generate key pairs for Alice and Bob
        ECKeyPair alice = ECKeyPair.generate(curve);
        ECKeyPair bob = ECKeyPair.generate(curve);
        
        System.out.println("Alice's private key: " + alice.getPrivateKeyHex());
        System.out.println("Alice's public key:  " + alice.getPublicKeyCompressedHex());
        
        System.out.println("Bob's private key:   " + bob.getPrivateKeyHex());
        System.out.println("Bob's public key:    " + bob.getPublicKeyCompressedHex());
        
        // Perform ECDH
        BigInteger sharedSecretAlice = alice.ecdhSecret(bob.getPublicKey());
        BigInteger sharedSecretBob = bob.ecdhSecret(alice.getPublicKey());
        
        System.out.println("Shared secret (Alice): " + sharedSecretAlice.toString(16));
        System.out.println("Shared secret (Bob):   " + sharedSecretBob.toString(16));
        System.out.println("Secrets match: " + sharedSecretAlice.equals(sharedSecretBob));
        
        System.out.println();
    }
    
    private static void demonstrateDigitalSignatures() {
        System.out.println("3. Digital Signatures (ECDSA)");
        System.out.println("-----------------------------");
        
        ECCrypto crypto = new ECCrypto();
        ECKeyPair keyPair = crypto.generateKeyPair();
        
        String message = "Hello, ECC World! This is a test message for digital signing.";
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        
        System.out.println("Message: " + message);
        System.out.println("Message hash: " + ECCrypto.bytesToHex(messageBytes));
        
        // Sign the message
        ECKeyPair.ECSignature signature = crypto.sign(messageBytes, keyPair);
        System.out.println("Signature R: " + signature.getR().toString(16));
        System.out.println("Signature S: " + signature.getS().toString(16));
        
        // Verify the signature
        boolean isValid = crypto.verify(messageBytes, signature, keyPair.getPublicKey());
        System.out.println("Signature valid: " + isValid);
        
        // Test with tampered message
        String tamperedMessage = message + " TAMPERED";
        byte[] tamperedBytes = tamperedMessage.getBytes(StandardCharsets.UTF_8);
        boolean isTamperedValid = crypto.verify(tamperedBytes, signature, keyPair.getPublicKey());
        System.out.println("Tampered message valid: " + isTamperedValid);
        
        System.out.println();
    }
    
    private static void demonstrateEncryption() {
        System.out.println("4. ECIES Encryption/Decryption");
        System.out.println("------------------------------");
        
        ECCrypto crypto = new ECCrypto();
        ECKeyPair recipient = crypto.generateKeyPair();
        
        String plaintext = "This is a confidential message encrypted using ECIES!";
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Recipient public key: " + recipient.getPublicKeyCompressedHex());
        
        // Encrypt
        ECCrypto.ECEncryptedData encrypted = crypto.encrypt(plaintextBytes, recipient.getPublicKey());
        System.out.println("Encrypted data length: " + encrypted.getCiphertext().length + " bytes");
        System.out.println("Ephemeral public key: " + encrypted.getEphemeralPublicKey());
        System.out.println("MAC: " + ECCrypto.bytesToHex(encrypted.getMac()));
        
        // Decrypt
        byte[] decryptedBytes = crypto.decrypt(encrypted, recipient);
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
        
        System.out.println("Decrypted: " + decryptedText);
        System.out.println("Decryption successful: " + plaintext.equals(decryptedText));
        
        System.out.println();
    }
    
    // Test all standard curves
    public static void testAllCurves() {
        System.out.println("Testing all standard curves:");
        
        String[] curves = {"secp256r1", "secp384r1", "secp521r1", "secp256k1"};
        
        for (String curveName : curves) {
            EllipticCurve curve = ECDomainParameters.getCurve(curveName);
            if (curve != null) {
                System.out.println("✓ " + curveName + " - " + curve.getBitLength() + " bits");
                
                // Quick test
                ECKeyPair keyPair = ECKeyPair.generate(curve);
                ECPoint testPoint = keyPair.getPublicKey();
                System.out.println("  Generator order validation: " + 
                    curve.getG().multiply(curve.getN(), curve).isInfinity());
                System.out.println("  Public key on curve: " + testPoint.isOnCurve(curve));
            }
        }
    }
}