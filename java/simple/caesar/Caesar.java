// Part 1 - Encrypting and Decrypting
/**
 * Performs Caesar cipher encryption and decryption on strings.
 * <p>
 * The Caesar cipher is a simple substitution cipher that replaces each letter in a text
 * with a letter a fixed number of positions down the alphabet. Non-letter characters are
 * unchanged. Lowercase and uppercase letters are rotated separately.
 * <h2>Glossary</h2>
 * <ul>
 * <li>"shift" is the number of positions moved down the alphabet (with "wrap around",
 * so that 'z' changes to 'c' given a shift of 3)</li>
 * <li>"message" is the text with the correct shift value</li>
 * <li>"cryptotext" is the text with a shift value applied to it</li>
 * <li>"key" is the shift value required to get from the cryptotext back to the message</li>
 * </ul>
 * <p>
 * Usage: <code>java Caesar n "cipher text"</code><br>
 *  where <code>n</code> is the amount to shift the characters by<br>
 *  and <code>"cipher text"</code> is the string to be rotated
 * <p>
 * Example usage: <code>java Caesar 3 "Hello, world!"</code>
 *
 * @author Skeletony_007
 * @version 1.0
 */
public class Caesar {
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
     * Rotates a string by a specified shift value.
     *
     * @param shift the value to shift the characters by
     * @param s the cipher text to be rotated
     * @return the string rotated by the specified shift value
     */
    public static String rotate(int shift, String s) {
        // Create a new string builder to hold the rotated cipher text
        StringBuilder sb = new StringBuilder();

        // Iterate over each character in the cipher text
        for (char c : s.toCharArray()) {
            // Rotate and append the chracter to the string builder
            sb.append(rotate(shift, c));
        }

        // Convert the string builder to a string and return it
        return sb.toString();
    }
    /**
     * Encodes or decodes text using the Caesar cipher.
     * <p>
     * The first command line argument is the shift value.<br>
     * The second command line argument is the message to be rotated.
     *
     * @param args command line arguments specifying the shift and the message to be rotated
     */
    public static void main(String[] args) {
        // Check if exactly two arguments are passed in
        int aLength = args.length;
        if (aLength != 2) {
            // Complain at the user
            System.out.println("Too " + (aLength < 2 ? "few" : "many") + " parameters!");
            System.out.println("Usage: java Caesar n \"cipher text\"");
            return;
        }

        // Parse the command line arguments, rotate the message appropriately, and print the result
        System.out.println(rotate(Integer.parseInt(args[0]), args[1]));
    }
}
