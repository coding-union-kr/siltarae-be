package weavers.siltarae.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Component
public class CryptoUtil {
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static String secretKey;
    private static String iv;

    @Value("${privacy.encryption.secretKey}")
    public void setSecretKey(String secretKey) {
        CryptoUtil.secretKey = secretKey;
    }

    @Value("${privacy.encryption.iv}")
    public void setIv(String iv) {
        CryptoUtil.iv = iv;
    }

    public static String encrypt(String value) {
        try {
            Cipher cipher = createCipherForMode(Cipher.ENCRYPT_MODE);

            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new BadRequestException(ExceptionCode.FAIL_ENCRYPT);
        }
    }

    public static String decrypt(String encrypted) {
        try {
            Cipher cipher = createCipherForMode(Cipher.DECRYPT_MODE);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BadRequestException(ExceptionCode.FAIL_DECRYPT);
        }
    }

    private static Cipher createCipherForMode(int encryptMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] ivByte = Base64.getDecoder().decode(iv);

        Key key = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(ivByte);

        cipher.init(encryptMode, key, ivSpec);
        return cipher;
    }
}
