package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ServerControlGUI extends JFrame {
    private ServerWindow server;
    private JButton btnToggleServer;

    public ServerControlGUI(ServerWindow server) {
        this.server = server;

        setTitle("Управление сервером");
        setSize(200, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        btnToggleServer = new JButton("Запуск Сервера");
        btnToggleServer.addActionListener((ActionEvent e) -> {
            if (!server.isRunning()) {
                server.startServer();
                btnToggleServer.setText("Остановить сервер");
            } else {
                server.stopServer();
                btnToggleServer.setText("Запуск сервера");
            }
        });

        add(btnToggleServer);
        setVisible(true);
    }
}