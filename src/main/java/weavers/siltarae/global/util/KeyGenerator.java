package weavers.siltarae.global.util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    public static String generateSecretKey(int size) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[size];
        random.nextBytes(key);

        String s = Base64.getEncoder().encodeToString(key);

        System.out.println("s = " + s);

        return s;
    }

}
