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

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * @author Michal Karm Babacek karm@fedoraproject.org
 *         See README.md and COPYING.md
 */
public class CRC64Test {
    private static final Logger logger = Logger.getLogger(CRC64Test.class.getName());

    @Test
    public void testCRC64() {
        final CRC64 s = CRC64.getInstance();
        final Map<String, String[]> data = new HashMap<String, String[]>() {{
            put("LALALALALALALALA", new String[]{"2265390610271838444", "2265390610271838444", "1f7048c1eac220ec"});
            put("seznam.cz", new String[]{"6663698304406954434", "6663698304406954434", "5c7a364d548ca1c2"});
            put("example.org", new String[]{"8010417359890661387", "8010417359890661387", "6f2ab825d6e7c00b"});
            put("A", new String[]{"11935289383220651030", "11935289383220651030", "a5a2aa754a5ba416"});
        }};

        StringBuilder sb = new StringBuilder(512);
        data.forEach((k, v) -> {
            sb.append("\n");
            sb.append("Bytes to be hashed:                     ");
            sb.append(k);
            sb.append("\n");
            sb.append("CRC64Imp BigInteger::toString:          ");
            String bis = s.crc64BigInteger(k.getBytes()).toString();
            sb.append(bis);
            assertEquals("Wrong hash.", v[0], bis);
            sb.append("\n");
            sb.append("CRC64Imp UINT64 string representation:  ");
            String si = s.crc64String(k.getBytes());
            sb.append(si);
            assertEquals("Wrong hash.", v[1], si);
            sb.append("\n");
            sb.append("CRC64Imp Hex:                           ");
            String sx = s.crc64Hex(k.getBytes());
            sb.append(sx);
            assertEquals("Wrong hash.", v[2], sx);
            sb.append("\n");
        });
        logger.info(sb.toString());
    }
}