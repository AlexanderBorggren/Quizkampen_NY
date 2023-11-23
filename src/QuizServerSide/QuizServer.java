package QuizServerSide;

import java.net.ServerSocket;
import java.util.ArrayList;
public class QuizServer{

    static int port = 42050;

    public static void main(String[] args) throws Exception {

        ServerSocket listener = new ServerSocket(port);

        System.out.println("Quiz Server is Running");

        //ONLY FOR TESTING
        ArrayList<String> names = new ArrayList<>();
        names.add("Player1");
        names.add("Player2");
        names.add("Player3");
        names.add("Player4");
        int curIndex = 0;

        try {
            while (true) {
                //Think we must have game object so we have a game session that we can connect players to,
                //otherwise they will all play on the same game?
                QuizServerGame game = new QuizServerGame();

                /*
                    -- Explanation on how server communicates with client --

                    (CLIENT SIDE)
                    Socket and streams gets created from QuizClient class, we create QuizClient from QuizController main function
                    And the client game loop is in the QuizClient.play()

                    (SERVER SIDE)
                    The code in this loop controls how many players we set per game, listener.accept() halts the programming and waits for a connection
                    Player per game is decided based on the amount of listener.accept() exists in this loop
                    Multiple Threads gets enabled through Player class serverside (Runs separate thread per client)
                    Game loop is in QuizServerPlayer.run()
                 */
                QuizServerPlayer player1 = new QuizServerPlayer(listener.accept(), game, names.get(curIndex), true);
                curIndex++;
                QuizServerPlayer player2 = new QuizServerPlayer(listener.accept(), game, names.get(curIndex), false);
                curIndex++;

                System.out.println("Two players connected, Starting game");
                player1.start();
                player2.start();

                //Alternative approach, easier threading (Sigrun's comment)
                //Game2 game2 = new Game2(socket1, socket2);
            }
        }
        finally {
            listener.close();
        }
    }

}
