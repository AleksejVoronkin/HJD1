package client;

import server.ServerWindow;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JPasswordField pfPassword = new JPasswordField();
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JButton btnConnect = new JButton("Connect");
    private ServerWindow server;
    private String userName;

    public ClientGUI(ServerWindow server, String userName) {
        this.server = server;
        this.userName = userName;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Чат:" + userName);

        setLayout(new BorderLayout());

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog, BorderLayout.CENTER);

        JPanel panelTop = new JPanel(new GridLayout(1, 3));
        panelTop.add(new JLabel("IP:"));
        panelTop.add(tfIPAddress);
        panelTop.add(new JLabel("Password:"));
        panelTop.add(pfPassword);
        panelTop.add(btnConnect);
        add(panelTop, BorderLayout.NORTH);

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        btnConnect.addActionListener(e -> connectToServer());
        btnSend.addActionListener(e -> sendMessage());
        tfMessage.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    private void connectToServer() {
        if (server.isRunning() && new String(pfPassword.getPassword()).equals("12345")) {
            server.addListener(this::receiveMessage);
            JOptionPane.showMessageDialog(this, "Соединение устанволено!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "НЕ удалось подключиться. Проверьте пароль, либо IP адресс", "Ошибка подключения", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessage() {
        String message = tfMessage.getText().trim();
        if (!message.isEmpty()) {
            String formattedMessage = userName + ": " + message;
            server.broadcastMessage(formattedMessage, true);
            tfMessage.setText("");
        }
    }

    public void receiveMessage(String message) {
        SwingUtilities.invokeLater(() -> log.append(message + "\n"));
    }
}