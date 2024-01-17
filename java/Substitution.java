/**
 * An abstract class that implements methods from <code>Cipher</code>.
 * <p>
 * Substitution ciphers iteratively replace each character in a text.
 * <p>
 * Two methods:
 * <ul>
 * <li> `encrypt(String)`
 * <li> `decrypt(String)`
 * </ul>
 * and two new abstract methods:
 * <ul>
 * <li> `encrypt(char)`
 * <li> `decrypt(char)`
 * </ul>
 *
 * @author Skeletony_007
 * @see Cipher
 */
public abstract class Substitution implements Cipher {

    /**
     * Encodes the given plain text into a secret cipher text.
     *
     * @param plaintext the plain text to encode
     * @return the cipher text
     */
    public String encrypt(String plaintext) {
        int plaintextLength = plaintext.length();
        StringBuilder cryptotext = new StringBuilder(plaintextLength);
        for (int i = 0; i < plaintextLength; i++) {
            char c = plaintext.charAt(i);
            cryptotext.append(encrypt(c));
        }
        return cryptotext.toString();
    }

    /**
     * Determines the plain text string for a given cipher text.
     *
     * @param cryptotext the cipher text to decode
     * @return the plain text original
     */
    public String decrypt(String cryptotext) {
        int cryptotextLength = cryptotext.length();
        StringBuilder plaintext = new StringBuilder(cryptotextLength);
        for (int i = 0; i < cryptotextLength; i++) {
            char c = cryptotext.charAt(i);
            plaintext.append(decrypt(c));
        }
        return plaintext.toString();
    }

    /**
     * Encodes the given character using substitution.
     *
     * @param c the character to encode
     * @return the encoded character
     */
    public abstract char encrypt(char c);

    /**
     * Determines the original character using substitution.
     *
     * @param c the character to decode
     * @return the the decoded character
     */
    public abstract char decrypt(char c);
}
