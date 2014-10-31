package swing_learn;

import javax.swing.*;
import java.util.Vector;

/**
 * @author lhfcws
 * @time 14-10-29.
 */
public class SwingCompenentTest {
    public static void start() {
        JFrame frame = new JFrame();

        JEditorPane component = new JEditorPane();
        Vector vector = new Vector();
        vector.addElement(component);
        vector.addElement(new JEditorPane());
        JList jList = new JList(vector);


        frame.add(jList);

        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                start();
            }
        });
    }
}
