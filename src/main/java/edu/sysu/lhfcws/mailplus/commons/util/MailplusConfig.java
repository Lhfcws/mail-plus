package edu.sysu.lhfcws.mailplus.commons.util;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.FileLineReader;

import java.io.*;
import java.util.HashMap;

/**
 * Configuration Object, with loading config file `mailplus.config`.
 * @author lhfcws
 * @time 14-10-21.
 */
public class MailplusConfig {
    private static MailplusConfig config = null;
    private HashMap<String, String> params;

    private MailplusConfig() {
        init();
    }

    public static MailplusConfig getInstance() {
        if (config == null) {
            synchronized (MailplusConfig.class) {
                if (config == null) {
                    config = new MailplusConfig();
                }
            }
        }
        return config;
    }

    private void init() {
        params = new HashMap<String, String>();
        try {
            FileLineReader lineReader = new FileLineReader(Consts.MAILPLUS_CONFIG);

            String line;
            while ((line = lineReader.readLine()) != null) {
                String[] args = line.split("=");
                Preconditions.checkArgument(args.length > 1);
                String param = args[0].trim();

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++)
                    stringBuilder.append(args[i]);
                String value = stringBuilder.toString().trim();

                params.put(param, value);
            }

            lineReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String param) {
        Preconditions.checkArgument(param != null);

        return this.params.get(param);
    }

    public void put(String param, String value) {
        Preconditions.checkArgument(param != null);
        Preconditions.checkArgument(value != null);

        this.params.put(param, value);
    }
}
