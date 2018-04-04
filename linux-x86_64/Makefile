CC = cc
SRC = crc64.c
OBJ = $(patsubst %.c, target/%.o, $(SRC))

default: target/classes/linux-x86_64/libcrc64.so

clean:
	rm -rf target

target/classes/linux-x86_64:
	mkdir -p target/classes/linux-x86_64

target/%.o : ../libcrc64/src/%.c target/classes/linux-x86_64
	$(CC) $(CFLAGS) -Wall -std=c99 -fPIC -g -c $< -o $@ -I../libcrc64/include -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
#	$(CC) $(CFLAGS) -Werror -Wall -Wmissing-prototypes -Wstrict-prototypes -Wmissing-declarations -Wpointer-arith -std=c99 -Wdeclaration-after-statement -Wformat -Wformat-security -Wunused -Wno-unknown-pragmas -fPIC -c $< -o $@ -I../libcrc64/include -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux

target/classes/linux-x86_64/libcrc64.so: $(OBJ)
	$(CC) $(CFLAGS) -shared $(OBJ) -o $@ $(LDFLAGS) -Wl,--no-as-needed -ldl
