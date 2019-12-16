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

import biz.karms.crc64java.utils.BigIntegerNormalizer;

import java.math.BigInteger;

/**
 * @author Michal Karm Babacek karm@fedoraproject.org
 *         See README.md and COPYING.md
 */
public class CRC64Imp extends CRC64 {

    public CRC64Imp() {
    }

    public synchronized native String crc64String(final byte[] data);

    public BigInteger crc64BigInteger(final byte[] data) {
        return new BigInteger(crc64String(data));
    }

    @Override
    public byte[] crc64UnsignedBigEndian(final byte[] data) {
        return BigIntegerNormalizer.unsignedBigEndian(crc64BigInteger(data).toByteArray(), 8);
    }

    public String crc64Hex(final byte[] data) {
        return new BigInteger(crc64String(data)).toString(16);
    }
}
