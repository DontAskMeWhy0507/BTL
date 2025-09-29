package org.example.demo6.Classes;

import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.math.BigInteger;

/**
 * Demonstration of ECC integration with library management system
 * Shows practical applications of cryptographic features
 */
public class ECCIntegrationDemo {
    
    public static void main(String[] args) {
        System.out.println("=== ECC Integration with Library Management System ===\n");
        
        demonstrateSecureUserAuthentication();
        System.out.println();
        
        demonstrateSecureBookRecommendations();
        System.out.println();
        
        demonstrateDigitalLibraryCards();
        System.out.println();
        
        demonstrateSecureTransactionLogging();
        System.out.println();
        
        System.out.println("=== Integration Demo Complete ===");
    }
    
    /**
     * Demonstrates secure user authentication using ECC
     */
    private static void demonstrateSecureUserAuthentication() {
        System.out.println("1. Secure User Authentication");
        System.out.println("-----------------------------");
        
        // Create a simple user (avoiding JavaFX dependencies)
        SimpleUser alice = new SimpleUser("alice", "alice123", "alice@library.com");
        alice.generateCryptoKeys();
        
        System.out.println("User: " + alice.username);
        System.out.println("Public Key: " + alice.getPublicKeyHex());
        
        // Create authentication token
        String authToken = alice.createAuthToken();
        System.out.println("Auth Token: " + authToken.substring(0, 50) + "...");
        
        // Verify token
        boolean isValid = alice.verifyAuthToken(authToken);
        System.out.println("Token Valid: " + isValid);
        
        // Simulate token tampering
        String tamperedToken = authToken.replace('A', 'B');
        boolean tamperedValid = alice.verifyAuthToken(tamperedToken);
        System.out.println("Tampered Token Valid: " + tamperedValid);
    }
    
    /**
     * Demonstrates secure book recommendations between users
     */
    private static void demonstrateSecureBookRecommendations() {
        System.out.println("2. Secure Book Recommendations");
        System.out.println("------------------------------");
        
        SimpleUser alice = new SimpleUser("alice", "alice123", "alice@library.com");
        SimpleUser bob = new SimpleUser("bob", "bob456", "bob@library.com");
        alice.generateCryptoKeys();
        bob.generateCryptoKeys();
        
        // Alice sends encrypted book recommendation to Bob
        String recommendation = "I highly recommend 'The Art of Computer Programming' by Knuth!";
        ECCrypto.ECEncryptedData encrypted = alice.encryptMessage(recommendation, bob.getPublicKey());
        
        System.out.println("Alice's recommendation (encrypted): [ENCRYPTED DATA]");
        System.out.println("Encryption successful: " + (encrypted != null));
        
        // Bob decrypts the message
        String decrypted = bob.decryptMessage(encrypted);
        System.out.println("Bob receives: " + decrypted);
        System.out.println("Message integrity: " + recommendation.equals(decrypted));
        
        // Bob signs his response
        String response = "Thanks! I'll add it to my reading list.";
        ECKeyPair.ECSignature signature = bob.signMessage(response);
        boolean signatureValid = alice.verifySignature(response, signature, bob.getPublicKey());
        
        System.out.println("Bob's signed response: " + response);
        System.out.println("Signature verification: " + signatureValid);
    }
    
    /**
     * Demonstrates digital library cards with cryptographic verification
     */
    private static void demonstrateDigitalLibraryCards() {
        System.out.println("3. Digital Library Cards");
        System.out.println("------------------------");
        
        SimpleUser librarian = new SimpleUser("librarian", "lib_admin_2024", "admin@library.com");
        SimpleUser student = new SimpleUser("john_doe", "student123", "john@university.edu");
        
        librarian.generateCryptoKeys();
        student.generateCryptoKeys();
        
        // Librarian issues a digital library card
        String cardData = String.format("LIBRARY_CARD|%s|%s|%s|VALID_UNTIL:%s", 
                                      student.username, 
                                      student.email, 
                                      student.getPublicKeyHex(),
                                      LocalDate.now().plusYears(1));
        
        ECKeyPair.ECSignature librarianSignature = librarian.signMessage(cardData);
        
        System.out.println("Digital Library Card issued for: " + student.username);
        System.out.println("Card data: " + cardData.substring(0, 50) + "...");
        System.out.println("Librarian signature: " + librarianSignature.getR().toString(16).substring(0, 16) + "...");
        
        // Verify library card authenticity
        boolean cardValid = student.verifySignature(cardData, librarianSignature, librarian.getPublicKey());
        System.out.println("Library card authentic: " + cardValid);
        
        // Student can prove ownership by signing with their private key
        String ownershipProof = "I am the owner of this library card: " + student.username;
        ECKeyPair.ECSignature ownerSignature = student.signMessage(ownershipProof);
        
        // Verify ownership (librarian can check this)
        boolean ownershipValid = librarian.verifySignature(ownershipProof, ownerSignature, student.getPublicKey());
        System.out.println("Ownership proof valid: " + ownershipValid);
    }
    
