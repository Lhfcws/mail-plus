package edu.sysu.lhfcws.mailplus.commons.io;

import java.io.*;

/**
 * @author lhfcws
 * @time 14-12-24.
 */
public class BinaryFileReader {
    private FileInputStream fin = null;
    private DataInputStream dis = null;

    public BinaryFileReader(String file) throws FileNotFoundException {
        fin = new FileInputStream(file);
        dis = new DataInputStream(fin);
    }

    public BinaryFileReader(InputStream is) throws FileNotFoundException {
        dis = new DataInputStream(is);
    }

    public byte[] readAllBytes() throws IOException {
        ByteArray ba = null;
        while (dis.available() > 0) {
            byte[] bytes = new byte[dis.available()];
            dis.read(bytes);
            ByteArray b = new ByteArray(bytes);
            if (ba == null)
                ba = b;
            else
                ba = ByteArray.concat(ba, b);
        }

        if (ba != null)
            return ba.getBytes();
        else
            return "".getBytes();
    }

    public String readString() throws IOException {
        return new String(readAllBytes());
    }

    public void close() throws IOException {
        if (dis != null)
            dis.close();
        if (fin != null)
            fin.close();
    }

}
