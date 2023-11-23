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


    public QuizServerPlayer(Socket socket, QuizServerGame game, String playerName, boolean firstMove) {
        this.game = game;
        this.playerName = playerName;
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
                System.out.println("Running gameloop for player: " + getPlayerName());

                //SEND NETWORKMESSAGE OBJECT
                System.out.println("Sending NetworkMessageObject for player: " + getPlayerName());
                output.writeObject(new NetworkMessage((byte) 0x10));

                //SEND PLAYER OBJECT
                System.out.println("Sending PlayerObject for player: " + getPlayerName());
                output.writeObject(this);

                System.out.println("Write answer");
                //READ ANSWER
                String answerInput = (String) input.readObject();
                System.out.println("Read answer: " + answerInput);
                // Kontrollera om svaret är korrekt
                boolean isCorrect = correctAnswer(answerInput);
                // Skicka resultatet tillbaka till klienten
                output.writeObject(isCorrect);
                System.out.println(isCorrect);

            //END OF TESTING

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

    public boolean correctAnswer(String answerInput) {
        // Kontrollera om svaret är korrekt
        System.out.println("Correct answer(check): " + answerInput);
        boolean isCorrect = answerInput.equals("true");
        return isCorrect;
    }
}

