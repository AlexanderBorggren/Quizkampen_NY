package QuizClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizController implements Runnable{

    QuizPlayer player;
    QuizGUI pGUI;
    QuizClient client;
    String messageFromServer;
    String[] messageArrayFromServer;
    Thread t = new Thread(this);

    int round = 0;
    boolean newRound = true;

    public QuizController(QuizPlayer player, QuizGUI pGUI, QuizClient client) {
        this.player = player;
        this.pGUI = pGUI;
        this.client = client;
        this.pGUI.addButtonListener(new MyButtonListener());
        this.pGUI.initCategoryButtonListener(new CategoryButtonListener());
        //client.play();
        t.start();
//commented for Testing GUI       startGame();
        System.out.println(player.getName());
    }

    public QuizController(QuizPlayer player, QuizGUI pGUI) {
        this.player = player;
        this.pGUI = pGUI;
        this.pGUI.addButtonListener(new MyButtonListener());
        this.pGUI.initCategoryButtonListener(new CategoryButtonListener());
        //t.start();
    }

    @Override
    public void run() {
        System.out.println("I RUN......");
        while(!Thread.interrupted()) {
            client.play();
        }
    }

    class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == pGUI.welcomeStartButton){
                String name;
                if(!(name=(pGUI.welcomeInput.getText()).trim()).isEmpty()) {
                    player.setName(name);
                    pGUI.changeWindow("1");
                }
            }
            if(e.getSource() == pGUI.answerButtons[0]){

                //messageFromServer=client.sendAndGetMessage(pGUI.getButtonText(0));
                for(int i = 0; i<1000; i++) {
                    System.out.println(pGUI.getButtonText(0));
                }
            }
            if(e.getSource() == pGUI.answerButtons[1]){
                String answer = pGUI.getButtonText(1);
                boolean isCorrect = client.sendAndGetMessage("true");
                JButton button = (JButton) e.getSource();
                if (isCorrect) {
                    button.setBackground(new Color(0,255,0));
                    System.out.println("Correct answer");
                } else {
                    button.setBackground(new Color(255,0,0));
                    System.out.println("Wrong answer");
                }

                //messageFromServer=client.sendAndGetMessage(pGUI.getButtonText(1));
            }
            if(e.getSource() == pGUI.answerButtons[2]){
                System.out.println("Alt.3 Pushed");
                //messageFromServer=client.sendAndGetMessage(pGUI.getButtonText(2));
            }
            if(e.getSource() == pGUI.answerButtons[3]){
                System.out.println("Alt.4 Pushed");
                //messageFromServer=client.sendAndGetMessage(pGUI.getButtonText(3));
                pGUI.setScoreBoard(2,4,false);
                pGUI.changeWindow("3");
            }
            if(e.getSource() == pGUI.scoreBoardStartButton){
                System.out.println("ScoreBoard");
                //messageFromServer=client.sendAndGetMessage(pGUI.getButtonText(3));

                pGUI.changeWindow("0");
            }
        }
    }
class CategoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Pick category window
            if(e.getSource() == pGUI.categoryButtons[0]){
                System.out.println("Chose category 1");
                pGUI.changeWindow("2");
            }
            else if(e.getSource() == pGUI.categoryButtons[1]){
                System.out.println("Chose category 2");
                pGUI.changeWindow("2");
            }
            else if(e.getSource() == pGUI.categoryButtons[2]){
                System.out.println("Chose category 3");
                pGUI.changeWindow("2");
            }

        }

    }

    public static void main(String[] args) throws Exception {
        QuizPlayer p = new QuizPlayer();
        QuizGUI pGUI = new QuizGUI();
        QuizClient c = new QuizClient();
        new QuizController(p,pGUI, c);
        //c.play();
    }
}
