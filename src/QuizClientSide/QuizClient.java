package QuizClientSide;

import QuizServerSide.NetworkMessage;
import java.io.*;
import java.net.Socket;

public class QuizClient{
    String ip="127.0.0.1";
    int port = 42052;
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public QuizClient() {
        try {
            this.socket = new Socket(ip, port);
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            inputStream = new ObjectInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public ObjectOutputStream getOutputStream() { return outputStream; }

    public void play(NetworkProtocolClient networkProtocolClient) {
        System.out.println("Run quizClient.play");
        try {
            Object inputStreamMessage;
            while (true) {
                //GAME LOOP(CLIENT SIDE), THIS IS WHERE THE THREAD WILL INFINITELY LOOP
                //HAS A GAME LOOP SERVERSIDE THAT CORRESPONDS/LISTENS TO THIS TO CREATE 2-WAY COMMUNICATION)
                try {
                    inputStreamMessage = inputStream.readObject();
                }
                catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(inputStreamMessage instanceof NetworkMessage networkMessage)
                {
                    networkProtocolClient.parsePacket(inputStream, networkMessage);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
