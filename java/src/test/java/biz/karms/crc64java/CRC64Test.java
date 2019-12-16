/*
This file is part of CRC64Java.

CRC64Java is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CRC64Java is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar. If not, see <http://www.gnu.org/licenses/>.

See crc64.h for a separate Copyright and license notice.
*/
package biz.karms.crc64java;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Michal Karm Babacek karm@fedoraproject.org
 *         See README.md and COPYING.md
 */
public class CRC64Test {
    private static final Logger logger = Logger.getLogger(CRC64Test.class.getName());

    static Stream<Arguments> testCRC64Data() {
        return Stream.of(
                Arguments.of("LALALALALALALALA", "2265390610271838444", "2265390610271838444", "1f7048c1eac220ec", "1f7048c1eac220ec"),
                Arguments.of("seznam.cz", "6663698304406954434", "6663698304406954434", "5c7a364d548ca1c2", "5c7a364d548ca1c2"),
                Arguments.of("example.org", "8010417359890661387", "8010417359890661387", "6f2ab825d6e7c00b", "6f2ab825d6e7c00b"),
                Arguments.of("A", "11935289383220651030", "11935289383220651030", "a5a2aa754a5ba416", "a5a2aa754a5ba416"),
                Arguments.of("pbHFhfYKkG.com", "18417324782760399541", "18417324782760399541", "ff977b4f8d6e92b5", "ff977b4f8d6e92b5")
        );
    }

    @ParameterizedTest
    @MethodSource("testCRC64Data")
    void testCRC64(final String valueToBeHashed,
                   final String expectedBigIntegerString,
                   final String expectedUINT64String,
                   final String expectedHex,
                   final String expectedBigIntegerUnsignedNormalized) {
        final CRC64 s = CRC64.getInstance();
        final byte[] bytesToBeHashed = valueToBeHashed.getBytes(StandardCharsets.UTF_8);

        StringBuilder sb = new StringBuilder(512);
        sb.append("\n");
        sb.append("Bytes to be hashed:                     ");
        sb.append(valueToBeHashed);
        sb.append("\n");
        sb.append("CRC64Imp BigInteger::toString:          ");
        String bis = s.crc64BigInteger(bytesToBeHashed).toString();
        sb.append(bis);
        assertEquals(expectedBigIntegerString, bis, "Wrong hash.");
        sb.append("\n");
        sb.append("CRC64Imp UINT64 string representation:  ");
        String si = s.crc64String(bytesToBeHashed);
        sb.append(si);
        assertEquals(expectedUINT64String, si, "Wrong hash.");
        sb.append("\n");
        sb.append("CRC64Imp Hex:                           ");
        String sx = s.crc64Hex(bytesToBeHashed);
        sb.append(sx);
        assertEquals(expectedHex, sx, "Wrong hash.");
        sb.append("\n");
        sb.append("CRC64Imp BigIntegerUnsignedNormalized:  ");
        String biNorm = byteArrayToString(s.crc64UnsignedBigEndian(bytesToBeHashed));
        sb.append(biNorm);
        assertEquals(expectedBigIntegerUnsignedNormalized, biNorm, "Wrong hash.");
        sb.append("\n");
        logger.info(sb.toString());
    }

    private static String byteArrayToString(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (byte b : bytes) {
            s.append(String.format("%02x", b & 0xFF));
        }
        return s.toString();
    }
}