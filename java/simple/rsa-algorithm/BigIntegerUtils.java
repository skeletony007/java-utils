import java.math.BigInteger;
import java.util.Random;

public class BigIntegerUtils {
    public static BigInteger randomBigInteger(BigInteger min, BigInteger max) {
        Random rnd = new Random();
        int bitLength = max.bitLength();
        BigInteger x;
        do {
            x = new BigInteger(bitLength, rnd);
        } while (x.compareTo(min) < 0 || x.compareTo(max) > 0);
        return x;
    }
    public static BigInteger chooseCoprime(BigInteger a) {
        // Generates numbers randomly until one is relatively prime to `a`
        Random random = new Random();
        BigInteger b;
        do {
            b = new BigInteger(a.bitLength(), random);
        } while (a.gcd(b).compareTo(BigInteger.ONE) != 0);
        return b;
    }
}