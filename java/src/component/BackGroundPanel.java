package component;

import javax.swing.*;
import java.awt.*;

public class BackGroundPanel extends JPanel {

    private Image backIcon;
    public BackGroundPanel(Image backIcon) {
        this.backIcon = backIcon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backIcon, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
