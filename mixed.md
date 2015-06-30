# This Page is for general Samples and Help #


## Firmeware Version via direct connect ##
This will work only on Windows machines!
```java

TerminalFactory factory;
List<CardTerminal> terminals;
factory = TerminalFactory.getDefault();
terminals = factory.terminals().list();
CardTerminal terminal = terminals.get(0);

byte[] GET_FIRMWARE_VERSION = { (byte) 0xff, (byte) 0x00, (byte) 0x48,
(byte) 0x00, (byte) 0x00 };

int controlCode = 0x310000 + 3500 * 4;
Card card = terminal.connect("DIRECT");
byte[] response = card.transmitControlCommand(controlCode,
GET_FIRMWARE_VERSION);

System.out.println("Firmeware: " + new String(response));
```

## PCSC-lite and linux ##

You need the following packages, so execude
```
sudo apt-get install libpcsclite1 pcscd libccid libpcsclite-dev```

In some Versions the JVM only searches for the pcsclite libs under /usr/lib64:/usr/local/lib:/usr/local/lib64. If your libpcsclite.so.1 is in another directory you can correct it by setting the System Variable  JAVA\_OPTIONS. For example
```
_JAVA_OPTIONS="-Dsun.security.smartcardio.library=/lib/x86_64-linux-gnu/libpcsclite.so.1"```

Maybe this page will help you with some problems:
https://wiki.archlinux.org/index.php/Touchatag_RFID_Reader

_We had to set the ifdDriverOptions, to work with our reader_

This part is under construction and will be updated frequently! If you have some comments please contact me!