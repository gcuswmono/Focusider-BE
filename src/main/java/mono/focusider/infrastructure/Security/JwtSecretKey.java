package mono.focusider.infrastructure.Security;

import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class JwtSecretKey {

    private final String secretKey;

    public JwtSecretKey() {
        this.secretKey = generateSecretKey();
    }

    private String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            keyGen.init(512); // 512-bit key for HS512
            SecretKey key = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating JWT secret key", e);
        }
    }

    public String getSecretKey() {
        return secretKey;
    }
}