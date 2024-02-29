package ui;
import pojos.User;
import pojos.UserTableAdaptor;
import web.API;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ManagerInterface {
    private JFrame frame;
    private JTable table;
    private List<User> users;
    final int WIDTH = 1000;
    final int HEIGHT = 600;
    private API api = API.getInstance();

    public ManagerInterface() throws IOException, InterruptedException {
        users = api.getUsers();
        frame = new JFrame("User Table");

        frame.setIconImage(ImageIO.read(new File("java/src/images/Logo.png")));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {"User ID", "Username","Password", "Real Name", "Gender", "Country", "Description", "isAdmin", "AuthToken", "AvatarUrl"};

        //UserTableAdaptor
        UserTableAdaptor model = new UserTableAdaptor(users, columnNames);
        table = new JTable(model);

        //Delete button
        JButton deleteButton = new JButton("Delete User");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        api.deleteUser(users.get(selectedRow).getUserId());
                        return null;
                    }

                    @Override
                    protected void done() {
                        users.remove(selectedRow);
                        // Update the table model
                        table.setModel(new UserTableAdaptor(users, columnNames));
                    }
                }.execute();
            }
        });

        //Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            try {
                new MainInterface().init();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                List<User> newUsers = api.getUsers();
                if (!newUsers.equals(users)) {
                    users = newUsers;
                    table.setModel(new UserTableAdaptor(users, columnNames));
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.scheduleAtFixedRate(task, 0, 7, TimeUnit.SECONDS);
    }
}

