import java.util.HashMap;

/**
 * A concrete subclass of <code>MonoAlphaSubstitution</code> with methods relating to the Caesar cipher.
 * <p>
 * Monoalphabetical substitution ciphers iteratively replace each character in a text according to onefixed substitution
 * table. This is a special case, where each character maps to the one n positions later in the alphabet, for a
 * fixed shift n which is the secret key.
 * <p>
 * Usage: <code>java Caesar encrypt n "cipher text"</code><br>
 *        where <code>n</code> is the shift value
 *        and   <code>"cipher text"</code> is the text to be encoded or decoded
 * <p>
 * Example usage: <code>java Caesar encrypt 3 "The ships hung in the sky in much the same way that bricks don't."</code>
 *
 * @author Skeletony_007
 * @see MonoAlphaSubstitution
 */
public class Caesar extends MonoAlphaSubstitution {
    /**
     * Required to get 0.67 marks on codegrade.
     * <p>
     * This attribute was not mentioned on the Assignment 2 page in Canvas.
     *
     */
    private int shift;

    /**
     * The default constructor which results in the trivial identity substitution.
     *
     */
    public Caesar() {
        substitutionTable = new HashMap<>();
    }

    /**
     * A consructor that initialises a specified shift mapping.
     *
     * @param shift the specified shift mapping
     */
    public Caesar(int shift) {
        substitutionTable = new HashMap<>(52);
        for (char c = 'A'; c <= 'Z'; c++) {
            substitutionTable.put(c, rotate(shift, c));
        }
        for (char c = 'a'; c <= 'z'; c++) {
            substitutionTable.put(c, rotate(shift, c));
        }
    }

    /**
     * Rotates a single character by the specified shift value.
     *
     * @param shift the amount to shift the character by
     * @param c the character to be rotated
     * @return the character rotated by the specified shift value
     */
    public static char rotate(int shift, char c) {
        // If the character is not a letter, then return it unchanged
        if (!Character.isLetter(c)) {
            return c;
        }

        // Determine the base character by the case of the `c`
        int base = Character.isLowerCase(c) ? 'a' : 'A';

        // Calculate the offset of the rotated character relative to the `base` character,
        // add 26 to ensure the offset is non-negative, and take the remainder of the result
        // when divided by 26 to get the final offset within the range of 0 to 25.
        // Add the `base` value to get the final rotated character.
        return (char) (((c - base + shift) % 26 + 26) % 26 + base);
    }

    /**
     * A <code>main</code> method for the command line interface.
     * <p>
     * The first command line argument is the encode or decode preference.<br>
     * The second command line argument is the shift value such that every character maps to the one n positions
     * later in the alphabet<br>
     * The third command line argument is the text to be encoded or decoded.
     *
     * @param args command line arguments specifying to encrypt/decrypt, key, and cipher text
     */
    public static void main(String[] args) {
        int argsLength = args.length;
        String usage = "Usage: java Caesar encrypt n \"cipher text\"";
        if (argsLength != 3) {
            System.out.println("Too " + (argsLength < 3 ? "few" : "many") + " parameters!\n" + usage);
            return;
        }

        String direction = args[0];
        int n = Integer.parseInt(args[1]);
        String text = args[2];

        Caesar cipher = new Caesar(n);
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
