package org.openblend.fejstbuk.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class SecurityUtils {
    /**
     * The salt
     */
    private static final String SALT = "e20#!6J";

    public static String hash(String... strings) {
        if (strings == null || strings.length == 0)
            throw new IllegalArgumentException("Null or empty strings: " + Arrays.toString(strings));

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            StringBuilder builder = new StringBuilder();
            for (String s : strings)
                builder.append(s);
            builder.append(SALT);

            byte[] bytes = builder.toString().getBytes();
            m.update(bytes, 0, bytes.length);
            BigInteger i = new BigInteger(1, m.digest());
            return String.format("%1$032X", i);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
