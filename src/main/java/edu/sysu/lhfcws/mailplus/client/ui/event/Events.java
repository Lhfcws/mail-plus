package edu.sysu.lhfcws.mailplus.client.ui.event;

import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.event.listener.TextAdapter;

import javax.swing.*;
import java.awt.event.*;

/**
 * Common events, in JavaScript style.
 *
 * @author lhfcws
 * @time 14-10-30.
 */
public class Events {

    // ===== Mouse events
    public static void onClick(JComponent component, final Callback function) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (function != null && e != null)
                    function.callback(e);
            }
        };

        if (component instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) component;
            item.addActionListener(actionListener);
        } else if (component instanceof JButton) {
            JButton item = (JButton) component;
            item.addActionListener(actionListener);
        } else {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    function.callback(e);
                }
            });
        }
    }

    public static void onMouseMove(JComponent component, final Callback function) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onMouseDrag(JComponent component, final Callback function) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onMouseEnter(JComponent component, final Callback function) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onMouseLeave(JComponent component, final Callback function) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onMouseDown(JComponent component, final Callback function) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onMouseUp(JComponent component, final Callback function) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                function.callback(e);
            }
        });
    }

    // ===== Keyboard events
    public static void onKeyDown(JComponent component, final Callback function) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onKeyUp(JComponent component, final Callback function) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                function.callback(e);
            }
        });
    }

    // ===== Text events
    public static void onFocus(JComponent component, final Callback function) {
        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onBlur(JComponent component, final Callback function) {
        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                function.callback(e);
            }
        });
    }

    public static void onChange(JComponent component, final Callback function) {
        component.addComponentListener(new TextAdapter() {
            @Override
            public void textValueChanged(TextEvent e) {
                function.callback(e);
            }
        });
    }

    public static void bindKeyToButton(int keyCode, final JButton button) {
        button.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        }, KeyStroke.getKeyStroke(keyCode, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
