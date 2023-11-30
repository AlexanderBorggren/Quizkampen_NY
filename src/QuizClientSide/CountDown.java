package QuizClientSide;

import java.awt.event.*;
import javax.swing.*;

class CountDown {

    Timer timer;

    CountDown(QuizController quizController) {
        quizController.pGUI.progressBar.setValue(1000);
        ActionListener listener = new ActionListener() {
            int counter = 1000;
            public void actionPerformed(ActionEvent ae) {
                counter--;
                System.out.println("Update timer"+counter);
                quizController.pGUI.progressBar.setValue(counter);
                if (counter<1) {
                    System.out.println(quizController.player + "Time's up");
                    timer.stop();
                }
            }
        };
        timer = new Timer(1, listener);
        timer.start();
    }



            public void run(QuizController qc) {
                CountDown cdpb = new CountDown(qc);
            }
        }