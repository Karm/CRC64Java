.SUFFIXES: .dll

all: target\classes\win-x86_64\crc64.dll
OBJECTS = target\alpn.obj target\clientcert.obj target\options.obj target\session.obj target\ssl.obj target\threads.obj target\util.obj

{../libcrc64/src}.c{target}.obj:
    cl $<  /machine:AMD64 /Fo$@ -I..\libcrc64\include -I"$(JAVA_HOME)\include" -I"$(JAVA_HOME)\include\win32" /LD /c

target:
  mkdir target

target\classes: target
  mkdir target\classes

target\classes\win-x86_64: target\classes
  mkdir target\classes\win-x86_64

target\classes\win-x86_64\crc64.dll: target\classes\win-x86_64 $(OBJECTS)
   link $(OBJECTS)  /machine:AMD64 /DLL -out:target\classes\win-x86_64\crc64.dll
