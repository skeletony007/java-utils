import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class RSAAlgorithm {

    // the two primes
    private BigInteger p;
    private BigInteger q;
    // the public base
    private BigInteger n;
    // the totient
    private BigInteger phiN;
    // the public exponent coprime (gcd is 1) with phiN (used for encryption) (publicKey)
    private BigInteger a;
    // the private exponent (used for decryption) in the linear combination x*a + y*phiN = 1 (privateKey)
    private BigInteger x;
    // the coefficient of the totient in the linear combination x*a + y*phiN = 1
    private BigInteger y;

    public RSAAlgorithm(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.n = p.multiply(q);
        this.phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        // choose the exponent by guessing numbers randomly until one is relatively prime to the totient
        do {
            this.a = BigIntegerUtils.chooseCoprime(this.phiN);
            // find a linear combination of `a` and `phiN` that equals 1 using the extended Euclidean Algorithm
            BigInteger[] coefficients = EuclideanAlgorithm.extendedEuclidean(a, phiN);
            // parse `x` and `y`
            this.x = coefficients[1];
            this.y = coefficients[2];
        } while (a.equals(BigInteger.ONE) || x.equals(BigInteger.ONE));
    }

    public int isValidInput(String[] blocks) {
        int numOfBlocks = blocks.length;
        for (int i = 0; i < numOfBlocks; i++) {
            // error:
            BigInteger m = new BigInteger(blocks[i]);
            int digitsInBlock = blocks[i].length();
            // decoding and then encoding again is inconsistent
            if (encode(decode(m)) == m)
                return 1;
            // the number of digits in any block should be less than the number of digits in either of the primes chosen
            if (digitsInBlock >= p.toString().length() || digitsInBlock >= q.toString().length())
                return 2;
        }
        // input is completely valid
        return 0;
    }

    public BigInteger[] getPublicKey() {
        return new BigInteger[] {n, a};
    }

    public BigInteger encode(BigInteger c) {
        return c.modPow(a, n);
    }

    public BigInteger decode(BigInteger c) {
        return c.modPow(x, n);
    }

    public String getExample(String message, HashMap<Character, String> cipherTable) {

        BigInteger min = BigInteger.ONE;
        BigInteger max = new BigInteger("100");
        BigInteger random = BigIntegerUtils.randomBigInteger(min, max);
        BigInteger randomEncoded = encode(random);
        BigInteger randomEncodedDecoded = decode(randomEncoded);
        // Use a StringBuilder to build the string
        StringBuilder example = new StringBuilder(1000);

        example.append("\\paragraph{Example} Take $p = ").append(p).append("$ and $q = ").append(q)
               .append("$ to be our two primes $p$, $q$. So $n = ").append(n).append("$ and $\\phi(n) = (")
               .append(p).append(" - 1)(").append(q).append(" - 1) = ").append(phiN)
               .append("$.\n\nWe choose an integer $a$ relatively prime to $\\phi(n) = ").append(phiN)
               .append("$: Say $a = ").append(a).append("$. Express $1$ as a linear combination of $")
               .append(phiN).append("$ and $").append(a).append("$:\n$$").append(x).append(" \\cdot ")
               .append(a).append(y.signum() >= 0 ? " + " : " - ").append(y.abs()).append(" \\cdot ")
               .append(phiN).append(" = 1$$\nso `$x$' is $").append(x).append("$. We publish $(n, a) = (")
               .append(n).append(", ").append(a).append(")$.\n\nTo encode a block $\\beta$, the sender calculates $\\beta^{")
               .append(a).append("} \\mod ").append(n).append("$, and to decode a received block $m$, we calculate $m^{")
               .append(x).append("} \\mod ").append(n).append("$.\n\nThus, for example, to encode the message $\\beta = ")
               .append(random).append("$, the sender computes\n$$").append(random).append("^{").append(a)
               .append("} \\mod ").append(n).append(" = ").append(randomEncoded).append(" \\mod ").append(n)
               .append("$$\nand so sends $m = ").append(randomEncoded)
               .append("$. On receipt of this message, anyone who knows `$x$' (the inverse of $").append(a)
               .append(" \\mod ").append(n).append("$) computes $").append(randomEncoded).append("^{")
               .append(x).append("} \\mod ").append(n).append("$ which is equal to the original message $")
               .append(randomEncodedDecoded).append("$.\n\nIf now we use the number-to-letter equivalents:\n")
               .append(HashMapUtils.hashMapToLatexTable(cipherTable)).append("and the received message is $");

        // configure the specific string example using `message` and `cipherTable`
        int messageLength = message.length();
        int numOfBlocks = messageLength / 2; // encode in blocks of two characters (reduces operations)
        String[] blocks = new String[numOfBlocks];
        StringBuilder sb = new StringBuilder();
        int sizeChar = cipherTable.get(message.charAt(0)).length();

        for (int i = 0; i < messageLength; i++) {
            sb.append(cipherTable.get(message.charAt(i)));
            if (i % 2 == 1) {
                blocks[i / 2] = encode(new BigInteger(sb.toString())).toString();
                example.append(new BigInteger(blocks[i / 2]))
                       .append(i == messageLength - 1 ? "$, " : "/");
                sb.setLength(0);
            }
        }

        example.append("the original message is decoded by calculating\n");
        for (int i = 0; i < numOfBlocks; i++) {
            BigInteger valueM = new BigInteger(blocks[i]);
            BigInteger valueBeta = decode(valueM);
            while (sb.length() + valueBeta.toString().length() < 2 * sizeChar)
                sb.append("0");
            sb.append(valueBeta.toString());
            blocks[i] = sb.toString();

            show("Block "+i+": "+blocks[i]);

            sb.setLength(0);
            example.append("$$").append(valueM).append("^{").append(x).append("} \\equiv ")
                   .append(valueBeta).append(" \\mod ").append(n).append("$$\n")
                   .append(i == numOfBlocks - 1 ? "\n" : "and\n");
        }

        example.append("Juxtaposing these blocks gives ");
        for (int i = 0; i < numOfBlocks; i++) {
            example.append(blocks[i]);
        }

        example.append(", and so the message was the word ");
        for (int i = 0; i < numOfBlocks; i++) {
            for (int j = 0; j < 2; j++) {
                example.append(HashMapUtils.getKeyFromValue(cipherTable, blocks[i].substring(j * sizeChar, (j + 1) * sizeChar)))
                       .append(i == numOfBlocks - 1 && j == sizeChar ? ".\n\n" : " ");
            }
        }

        if (isValidInput(blocks) == 2)
            example.append("(In this example we used small primes for purposes of illustration but, in doing so, violated the requirement that the number of digits in any block should be less than the number of digits in either of the primes chosen.)\n\n");

        return example.toString();
    }

    private void show(String s) {
        System.out.println(s);
    }
}
