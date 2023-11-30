package QuizServerSide;

import java.net.ServerSocket;
public class QuizServer{
    static int port = 42052;
    public static void main(String[] args) throws Exception {

        ServerSocket listener = new ServerSocket(port);

        System.out.println("Quiz Server is Running");

        try {
            while (true) {
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
                QuizServerPlayer player1 = new QuizServerPlayer(listener.accept(), game, true);
                player1.start();
                QuizServerPlayer player2 = new QuizServerPlayer(listener.accept(), game, false);
                player2.start();

                player1.setOpponent(player2);
                player2.setOpponent(player1);

                System.out.println("Two players connected, Starting game");
            }
        }
        finally {
            listener.close();
        }
    }
}