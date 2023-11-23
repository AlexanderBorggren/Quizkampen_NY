package QuizClientSide;

import QuizServerSide.NetworkMessage;
import QuizServerSide.QuizServerPlayer;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class QuizClient{

    String ip="127.0.0.1";
    //String ip = "192.168.1.97";
    int port = 42050;
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

    /*
    //ARON'S BASE CODE - ANDRE DISABLED SINCE WE'RE CHANGING HOW STREAM IS GONNA WORK (NOT SURE IF WE MIGHT STILL WANT THIS THOUGH)

    public String sendAndGetMessage(String data) {
        pw.println(data);
        return readStringMessage();
    }

    public String readStringMessage() {
        String temp;
        try {
            //while ((temp = br.readLine()) == null) {
            while ((temp = (String)ois.readObject()) == null) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public String[] sendAndGetArrayMessage(String data) {
        pw.println(data);
        return readArrayMessage();
    }

    public String[] readArrayMessage() {
        String[] temp;
        try {
            //while ((temp = br.readLine()) == null) {
            while ((temp = (String[])ois.readObject()) == null) {
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

     */
    public boolean sendAndGetMessage(String answerOutput) {
        boolean isCorrect = false;
        try {
            // Skicka svaret till servern
            outputStream.writeObject(answerOutput);
            // Läs svaret från servern
            isCorrect = (boolean) inputStream.readObject();
            System.out.println(isCorrect);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return isCorrect;
    }

    public void play() {
        System.out.println("Run quizClient.play");
        try {
            Object inputStreamMessage;
            while (true) {
                //GAME LOOP(CLIENT SIDE), THIS IS WHERE THE THREAD WILL INFINITELY LOOP
                //HAS A GAME LOOP SERVERSIDE THAT CORRESPONDS/LISTENS TO THIS TO CREATE 2-WAY COMMUNICATION)

                try {
                    inputStreamMessage = inputStream.readObject();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(inputStreamMessage instanceof NetworkMessage)
                {
                    NetworkMessage networkMessage = (NetworkMessage)inputStreamMessage;
                    //System.out.println("Received network object from server, deserializing/unpacking");
                }
                else if(inputStreamMessage instanceof QuizServerPlayer)
                {
                    QuizServerPlayer player = (QuizServerPlayer)inputStreamMessage;
                    //System.out.println("Received player object from server, deserializing/unpacking (Object was player: " + player.getPlayerName());
                }

                //GAME LOGIC, IF WE HAVE STUFF SENT FROM SERVER, IT WILL DO THEM HERE
                //if (inputStreamMessage.startsWith("VALID_MOVE")) {
                //} else if (inputStreamMessage.startsWith("OPPONENT_MOVED")) {
                //}
            }
            //SEND QUIT MESSAGE

            //outputStream.writeObject();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
