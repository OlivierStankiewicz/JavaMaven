package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Klient {
    private static final Logger LOGGER = Logger.getLogger(Klient.class.getName());

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            LOGGER.info("Nawiązano połączenie z serwerem.");

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Klient odbiera obiekt String o stałej wartości ready
            String readyMessage = (String) inputStream.readObject();
            LOGGER.info("Serwer jest gotowy: " + readyMessage);

            // Klient wysyła wartość n
            Scanner scanner = new Scanner(System.in);
            LOGGER.info("Podaj wartość n: ");
            int n = scanner.nextInt();
            outputStream.writeObject(n);

            // Klient odbiera obiekt String o stałej wartości ready for messages
            String readyForMessages = (String) inputStream.readObject();
            LOGGER.info("Serwer jest gotowy na wysłanie wiadomości: " + readyForMessages);

            // Klient wysyła n obiektów klasy Message
            for (int i = 1; i <= n; i++) {
                Message message = new Message();
                LOGGER.info("Podaj treść wiadomości o numerze " + i + ": ");
                String s = scanner.next();
                message.setContent("Wiadomość o treści: " + s);
                outputStream.writeObject(message);
                LOGGER.info("Wysłano wiadomość: " + message.getContent());
            }

            // Klient odbiera obiekt String o stałej wartości finished
            String finishedMessage = (String) inputStream.readObject();
            LOGGER.info("Serwer zakończył przetwarzanie: " + finishedMessage);

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.severe("Błąd klienta: " + e.getMessage());
        }
    }
}
