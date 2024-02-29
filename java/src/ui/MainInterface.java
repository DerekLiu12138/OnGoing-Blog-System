package ui;

import component.BackGroundPanel;
import pojos.User;
import util.ScreenUtils;

import web.API;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainInterface {
    JFrame jf = new JFrame("GOGOGO Blog System - Administrator");

    final int WIDTH = 500;
    final int HEIGHT = 300;

    public void init() throws Exception{
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("java/src/images/Logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("java/src/images/cover/cover1.jpg")));
        //Login in
        Box vBox = Box.createVerticalBox();

        //Username
        Box uBox = Box.createHorizontalBox();
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        JTextField usernameField = new JTextField(15);
        uBox.add(usernameLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(usernameField);

        //Password
        Box pBox = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = new JPasswordField(15);
        pBox.add(passwordLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(passwordField);

        Box bBox = Box.createHorizontalBox();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    User User = API.getInstance().userLogin(username, password);
                    JOptionPane.showMessageDialog(null, "Successfully logged in!");
                    jf.setVisible(false);
                    new ManagerInterface();
                } catch (API.NotAdminException ex) {
                    JOptionPane.showMessageDialog(null, "You are not an admin!");
                } catch (API.LoginFailedException ex) {
                    JOptionPane.showMessageDialog(null, "Username or password is incorrect!");
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
            });

        bBox.add(loginButton);

        vBox.add(Box.createVerticalStrut(50));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(bBox);

        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setVisible(true);

    }

}


