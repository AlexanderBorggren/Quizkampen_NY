package QuizClientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class QuizController implements Runnable{
    QuizPlayer player;
    QuizGUI pGUI;
    QuizClient client;
    Thread t = new Thread(this);
    NetworkProtocolClient networkProtocolClient;
    AnswerTimer timer;

    public QuizController(QuizPlayer player, QuizGUI pGUI, QuizClient client) {
        this.player = player;
        this.pGUI = pGUI;
        this.client = client;
        this.networkProtocolClient = new NetworkProtocolClient(this);
        this.pGUI.addButtonListener(new MyButtonListener());
        this.pGUI.initCategoryButtonListener(new CategoryButtonListener());

        t.start();
        pGUI.setVisible(true);
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
                    try {
                        networkProtocolClient.sendPlayerName(client.getOutputStream(), name);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            if(e.getSource() == pGUI.answerButtons[0]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[0].getText(),0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(e.getSource() == pGUI.answerButtons[1]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[1].getText(),1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(e.getSource() == pGUI.answerButtons[2]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[2].getText(),2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(e.getSource() == pGUI.answerButtons[3]){
                try {
                    networkProtocolClient.sendAnswer(client.getOutputStream(),pGUI.answerButtons[3].getText(),3);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
            for(int i = 0; i<pGUI.categoryButtons.length; i++){
                if(e.getSource() == pGUI.categoryButtons[i]){
                    try {
                        networkProtocolClient.sendChosenCategory(client.getOutputStream(),pGUI.categoryButtons[i].getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
    public static void main(String[] args){
        QuizPlayer p = new QuizPlayer();
        QuizClient c = new QuizClient();
        QuizGUI pGUI = new QuizGUI();
        new QuizController(p,pGUI, c);
    }
}
