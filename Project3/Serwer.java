package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Serwer {
    private static final Logger LOGGER = Logger.getLogger(Serwer.class.getName());

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            LOGGER.info("Serwer nasłuchuje na porcie 12345...");
            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.info("Nawiązano połączenie z klientem " + socket.getPort());
                new Thread(new ConnectionHandler(socket)).start();
            }
        } catch (IOException e) {
            LOGGER.severe("Błąd serwera: " + e.getMessage());
        }
    }

    static class ConnectionHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                // Serwer wysyła obiekt String o stałej wartości ready
                outputStream.writeObject("ready");

                // Serwer odbiera wartość n od klienta
                int n = (int) inputStream.readObject();
                LOGGER.info("Serwer otrzymał wartość n = " + n + " z portu " + socket.getPort());

                // Serwer wysyła obiekt String o stałej wartości ready for messages
                outputStream.writeObject("ready for messages");

                // Serwer odbiera n obiektów klasy Message
                for (int i = 0; i < n; i++) {
                    Message message = (Message) inputStream.readObject();
                    LOGGER.info("Serwer otrzymał wiadomość: " + message.getContent());
                }

                // Serwer wysyła obiekt String o stałej wartości finished
                outputStream.writeObject("finished");

                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.severe("Błąd obsługi połączenia: " + e.getMessage());
            }
        }
    }
}
