CC = cc
SRC = crc64.c
OBJ = $(patsubst %.c, target/%.o, $(SRC))

default: target/classes/mac-x86_64/libcrc64.dylib

clean:
	rm -rf target

target/classes/mac-x86_64:
	mkdir -p target/classes/mac-x86_64

target/%.o : ../libcrc64/src/%.c target/classes/mac-x86_64
	$(CC) $(CFLAGS) -Wall -std=c99 -fPIC -g -c $< -o $@ -I../libcrc64/include -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin
#	$(CC) $(CFLAGS) -Werror -Wall -Wmissing-prototypes -Wstrict-prototypes -Wmissing-declarations -Wpointer-arith -std=c99 -Wdeclaration-after-statement -Wformat -Wformat-security -Wunused -Wno-unknown-pragmas -fPIC -c $< -o $@ -I../libcrc64/include -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin

target/classes/mac-x86_64/libcrc64.dylib: $(OBJ)
	$(CC) $(CFLAGS) -shared $(OBJ) -o $@ $(LDFLAGS)
