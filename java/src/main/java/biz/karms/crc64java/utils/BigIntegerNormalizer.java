package biz.karms.crc64java.utils;

/**
 * @author Michal Karm Babacek
 */
public class BigIntegerNormalizer {

    /**
     *
     *         BigInteger a = new BigInteger("10000000000");
     *         BigInteger b = new BigInteger("150");
     *         BigInteger c = new BigInteger("15395186840396682663");
     *         BigInteger d = new BigInteger("1625639120484488996");
     *         BigInteger e = new BigInteger("340282366920938463463374607431768211455");
     *
     * e.g.
     * length a = 5
     * length b = 2
     * length c = 9
     * length d = 8
     * length e = 17
     *
     * aA: 02540be400
     * bA: 0096
     * cA: 00d5a6b0dd389cd5a7
     * dA: 168f6e2ac574d724
     * eA: 00ffffffffffffffffffffffffffffffff
     *
     * Normalized:
     *
     * aX: 00000002540be400
     * bX: 0000000000000096
     * cX: d5a6b0dd389cd5a7
     * dX: 168f6e2ac574d724
     * eX: ffffffffffffffffffffffffffffffff
     *
     * length aX = 8
     * length bX = 8
     * length cX = 8
     * length dX = 8
     * length eX = 16
     *
     * @param original
     * @param size
     * @return BigEndian, Unsigned
     */
    public static byte[] unsignedBigEndian(byte[] original, int size) {
        int from = (original[0] == 0) ? 1 : 0;
        int to = original.length;
        int newLength = to - from;
        if (size < newLength) {
            throw new IllegalArgumentException(size + " < " + to + " - " + from);
        }
        byte[] copy = new byte[size];
        System.arraycopy(original, from, copy, size - newLength, Math.min(original.length - from, newLength));
        return copy;
    }
}