    /**
     * Demonstrates secure transaction logging for book borrowing
     */
    private static void demonstrateSecureTransactionLogging() {
        System.out.println("4. Secure Transaction Logging");
        System.out.println("-----------------------------");
        
        SimpleUser librarian = new SimpleUser("librarian", "lib_secure", "admin@library.com");
        SimpleUser user = new SimpleUser("alice", "alice123", "alice@library.com");
        
        librarian.generateCryptoKeys();
        user.generateCryptoKeys();
        
        // Create book borrowing transaction
        String transactionId = "TXN_" + System.currentTimeMillis();
        String bookId = "ISBN_9781234567890";
        String bookTitle = "Advanced Cryptography";
        
        String transaction = String.format("BORROW|%s|%s|%s|%s|%s", 
                                         transactionId, user.username, bookId, bookTitle, LocalDate.now());
        
        // Both parties sign the transaction
        ECKeyPair.ECSignature userSignature = user.signMessage(transaction);
        ECKeyPair.ECSignature librarianSignature = librarian.signMessage(transaction);
        
        System.out.println("Transaction: " + transaction);
        System.out.println("Transaction ID: " + transactionId);
        
        // Create secure transaction record
        String secureRecord = createSecureTransactionRecord(transaction, userSignature, librarianSignature);
        System.out.println("Secure record created: " + (secureRecord.length() > 0));
        
        // Verify transaction integrity
        boolean userSigValid = librarian.verifySignature(transaction, userSignature, user.getPublicKey());
        boolean libSigValid = user.verifySignature(transaction, librarianSignature, librarian.getPublicKey());
        
        System.out.println("User signature valid: " + userSigValid);
        System.out.println("Librarian signature valid: " + libSigValid);
        System.out.println("Transaction integrity: " + (userSigValid && libSigValid));
        
        // Demonstrate tamper detection
        String tamperedTransaction = transaction.replace("BORROW", "RETURN");
        boolean tamperedValid = librarian.verifySignature(tamperedTransaction, userSignature, user.getPublicKey());
        System.out.println("Tampered transaction valid: " + tamperedValid);
    }
    
    private static String createSecureTransactionRecord(String transaction, 
                                                       ECKeyPair.ECSignature userSig, 
                                                       ECKeyPair.ECSignature libSig) {
        return Base64.getEncoder().encodeToString(transaction.getBytes()) + "|" +
               userSig.getR().toString(16) + "|" + userSig.getS().toString(16) + "|" +
               libSig.getR().toString(16) + "|" + libSig.getS().toString(16);
    }
    
    /**
     * Simple user class without JavaFX dependencies for demonstration
     */
    static class SimpleUser {
        String username;
        String password;
        String email;
        ECKeyPair keyPair;
        ECCrypto crypto;
        
        public SimpleUser(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.crypto = new ECCrypto();
        }
        
        public void generateCryptoKeys() {
            // Generate keys from password for deterministic results
            byte[] seed = (password + username).getBytes(StandardCharsets.UTF_8);
            this.keyPair = crypto.keyPairFromSeed(seed);
        }
        
        public ECPoint getPublicKey() {
            return keyPair.getPublicKey();
        }
        
        public String getPublicKeyHex() {
            return keyPair.getPublicKeyCompressedHex();
        }
        
        public ECKeyPair.ECSignature signMessage(String message) {
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            return crypto.sign(messageBytes, keyPair);
        }
        
        public boolean verifySignature(String message, ECKeyPair.ECSignature signature, ECPoint senderPublicKey) {
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            return crypto.verify(messageBytes, signature, senderPublicKey);
        }
        
        public ECCrypto.ECEncryptedData encryptMessage(String message, ECPoint recipientPublicKey) {
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            return crypto.encrypt(messageBytes, recipientPublicKey);
        }
        
        public String decryptMessage(ECCrypto.ECEncryptedData encryptedData) {
            byte[] decryptedBytes = crypto.decrypt(encryptedData, keyPair);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        }
        
        public String createAuthToken() {
            String tokenData = username + ":" + System.currentTimeMillis();
            ECKeyPair.ECSignature signature = signMessage(tokenData);
            
            return Base64.getEncoder().encodeToString(tokenData.getBytes()) + ":" +
                   signature.getR().toString(16) + ":" + signature.getS().toString(16);
        }
        
        public boolean verifyAuthToken(String token) {
            try {
                String[] parts = token.split(":");
                if (parts.length != 3) return false;
                
                String tokenData = new String(Base64.getDecoder().decode(parts[0]));
                String[] dataParts = tokenData.split(":");
                if (dataParts.length != 2) return false;
                
                if (!dataParts[0].equals(username)) return false;
                
                // Check if token is not too old (1 hour for demo)
                long timestamp = Long.parseLong(dataParts[1]);
                if (System.currentTimeMillis() - timestamp > 3600000) return false;
                
                ECKeyPair.ECSignature signature = new ECKeyPair.ECSignature(
                    new BigInteger(parts[1], 16), new BigInteger(parts[2], 16));
                
                return verifySignature(tokenData, signature, keyPair.getPublicKey());
            } catch (Exception e) {
                return false;
            }
        }
    }
}