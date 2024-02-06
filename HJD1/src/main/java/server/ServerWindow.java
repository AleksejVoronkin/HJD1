package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow {
    private boolean isRunning = false;
    private List<MessageListener> listeners = new ArrayList<>();
    private File logFile = new File("log.txt");

    public void startServer() {
        isRunning = true;
        loadMessageHistory();
        System.out.println("Сервер запущен.");
    }

    public void stopServer() {
        isRunning = false;
        System.out.println("Сервер выключен.");
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void loadMessageHistory() {
        if (logFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    broadcastMessage(line, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addListener(MessageListener listener) {
        if (isRunning) {
            listeners.add(listener);
            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    listener.onMessageReceived(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(String message, boolean saveToLogFile) {
        if (isRunning) {
            for (MessageListener listener : listeners) {
                listener.onMessageReceived(message);
            }
            if (saveToLogFile) {
                saveMessageToLogFile(message);
            }
        }
    }

    private void saveMessageToLogFile(String message) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)))) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }
}