package de.estudent.accesscontrol.nfc.ndef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.estudent.accesscontrol.nfc.NFCHelper;
import de.estudent.accesscontrol.nfc.exceptions.NdefFormatException;

/**
 * 
 * @author Wilko Oley
 */
public class NdefRecord {
    private final static Logger LOG = LoggerFactory.getLogger(NdefRecord.class);

    private Thread parserThread;

    private byte tnf;
    private byte[] type;
    private byte[] id;
    private byte[] payload;

    private int length;

    public static final byte TNF_EMPTY = 0x00;
    public static final byte TNF_WELL_KNOWN = 0x01;
    public static final byte TNF_MIME_MEDIA = 0x02;
    public static final byte TNF_ABSOLUTE_URI = 0x03;
    public static final byte TNF_EXTERNAL_TYPE = 0x04;
    public static final byte TNF_UNKNOWN = 0x05;
    public static final byte TNF_UNCHANGED = 0x06;
    public static final byte TNF_RESERVED = 0x07;

    public static final byte FLAG_MB = (byte) 0x80;
    public static final byte FLAG_ME = (byte) 0x40;
    public static final byte FLAG_CF = (byte) 0x20;
    public static final byte SR = (byte) 1 << 4;
    public static final byte IL = (byte) 1 << 3;

    public NdefRecord(byte[] data) throws NdefFormatException {
        parseNdefRecord(data);
    }

    public short getTnf() {
        return tnf;
    }

    public String getTnfAsString() {
        switch (tnf) {
        case TNF_EMPTY:
            return "TNF_EMPTY";
        case TNF_WELL_KNOWN:
            return "TNF_WELL_KNOWN";
        case TNF_MIME_MEDIA:
            return "TNF_MIME_MEDIA";
        case TNF_ABSOLUTE_URI:
            return "TNF_ABSOLUTE_URI";
        case TNF_EXTERNAL_TYPE:
            return "TNF_EXTERNAL_TYPE";
        case TNF_UNKNOWN:
            return "TNF_UNKNOWN";
        case TNF_UNCHANGED:
            return "TNF_UNCHANGED";
        case TNF_RESERVED:
            return "TNF_RESERVED";
        default:
            return null;
        }
    }

    private void parseNdefRecord(byte[] data) {

        byte header = data[0];

        tnf = (byte) (header & header << 1 & header << 2);

        int typeLength = data[1] & 0xFF;
        int payloadLength;
        int idLength;

        int start;
        if ((header & NdefRecord.SR) > 0) {
            LOG.debug("Short Record");
            payloadLength = data[2] & 0xFF;
            start = 3;
        } else {
            LOG.debug("Long Record");
            payloadLength = data[5] + (data[4] << 8) + (data[3] << 16)
                    + (data[2] << 24);
            start = 6;
        }
        if ((header & NdefRecord.IL) > 0) {
            idLength = data[start] & 0xFF;
            start++;
        } else {
            idLength = 0;
        }

        type = NFCHelper.subByteArray(data, start, typeLength);

        id = NFCHelper.subByteArray(data, typeLength + start, idLength);

        payload = NFCHelper.subByteArray(data, typeLength + idLength + start,
                payloadLength);

        length = typeLength + idLength + start + payloadLength;

        LOG.debug("NDEF Record created: ");
        LOG.debug("TNF " + getTnfAsString());
        LOG.debug("data " + data.length);
        LOG.debug("payload " + payloadLength);
        LOG.debug("typelength " + typeLength);
        LOG.debug("idlength " + idLength);
        LOG.debug("Total Length:" + getLength());
    }

    public byte[] getType() {
        return type.clone();
    }

    public byte[] getId() {
        return id.clone();
    }

    public byte[] getPayload() {
        return payload.clone();
    }

    public int getLength() {
        return length;
    }

    protected void setTnf(byte tnf) {
        this.tnf = tnf;
    }

    protected void setType(byte[] type) {
        this.type = type;
    }

    protected void setId(byte[] id) {
        this.id = id;
    }

    protected void setPayload(byte[] payload) {
        this.payload = payload;
    }

    protected void setLength(int length) {
        this.length = length;
    }

    protected Thread getParserThread() {
        return parserThread;
    }

}