package edu.sysu.lhfcws.mailplus.commons.io;

/**
 * @author lhfcws
 * @time 14-12-24.
 */
public class ByteArray {
    private byte[] bytes;

    public ByteArray(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int length() {
        return bytes.length;
    }

    public static ByteArray concat(ByteArray ba1, ByteArray ba2) {
        byte[] bytes = new byte[ba1.length() + ba2.length()];

        System.arraycopy(ba1.getBytes(), 0, bytes, 0, ba1.length());
        System.arraycopy(ba2.getBytes(), 0, bytes, ba1.length(), ba2.length());

        return new ByteArray(bytes);
    }
}
