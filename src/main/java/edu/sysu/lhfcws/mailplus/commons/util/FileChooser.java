package edu.sysu.lhfcws.mailplus.commons.util;

import javax.swing.*;
import java.io.File;

/**
 * A wrapper for JFileChooser, used in files saved or uploaded.
 * @author lhfcws
 * @time 14-12-20.
 */
public class FileChooser {
    private JFileChooser fileChooser;

    public FileChooser() {
        fileChooser = new JFileChooser();
    }

    public String getSavePathByUser(JComponent parent) {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Select path to save");
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        // Error
        if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win"))
            return "C:/";
        else
            return "~/";
    }

    public File getSelectedFile(JComponent parent) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Select file");
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }
}
