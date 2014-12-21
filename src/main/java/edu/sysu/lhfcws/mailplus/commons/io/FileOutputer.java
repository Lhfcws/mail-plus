package edu.sysu.lhfcws.mailplus.commons.io;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Wrapper for file writer.
 * @author lhfcws
 * @time 14-12-20.
 */
public class FileOutputer {
    private FileWriter writer;

    public FileOutputer(String file) throws IOException {
        writer = new FileWriter(file);
    }

    public void write(String content) throws IOException {
        writer.write(content);
    }

    public void close() throws IOException {
        writer.close();
    }
}
