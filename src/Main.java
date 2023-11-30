import QuizClientSide.QuizClient;
import QuizClientSide.QuizController;
import QuizClientSide.QuizGUI;
import QuizClientSide.QuizPlayer;
import QuizServerSide.QuizServer;

public class Main extends Thread {
    public static void main(String[] args) throws Exception {
        QuizServer startQuizServer = new QuizServer();
        startQuizServer.main(args);

        QuizPlayer p = new QuizPlayer();
        QuizGUI pGUI = new QuizGUI();
        QuizClient c = new QuizClient();
        QuizController player1 = new QuizController(p,pGUI, c);
        player1.run();
        QuizController player2 = new QuizController(p,pGUI, c);
        player2.run();

    }


}
