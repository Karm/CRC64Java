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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Locale;

/**
 * @author Michal Karm Babacek karm@fedoraproject.org
 *         See README.md and COPYING.md
 */
public abstract class CRC64 {

    private static CRC64 instance;

    public CRC64() {
    }

    private static volatile boolean init = false;

    public static CRC64 getInstance() {
        init();
        return instance;
    }

    static void init() {
        if (!init) {
            synchronized (CRC64.class) {
                if (!init) {
                    try {
                        System.loadLibrary("crc64");
                        instance = new CRC64Imp();
                    } catch (Throwable e) {
                        LibraryClassLoader libCl = new LibraryClassLoader(CRC64.class.getClassLoader());
                        try {
                            Class loader = libCl.loadClass(LibraryLoader.class.getName());
                            Method load = loader.getDeclaredMethod("load");
                            Constructor ctor = loader.getDeclaredConstructor();
                            ctor.setAccessible(true);
                            load.setAccessible(true);
                            load.invoke(ctor.newInstance());
                            Class sslClass = libCl.loadClass(CRC64Imp.class.getName());
                            instance = (CRC64) sslClass.newInstance();
                        } catch (Exception e1) {
                            throw new RuntimeException(e1);
                        }
                    }
                    init = true;
                }
            }
        }
    }

    public abstract String crc64String(final byte[] data);

    public abstract BigInteger crc64BigInteger(final byte[] data);

    public abstract String crc64Hex(final byte[] data);

    private static final class LibraryClassLoader extends ClassLoader {

        LibraryClassLoader(ClassLoader parent) {
            super(parent);
        }

        @Override
        protected String findLibrary(String libname) {
            final String mapped = System.mapLibraryName(libname);
            final String sysOs = System.getProperty("os.name").toUpperCase(Locale.US);
            String os;
            if (sysOs.startsWith("LINUX")) {
                os = "linux";
            } else if (sysOs.startsWith("WINDOWS")) {
                os = "win";
            } else {
                throw new UnsupportedOperationException("Only Linux and Windows is supported.");
            }
            final String sysArch = System.getProperty("os.arch").toUpperCase(Locale.US);
            String arch;
            if (sysArch.startsWith("X86_64") || sysArch.startsWith("AMD64")) {
                arch = "x86_64";
            } else {
                throw new UnsupportedOperationException("Only x86_64 is supported.");
            }
            final String complete = String.format("%s-%s/%s", os, arch, mapped);
            try {
                try (final InputStream resource = CRC64.class.getClassLoader().getResourceAsStream(complete)) {
                    if (resource != null) {
                        final File temp = File.createTempFile("tmp-", "crc64");
                        temp.delete();
                        temp.mkdir();
                        final File result = new File(temp, mapped);
                        try (FileOutputStream out = new FileOutputStream(result)) {
                            byte[] buf = new byte[2048];
                            int r;
                            while ((r = resource.read(buf)) > 0) {
                                out.write(buf, 0, r);
                            }
                        }
                        result.deleteOnExit();
                        temp.deleteOnExit();
                        return result.getAbsolutePath();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return super.findLibrary(libname);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (!name.endsWith("$LibraryLoader") && !name.endsWith(".CRC64Imp")) {
                return getParent().loadClass(name);
            }
            try (InputStream file = CRC64.class.getClassLoader().getResourceAsStream(name.replace(".", "/") + ".class")) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[2048];
                int r;
                while ((r = file.read(buf)) > 0) {
                    out.write(buf, 0, r);
                }
                byte[] data = out.toByteArray();
                return defineClass(name, data, 0, data.length, CRC64.class.getProtectionDomain());
            } catch (IOException e) {
                throw new ClassNotFoundException(e.getMessage());
            }
        }
    }

    static class LibraryLoader {
        public void load() {
            System.loadLibrary("crc64");
        }
    }

}
