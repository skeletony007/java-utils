import java.math.BigInteger;
import java.util.Arrays;

public class EuclideanAlgorithm {

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return b.equals(BigInteger.ZERO) ? a : gcd(b, a.mod(b));
    }

    public static BigInteger[] extendedEuclidean(BigInteger a, BigInteger b) {
        BigInteger[][] matrix = {{a, BigInteger.ONE, BigInteger.ZERO}, {b, BigInteger.ZERO, BigInteger.ONE}};
        while (!matrix[1][0].equals(BigInteger.ZERO)) {
            BigInteger[] quotientAndRemainder = divide(matrix[0][0], matrix[1][0]);
            BigInteger quotient = quotientAndRemainder[0];
            BigInteger remainder = quotientAndRemainder[1];
            BigInteger[] newRow = {remainder, matrix[0][1].subtract(quotient.multiply(matrix[1][1])), matrix[0][2].subtract(quotient.multiply(matrix[1][2]))};
            matrix[0] = Arrays.copyOf(matrix[1], 3);
            matrix[1] = newRow;
        }
        BigInteger[] result = {matrix[0][0], matrix[0][1], matrix[0][2]};
        // Ensure x is positive and non-zero
        if (result[1].compareTo(BigInteger.ZERO) < 0) {
            result[1] = result[1].add(b.abs());
            result[2] = result[2].subtract(a.abs());
        }
        return result;
    }

    private static BigInteger[] divide(BigInteger a, BigInteger b) {
        return new BigInteger[]{a.divide(b), a.mod(b)};
    }

}
