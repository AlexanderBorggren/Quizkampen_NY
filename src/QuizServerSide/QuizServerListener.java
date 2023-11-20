package QuizServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServerListener {

    int port = 52703;
    QuizServerProtocol qsp;

    public QuizServerListener() {
        this.qsp = new QuizServerProtocol();

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                System.out.println("Väntar på användare: ");
                Socket socket = serverSocket.accept();
                QuizServer s = new QuizServer(socket, qsp);
                s.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        //QuizServerProtocol qsp = new QuizServerProtocol();
        new QuizServerListener();
    }
}
