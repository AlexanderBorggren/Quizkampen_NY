package QuizClientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class QuizController implements Runnable{

    QuizPlayer player;
    QuizGUI pGUI;
    QuizClient client;
    String messageFromServer;
    String[] messageArrayFromServer;
    Thread t = new Thread(this);
    NetworkProtocolClient networkProtocolClient;
    AnswerTimer timer;

    int round = 0;
    boolean newRound = true;

    public QuizController(QuizPlayer player, QuizGUI pGUI, QuizClient client) {
        this.player = player;
        this.pGUI = pGUI;
        this.client = client;
        this.networkProtocolClient = new NetworkProtocolClient(this);
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

    public QuizController() {

    }

    QuizClient getClient() { return client; }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            client.play(networkProtocolClient);
        }
    }

    class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == pGUI.welcomeStartButton){
                String name;
                if(!(name=(pGUI.welcomeInput.getText()).trim()).isEmpty()) {
                    player.setName(name);
                    try {
                        networkProtocolClient.sendPlayerName(client.getOutputStream(), name);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                  /*  try {
                        client.getOutputStream().writeObject(new NetworkMessage(NetworkProtocolClient.PROTOCOL_SEND.SET_PLAYERNAME.ordinal()));
                        client.getOutputStream().writeObject(name);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }*/
                }
            }
            if(e.getSource() == pGUI.answerButtons[0]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[0].getText(),0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //System.out.println("pressed button");

            }
            if(e.getSource() == pGUI.answerButtons[1]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[1].getText(),1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //System.out.println("pressed button");

            }
            if(e.getSource() == pGUI.answerButtons[2]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[2].getText(),2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //System.out.println("pressed button");

            }
            if(e.getSource() == pGUI.answerButtons[3]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[3].getText(),3);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //System.out.println("pressed button");

            }
            if(e.getSource() == pGUI.scoreBoardStartButton){
                try {
                    networkProtocolClient.sendPlayerReady(client.getOutputStream());
                    pGUI.scoreBoardStartButton.setText("Waiting for other player to accept");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
class CategoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Pick category window
            if(e.getSource() == pGUI.categoryButtons[0]){
                try {
                    networkProtocolClient.sendChosenCategory(client.getOutputStream(),pGUI.categoryButtons[0].getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource() == pGUI.categoryButtons[1]){
                try {
                    networkProtocolClient.sendChosenCategory(client.getOutputStream(),pGUI.categoryButtons[1].getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource() == pGUI.categoryButtons[2]){
                try {
                    networkProtocolClient.sendChosenCategory(client.getOutputStream(),pGUI.categoryButtons[2].getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
