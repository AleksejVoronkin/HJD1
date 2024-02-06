import server.ServerWindow;
import client.ClientGUI;
import server.ServerControlGUI;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new ServerControlGUI(serverWindow);
        });

        javax.swing.SwingUtilities.invokeLater(() -> {
            new ClientGUI(serverWindow, "Ivan");
            new ClientGUI(serverWindow, "Koly");
        });
    }
}

/// ПАРОЛЬ 12345 