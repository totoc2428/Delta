package serveur.util.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String generate512(String code) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = code.getBytes();
            byte[] digest = md.digest(bytes);
            String hash = String.format("%064x", new BigInteger(1, digest));

            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
}
