package swing_learn;

import edu.sysu.lhfcws.mailplus.client.ui.framework.util.LinePanel;

import javax.swing.*;

/**
 * @author lhfcws
 * @time 14-11-2.
 */
public class LinePanelTest extends JFrame {
    public LinePanelTest() {
        super("LinePanelTest");
        LinePanel linePanel = new LinePanel();
        linePanel.add(new JLabel("Email: "));
        linePanel.add(new JTextField());
        LinePanel linePanel1 = new LinePanel();
        linePanel1.add(new JLabel("Password: "));
        linePanel1.add(new JPasswordField());

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().add(linePanel);
        this.getContentPane().add(linePanel1);
        this.setSize(600, 500);
        this.pack();
        this.show();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LinePanelTest();
            }
        });
    }
}
