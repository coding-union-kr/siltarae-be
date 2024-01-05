package weavers.siltarae.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Component
public class CryptoUtil {
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static String secretKey;

    @Value("${privacy.encryption.secretKey}")
    public void setSecretKey(String secretKey) {
        CryptoUtil.secretKey = secretKey;
    }

    public static String encrypt(String value) {
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

            Key key = new SecretKeySpec(keyBytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new BadRequestException(ExceptionCode.FAIL_ENCRYPT);
        }
    }

    public static String decrypt(String encrypted) {
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            Key key = new SecretKeySpec(keyBytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BadRequestException(ExceptionCode.FAIL_DECRYPT);
        }
    }
}
