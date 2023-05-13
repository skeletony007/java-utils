/**
 * A concrete subclass of <code>Substitution</code> with methods relating to the Vigenere substitution cipher.
 * <p>
 * Monoalphabetical substitution ciphers iteratively replace each character in a text according to onefixed substitution
 * table. The Vigenere substitution cipher uses a unique translation table for each character position in the plaintext.<br>
 * The translation table is based on a keyword, where, for each character in the plaintext, the respective Caasar shift
 * is calculated by the numeric difference (in ASCII) between the character 'A' and the character of equivelant position
 * in the keyword. Note that the character of equivelant position, if it would be past the end of the keyword, becomes
 * the first character of the keywordand continues to increment from there.
 * <p>
 * Usage: <code>java Vigenere encrypt key "cipher text"</code><br>
 *        where <code>key</code> is the keyword
 *        and   <code>"cipher text"</code> is the text to be encoded or decoded
 * <p>
 * Example usage: <code>java Caesar encrypt 3 "The ships hung in the sky in much the same way that bricks don't."</code>
 *
 * @author Skeletony_007
 * @see Substitution
 * @see Caesar
 */
public class Vigenere extends Substitution {
    private String key;
    private int position;

    /**
     * The default constructor which results in the trivial identity substitution.
     *
     */
    public Vigenere() {
        this.key = "";
        this.position = 0;
    }

    /**
     * A consructor that initialises a specified keyword mapping.
     *
     * @param key the specified keyword mapping
     */
    public Vigenere(String key) {
        this.key = key;
        this.position = 0;
    }

    /**
     * Encodes the given character using the respective Caesar shift value from the keyword.
     *
     * @param c the character to encode
     * @return the encoded character
     */
    public char encrypt(char c) {
        int shift = key.charAt(position) - 'A';
        position = (position + 1) % key.length();
        Caesar cipher = new Caesar(shift);
        return cipher.encrypt(c);
    }

    /**
     * Determines the original character using the respective Caesar shift value from the keyword.
     *
     * @param c the character to decode
     * @return the the decoded character
     */
    public char decrypt(char c) {
        int shift = key.charAt(position) - 'A';
        position = (position + 1) % key.length();
        Caesar cipher = new Caesar(shift);
        return cipher.decrypt(c);
    }

    /**
     * A <code>main</code> method for the command line interface.
     * <p>
     * The first command line argument is the encode or decode preference.<br>
     * The second command line argument is the keyword value such that, for each character in the plaintext, the respective
     * Caasar shift is calculated by the numeric difference (in ASCII) between the character 'A' and the character of equivelant
     * position in the keyword and, if this would be past the end of the keyword, this is the first character of the keyword
     * and continues to increment from there.<br>
     * The third command line argument is the text to be encoded or decoded.
     *
     * @param args command line arguments specifying to encrypt/decrypt, key, and cipher text
     */
    public static void main(String[] args) {
        int argsLength = args.length;
        String usage = "Usage: java Vigenere encrypt key \"cipher text\"";
        if (argsLength != 3) {
            System.out.println("Too " + (argsLength < 3 ? "few" : "many") + " parameters!\n" + usage);
            return;
        }

        String direction = args[0];
        String key = args[1];
        String text = args[2];

        Vigenere cipher = new Vigenere(key);
        String result;

        switch (direction) {
            case "encrypt":
                result = cipher.encrypt(text);
                break;
            case "decrypt":
                result = cipher.decrypt(text);
                break;
            default:
                System.out.println("The first parameter must be \"encrypt\" or \"decrypt\"!\n" + usage);
                return;
        }

        System.out.println(result);
    }
}
