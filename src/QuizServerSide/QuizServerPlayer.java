package QuizServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class QuizServerPlayer extends Thread implements Serializable {
    //NETWORK
    transient Socket socket;
    transient ObjectInputStream input;
    transient ObjectOutputStream output;
    QuizServerGame game;

    //GAME
    int score;
    boolean firstMove;
    QuizServerPlayer opponent;
    String playerName;


    public QuizServerPlayer(Socket socket, QuizServerGame game, boolean firstMove) {
        this.game = game;
        this.socket = socket;
        try {
            output = new ObjectOutputStream(this.socket.getOutputStream());
            input = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run()
    {
        try {
            while (true) {

            //ONLY TESTING

                //System.out.println("Running gameloop for player: " + getPlayerName());

                //SEND NETWORKMESSAGE OBJECT
                //System.out.println("Sending NetworkMessageObject for player: " + getPlayerName());
                //output.writeObject(new NetworkMessage((byte) 0x10));

                //SEND PLAYER OBJECT
                //System.out.println("Sending PlayerObject for player: " + getPlayerName());
                //output.writeObject(this);

            //END OF TESTING

                //Let user send their desired name
                //Keep reading from client until we receive a string
                boolean continueLoop = true;
                while(continueLoop) {
                    Object lastReadObject = input.readObject();
                    if (lastReadObject instanceof String) {
                        String lastReadString = (String)lastReadObject;
                        setPlayerName(lastReadString);
                        System.out.println("Received player name: " + lastReadString);
                        continueLoop = false;
                    }
                }

                //Player is ready to start game
                NetworkProtocolServer.sendPlayerReady(output);







                output.flush(); // True?: One flush per loop should be more than enough if not too much, don't call flush more than once per loop
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Cannot be named getName cause inherit from Thread class
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public QuizServerGame getGame() {
        return game;
    }
    public ObjectOutputStream getOutputStream() {
        return output;
    }
    public ObjectInputStream getInputStream() {
        return input;
    }
}
