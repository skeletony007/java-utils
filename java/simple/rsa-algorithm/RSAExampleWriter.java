import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;


public class RSAExampleWriter {
    public static void main(String[] args) {
        // Check if exactly two arguments are passed in
        int aLength = args.length;
        if (aLength != 4) {
            // Complain at the user
            System.out.println("Too " + (aLength < 4 ? "few" : "many") + " parameters!");
            System.out.println("Usage: java Example <prime> <prime> \"<message>\" \"<character> <value> ... <character> <value>\"");
            return;
        }

        try {

            // Create a FileWriter object to write to the file
            FileWriter writer = new FileWriter("example.txt");

            // parse the command line arguments
            BigInteger p = new BigInteger(args[0]);
            BigInteger q = new BigInteger(args[1]);
            String message = args[2];
            HashMap<Character, String> cipherTable = HashMapUtils.parseMap(args[3]);

            // Use a StringBuilder to build the string
            StringBuilder example = new StringBuilder(1000);

            // initialise and build the RSA example
            example.append("\\documentclass{article}\n\n\\usepackage{mathptmx}\n\n% Language setting\n\\usepackage[english]{babel}\n\n% Set page size and margins\n\n\\usepackage[a4paper,top=2cm,bottom=2cm,left=3cm,right=3cm,marginparwidth=1.75cm]{geometry}\n\n% Useful packages\n\\usepackage{amsmath}\n\n\\begin{document}\n\n\\section*{Examples}\n\n")
                   .append((new RSAAlgorithm(p, q)).getExample(message, cipherTable))
                   .append("\\end{document}\n");

            // Write the string to the file
            writer.write(example.toString());

            // Close the FileWriter
            writer.close();

            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
            e.printStackTrace();
        }
    }

}