package library.utils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowUtils {
    public static void openWindowWithButtonControl(JButton button, JFrame window) {
        button.setEnabled(false);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                button.setEnabled(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                button.setEnabled(true);
            }
        });

        window.setVisible(true);
    }
}
