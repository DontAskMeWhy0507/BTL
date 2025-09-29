import org.example.demo6.Classes.P256Crypto;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test P-256 elliptic curve cryptography functionality
 */
public class P256Test {
    
    @Test
    public void testP256KeyGeneration() {
        P256Crypto crypto = new P256Crypto(true); // real mode
        assertNotNull("Public key should be generated", crypto.getPublicKeyString());
        assertNotNull("Private key should be generated", crypto.getPrivateKeyString());
        assertTrue("Public key should be valid Base64", crypto.getPublicKeyString().length() > 0);
    }
    
    @Test
    public void testPasswordHashing() {
        P256Crypto crypto = new P256Crypto(true); // real mode
        String password = "testPassword123";
        String hash1 = crypto.hashPassword(password);
        String hash2 = crypto.hashPassword(password);
        
        assertNotNull("Hash should not be null", hash1);
        assertNotNull("Hash should not be null", hash2);
        assertEquals("Same password should produce same hash", hash1, hash2);
        assertNotEquals("Hash should not equal original password", password, hash1);
    }
    
    @Test
    public void testDataSigning() {
        P256Crypto crypto = new P256Crypto(true); // real mode
        String testData = "Hello, P-256 World!";
        
        String signature = crypto.signData(testData);
        assertNotNull("Signature should not be null", signature);
        assertTrue("Signature should be valid Base64", signature.length() > 0);
        
        // Verify signature with public key
        boolean isValid = crypto.verifySignature(testData, signature, 
                         crypto.loadPublicKey(crypto.getPublicKeyString()));
        assertTrue("Signature should be valid", isValid);
    }
    
    @Test
    public void testRealModeConfiguration() {
        P256Crypto cryptoReal = new P256Crypto(true);
        P256Crypto cryptoVisual = new P256Crypto(false);
        
        assertTrue("Real mode should be true", cryptoReal.isRealMode());
        assertFalse("Visual mode should be false", cryptoVisual.isRealMode());
        
        String curveInfo = cryptoReal.getCurveInfo();
        assertTrue("Curve info should contain P-256", curveInfo.contains("P-256"));
        assertTrue("Curve info should indicate real mode", curveInfo.contains("Real (Non-Visual)"));
    }
    
    @Test
    public void testAuthTokenCreation() {
        P256Crypto crypto = new P256Crypto(true); // real mode
        String username = "testuser";
        String password = "testpass";
        String hashedPassword = crypto.hashPassword(password);
        
        String token = crypto.createAuthToken(username, hashedPassword);
        assertNotNull("Auth token should not be null", token);
        assertTrue("Auth token should be valid", token.length() > 0);
    }
}