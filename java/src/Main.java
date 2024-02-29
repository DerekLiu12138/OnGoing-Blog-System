import ui.MainInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MainInterface mainInterface = new MainInterface();
                mainInterface.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}