package model.hasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static utils.StringHelper.stringToHex;

/**
 * Hasher for MD5 algorithm
 */
public class HasherMD5 implements IHasher {
    /**
     * Get password's MD5 hash
     *
     * @param password password to hash
     * @return MD5 hash of the given password as a hex string
     */
    @Override
    public String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.US_ASCII));
            return stringToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Check if password and hash pair is correct
     *
     * @param password password to check
     * @param hash     MD5 hash to check
     * @return "true" if the pair is correct
     */
    @Override
    public boolean checkHash(String password, String hash) {
        return Objects.equals(this.hash(password), hash);
    }

//    public static void main(String[] args) {
//        IHasher h = new HasherMD5();
//        String[] passwords = {"keii_paku_prusd"};
//        for (String p : passwords) {
//            System.out.println(p + " > " + h.hash(p));
//        }
//    }
}
