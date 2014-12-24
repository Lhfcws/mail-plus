package edu.sysu.lhfcws.mailplus.commons.io;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Read file by lines.
 * @author lhfcws
 * @time 14-10-22.
 */
public class FileLineReader {

    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public FileLineReader(String filepath) throws FileNotFoundException {
        inputStream = new FileInputStream(filepath);
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    public List<String> readLines() throws IOException {
        List<String> lines = new LinkedList<String>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    public String readAll() throws IOException {
        List<String> list = this.readLines();
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public void close() throws IOException {
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

}
