# CRC64Java is a JNI wrapper for CRC64 native

## Building

    mvn install
    
## Usage in your Java project
Add project repository and dependencies to your pom.xml. Use ```<artifactId>crc64java-mac-x86_64</artifactId>``` for Mac and ```<artifactId>crc64java-windows-x86_64</artifactId>``` for Windows.

    <dependencies>
        <dependency>
            <groupId>biz.karms.crc64java</groupId>
            <artifactId>crc64java-java</artifactId>
            <version>1.0.9</version>
        </dependency>
        <dependency>
            <groupId>biz.karms.crc64java</groupId>
            <artifactId>crc64java-linux-x86_64</artifactId>
            <version>1.0.9</version>
        </dependency>
    </dependencies>
    <repositories>
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>bintray-karm-maven</id>
            <name>bintray</name>
            <url>https://dl.bintray.com/karm/toys</url>
        </repository>
    </repositories>

#### Example
```java
final CRC64 crc64 = CRC64.getInstance();

final BigInteger hash = crc64.crc64BigInteger("SOME DATA TO HASH".getBytes());
System.out.println(hash.toString());

final String hexHash = crc64.crc64Hex("SOME DATA TO HASH".getBytes());
System.out.println(hexHash);
```    
Output:

    11422352796240386699
    9e84595997191a8b

## Acknowledgments

 * CRC64 algorithm native implementation, Copyright (c) 2012, Salvatore Sanfilippo <antirez at gmail dot com>, All rights reserved.
 * CRC64Java JNI wrapper, based off Wildfly-OpenSSL approach to packaging shared objects, although projects are not related in any way. GNU GLP v3 license.
 * See [COPYING.md](./COPYING.md) for details

