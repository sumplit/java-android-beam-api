## Software Prerequisites ##
**Java SE Development Kit 7u9**

**Maven _(recommended)_**

_The API is currently tested under Windows 7 only, but it should be no problem to get it running on a linux mashine._



&lt;hr&gt;


## Install in your local Maven Repository ##
To install our Artifact in your local Maven Repository, to use it in your maven projects, please follow these steps:

1. Download the API jar file

2. Unpack the Files and execude the following command:
```
mvn install:install-file -Dfile="<jar-name>"
```

3. Now you installed the pom file and jar in your local repository
include the NFC-API with the following lines in your pom:
```
<dependency>
  <groupId>de.estudent.accesscontrol</groupId>
  <artifactId>nfc</artifactId>
   <version> VERSION NUMBER </version>
</dependency>
```


&lt;hr&gt;


## Start Coding ##
Now you are ready to use our API in your Maven Project.

Next step would be to implement a BeamReceiveListener like this
```
public class MyListener implements BeamReceiveListener {

    public void beamRecieved(NdefMessage message) {
        // handle the message here
    }
}
```

Now you can initalize your NFC Reader and start the Android Beam Handshake.

```
public static void main(String[] args) {
    NFCDevice device = NFCDeviceFactory
            .createNFCDevice(NFCDeviceType.AUTODETECT);
    device.setBeamReceiveListener(this);
    device.initalizeWithDefaultValues();
    device.start();  // the device will whait for the Android Device now
}
```









