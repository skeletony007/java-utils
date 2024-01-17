// Part 2 - Cracking the Caesar cipher
/**
 * Deciphers Caesar-encoded English cryptotext without the key using the chi-squared score.
 * <p>
 * The chi-squared score will be lower if two sets of frequencies are closer to each other.
 * This can be used to compare the expected English letter frequencies with the observed
 * frequencies in a text.<br>
 * Iteratively changing the shift of the cryptotext to find the smallest chi-squared score
 * provides the corresponding best shift value. The best scoring key is used to decipher the
 * message.
 * <p>
 * Note that this method is not always accurate. However, the chance of success has a
 * positive correlation with the size of the cryptotext, given that it is in the English
 * language.
 * <h2>Glossary</h2>
 * <ul>
 * <li>"shift" is the number of positions moved down the alphabet (with "wrap around",
 * so that 'z' changes to 'c' given a shift of 3)</li>
 * <li>"message" is the text with the correct shift value</li>
 * <li>"cryptotext" is the text with a shift value applied to it</li>
 * <li>"key" is the shift value required to get from the cryptotext back to the message</li>
 * </ul>
 * <p>
 * Usage: <code>java Brutus "cipher text"</code><br>
 *  where <code>"cipher text"</code> is the string to be decrypted.
 * <p>
 * Example usage: <code>java Brutus "Khoor, zruog!"</code>
 *
 * @author Skeletony_007
 * @version 1.0
 * @see Caesar#rotate(int, String)
 */
public class Brutus {
    /**
     * Defines letter frequencies in English texts as an array of doubles in alphabetical order.
     * <p>
     * Usage: <code>english[n]</code><br>
     *  where <code>n</code> is the index of the letter in the alphabet (starting from 'a')
     * <p>
     * Example usage: <code>english[0]</code> would be the frequency of the letter 'a'
     */
    public static final double[] english = {
        0.0855, 0.0160, 0.0316, 0.0387, 0.1210, 0.0218, 0.0209, 0.0496, 0.0733,
        0.0022, 0.0081, 0.0421, 0.0253, 0.0717, 0.0747, 0.0207, 0.0010, 0.0633,
        0.0673, 0.0894, 0.0268, 0.0106, 0.0183, 0.0019, 0.0172, 0.0011
    };
    /**
     * Computes the count of each letter in a given string as an array of integers.
     * <p>
     * This method is not case sensitive and ignores non 'a'-'z' or 'A'-'Z' characters.
     *
     * @param s the string to compute letter count for
     * @return an array of length 26, where the values correspond to the count of each letter
     * in alphabetical order
     */
    public static int[] count(String s) {
        // Create an array of length 26 to store the count of each letter
        int[] counts = new int[26];

        // Store the characters of `s` in lower case in an array
        char[] chars = s.toLowerCase().toCharArray();

        // Iterate through each character in `s`
        for (char c : chars) {
            // If the character is a letter, increment the corresponding count in the array
            if (Character.isLetter(c)) {
                counts[c - 'a']++;
            }
        }

        // Return the array of counts
        return counts;
    }
    /**
     * Computes the frequency of each letter in a given string as an array of doubles.
     * <p>
     * This method is not case sensitive and ignores non 'a'-'z' or 'A'-'Z' characters.<br>
     * This method is similar to the <code>count</code> method that already exists.
     *
     * @param s the string to compute letter frequency for
     * @return an array of length 26, where the values correspond to the frequency of each letter
     * in alphabetical order
     * @see Brutus#count(String)
     */
    public static double[] frequency(String s) {
        // Create an array of length 26 to store the frequency of each letter
        double[] freqs = new double[26];

        // Compute and store the length of `s`
        int sLength = s.length();

        // Store the characters of `s` in lower case in an array
        char[] chars = s.toLowerCase().toCharArray();

        // Iterate through each character in `s`
        for (char c : chars) {
            // If the character is a letter, increment the corresponding frequency in the array
            if (Character.isLetter(c)) {
                freqs[c - 'a'] += 1.0 / sLength;
            }
        }

        // Return the array of frequencies
        return freqs;
    }
    /**
     * Computes the chi-squared score for the given arrays of frequencies.
     * <p>
     * Note that this chi-squared test for association only works if both collections of observed
     * and expected frequencies are of the same size.
     *
     * @param observed an array of observed frequencies
     * @param expected an array of expected frequencies
     * @return the chi-squared score for the two sets of frequencies
     */
    public static double chiSquared(double[] observed, double[] expected) {
        // Initialize sum to zero
        double sum = 0.0;

        // Store the length of the `observed` array
        int obsLength = observed.length;

        // Compute the sum of squared differences between observed and expected frequencies
        for (int i = 0; i < obsLength; i++) {
            // Compute the contribution of the i-th frequency to the chi-squared score
            double diff = observed[i] - expected[i];
            sum += diff * diff / expected[i];
        }

        // Return the chi-squared score
        return sum;
    }
    /**
     * Deciphers Caesar-encoded English cryptotext without the key.
     * <p>
     * Note that this method is not always accurate. However, the chance of success has a
     * positive correlation with the size of the cryptotext, given that it is in the English
     * language.
     *
     * @param args command line argument specifying the cryptotext
     * @see Caesar#rotate(int, String)
     */
    public static void main(String[] args) {
        // Check if exactly one argument is passed in
        int aLength = args.length;
        if (aLength != 1) {
            // Complain at the user
            System.out.println("Too " + (aLength < 1 ? "few" : "many") + " parameters!");
            System.out.println("Usage: java Brutus \"cipher text\"");
            return;
        }

        // Parse the command line arguments
        String cryptotext = args[0];

        // Initialize variables for finding best key and chi-squared score
        int bestKey = 0;
        double bestScore = Double.POSITIVE_INFINITY;

        // Iteratively test each possible key
        for (int key = 0; key < 26; key++) {
            // Decipher `cryptotext` with current key, compute the letter frequencies array,
            // then the chi-squared score compared to `english` frequencies
            double score = chiSquared(frequency(Caesar.rotate(26 - key, cryptotext)), english);

            // Update best score and key if current score is lower
            if (score < bestScore) {
                bestScore = score;
                bestKey = key;
            }
        }

        // Decipher `cryptotext` with the best scoring key and print the result
        System.out.println(Caesar.rotate(26 - bestKey, cryptotext));
    }
}
