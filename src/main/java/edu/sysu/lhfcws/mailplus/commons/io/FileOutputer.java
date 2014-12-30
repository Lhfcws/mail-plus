package edu.sysu.lhfcws.mailplus.commons.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Wrapper for file writer.
 * @author lhfcws
 * @time 14-12-20.
 */
public class FileOutputer {
    private FileOutputStream fos = null;

    public FileOutputer(String file) throws IOException {
        fos = new FileOutputStream(file);
    }

    public void write(String content) throws IOException {
        fos.write(content.getBytes());
    }

    public void write(byte[] bytes) throws IOException {
        fos.write(bytes);
    }

    public void close() throws IOException {
        fos.close();
    }
}
