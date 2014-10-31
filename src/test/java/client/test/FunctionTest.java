package client.test;


import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class FunctionTest {

    JLabel label;

    public void createGUI() {
        final TestFrame frame = new TestFrame();

        JButton button = new JButton("test event");
        button.setPreferredSize(new Dimension(120, 50));
        label = new JLabel("before click");

        Events.onClick(button, new Function() {
            @Override
            public void callback(AWTEvent e) {
                MouseEvent event = (MouseEvent) e;
                label.setVisible(false);
                label.setText("after click");
                label.setVisible(true);
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.add(button);
        frame.start();
    }

    public void changeLabel() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FunctionTest().createGUI();
            }
        });
    }
}
