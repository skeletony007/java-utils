import java.util.HashMap;

/**
 * A concrete subclass of <code>Substitution</code> with methods relating to monoalphabetical substitution ciphers.
 * <p>
 * Monoalphabetical substitution ciphers iteratively replace each character in a text according to onefixed substitution
 * table.<br>
 * This subclass overloads the two abstract methods <code>encrypt(char)</code> and <code>decrypt(char)</code> from
 * <code>Substitution.java</code>.
 * <p>
 * Usage: <code>java MonoAlphaSubstitution encrypt key "cipher text"</code><br>
 *        where <code>key</code> is the mapping where every character at an odd position is the encoding of the one
 *              directly before it<br>
 *        and   <code>"cipher text"</code> is the text to be encoded or decoded
 * <p>
 * Example usage: <code>java MonoAlphaSubstitution encrypt akbjcidhegffgehdicjbka "Life is wasted on the living."</code>
 *
 * @author Skeletony_007
 * @see Substitution
 */
public class MonoAlphaSubstitution extends Substitution {
    /**
     * Initialise the substitution table.
     *
     */
    public HashMap<Character, Character> substitutionTable;

    /**
     * The default constructor which results in the trivial identity substitution.
     *
     */
    public MonoAlphaSubstitution() {
        substitutionTable = new HashMap<>();
    }

    /**
     * A consructor that initialises a specified mapping.
     *
     * @param mapping the specified mapping
     */
    public MonoAlphaSubstitution(String mapping) {
        int mappingLength = mapping.length();
        substitutionTable = new HashMap<>(mappingLength / 2);
        for (int i = 0; i < mappingLength - 1; i += 2) {
            char key = mapping.charAt(i);
            char value = mapping.charAt(i + 1);
            substitutionTable.put(key, value);
        }
    }

    /**
     * Encodes the given character using the substitution table.
     *
     * @param c the character to encode
     * @return the encoded character
     */
    public char encrypt(char c) {
        return substitutionTable.getOrDefault(c, c);
    }

    /**
     * Determines the original character using the substitution table.
     * <p>
     * Note that this method assumes that <code>substitutionTable</code> contains only one-to-one mappings.
     *
     * @param c the character to decode
     * @return the the decoded character
     */
    public char decrypt(char c) {
        for (char key : substitutionTable.keySet()) {
            if (substitutionTable.get(key) == c) {
                return key;
            }
        }
        return c;
    }

    /**
     * A <code>main</code> method for the command line interface.
     * <p>
     * The first command line argument is the encode or decode preference.<br>
     * The second command line argument is the key such that every character at an odd position is the encoding of
     * the one directly before it.<br>
     * The third command line argument is the text to be encoded or decoded.
     *
     * @param args command line arguments specifying to encrypt/decrypt, key, and cipher text
     */
    public static void main(String[] args) {
        int argsLength = args.length;
        String usage = "Usage: java MonoAlphaSubstitution encrypt key \"cipher text\"";
        if (argsLength != 3) {
            System.out.println("Too " + (argsLength < 3 ? "few" : "many") + " parameters!\n" + usage);
            return;
        }

        String direction = args[0];
        String key = args[1];
        String text = args[2];

        MonoAlphaSubstitution cipher = new MonoAlphaSubstitution(key);
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
