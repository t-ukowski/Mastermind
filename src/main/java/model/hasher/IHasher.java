package model.hasher;

public interface IHasher {
    /**
     * Get password's hash
     *
     * @param password password to hash
     * @return hash of the given password
     */
    String hash(String password);

    /**
     * Check if password and hash pair is correct
     *
     * @param password password to check
     * @param hash     hash to check
     * @return "true" if the pair is correct
     */
    boolean checkHash(String password, String hash);
}
