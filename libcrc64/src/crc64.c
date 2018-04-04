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

@author Michal Karm Babacek karm@fedoraproject.org
        See README.md and COPYING.md
*/
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <jni.h>
#include "crc64.h"

#ifdef _WIN32
    typedef unsigned __int64 uint64_t;
#else
    #include <stdint.h>
#endif

#define CRC64_JAVA(type, name)  JNIEXPORT type JNICALL Java_biz_karms_crc64java_CRC64Imp_##name
#define UINT64_STRING_REP 23
char hash_string[UINT64_STRING_REP];
CRC64_JAVA(jstring, crc64String)(JNIEnv *e, jobject o, jbyteArray data) {
    jbyte* bufferPtr = (*e)->GetByteArrayElements(e, data, NULL);
    jsize length = (*e)->GetArrayLength(e, data);
    memset(hash_string, 0, UINT64_STRING_REP);
    sprintf(hash_string, "%"PRIu64"", crc64(0, (const unsigned char*) bufferPtr, (uint64_t) length));
    return (*e)->NewStringUTF(e,hash_string);
}
