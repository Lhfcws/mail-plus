package edu.sysu.lhfcws.mailplus.client.util;

import edu.sysu.lhfcws.mailplus.commons.io.FileLineReader;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class TemplateLoader {

    private static Log LOG = LogFactory.getLog(TemplateLoader.class);
    // key: template file name; value: template file content;
    private HashMap<String, String> cacheTemplates;

    private static TemplateLoader loader = null;

    public static TemplateLoader getInstance() {
        if (loader == null) {
            synchronized (TemplateLoader.class) {
                if (loader == null) {
                    loader = new TemplateLoader();
                }
            }
        }
        return loader;
    }

    private TemplateLoader() {
       this.cacheTemplates = new HashMap<String, String>();
    }

    public String get(String filename) {
        if (!this.cacheTemplates.containsKey(filename)) {
            return this.load(filename);
        }

        return this.cacheTemplates.get(filename);
    }

    private String load(String filename) {
        try {
            FileLineReader reader = new FileLineReader("target/" + filename);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            this.cacheTemplates.put(filename, sb.toString());

            reader.close();
            return this.cacheTemplates.get(filename);
        } catch (FileNotFoundException e) {
            LogUtil.error(LOG, e);
            return null;
        } catch (IOException e) {
            LogUtil.error(LOG, e);
            return null;
        }
    }
}
