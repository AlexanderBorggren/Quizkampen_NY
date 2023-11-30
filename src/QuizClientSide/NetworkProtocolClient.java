package QuizClientSide;

import QuizServerSide.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NetworkProtocolClient {
    NetworkProtocolClient(QuizController quizController) {
        this.quizController = quizController;
    }

    QuizController quizController;

    enum PROTOCOL_SEND {
        SET_PLAYERNAME,
        GET_CATEGORY,
        SEND_ANSWER,
        CHOSEN_CATEGORY,
        PLAYER_READY,
    }

    public void parsePacket(ObjectInputStream inputStream, NetworkMessage networkMessage) throws IOException, ClassNotFoundException {
        int networkCode = networkMessage.getNetworkCode();

        switch (networkCode) {
            case 0:
                parsePlayerReady(inputStream);
                break;
            case 1:
                parseGetCategories(inputStream);
                break;
            case 2:
                parseGetOpponentName(inputStream);
                break;
            case 3:
                parseSendQuestion(inputStream);
                break;
            case 4:
                parseAnswerResult(inputStream);
                break;
            case 5:
                parseChangeWindow(inputStream);
                break;
            case 6:
                parseScore(inputStream);
                break;
            case 7:
                parseOpponentNotReady(inputStream);
                break;
            case 8:
                parseIsPlayerToChooseCategory(inputStream);
                break;
            case 9:
                parseGetChosenCategory(inputStream);
                break;
            case 10:
                parseButtonResetColor(inputStream);
                break;
            case 11:
                parseUpdateWinnerLabel(inputStream);
                break;
            case 12:
                parseResetStartNewRoundButton();
                break;
            case 13:
                parseDisableStartNewRoundButton();
                break;
            case 14:
                parseCorrectAnswerIndex(inputStream);
                break;
            case 15:
                parseOpponentAllAnswers(inputStream);
                break;
            case 16:
                parseOpponentAnswersForRound(inputStream);
                break;
            case 17:
                parseUpdateRoundAnswerIcons();
                break;
            case 18:
                parseRoundScores(inputStream);
                break;
            case 19:
                parseIsWinner(inputStream);
                break;
            case 20:
                parseGetProperties(inputStream);
                break;
            case 21:
                break;
            case 22:
                break;
        }
    }

    public void sendAnswer(ObjectOutputStream outputStream, String answer, int buttonIndex) throws IOException {
        outputStream.writeObject(new NetworkMessage(NetworkProtocolClient.PROTOCOL_SEND.SEND_ANSWER.ordinal()));
        outputStream.writeObject(answer);
        outputStream.writeObject(buttonIndex);
        quizController.pGUI.setLastAnsweredQuestion(answer);
    }

    public void sendPlayerName(ObjectOutputStream outputStream, String name) throws IOException {
        outputStream.writeObject(new NetworkMessage(NetworkProtocolClient.PROTOCOL_SEND.SET_PLAYERNAME.ordinal()));
        outputStream.writeObject(name);
        quizController.pGUI.setNameLabels(name);
    }
    public void sendChosenCategory(ObjectOutputStream outputStream, String name) throws IOException {
        outputStream.writeObject(new NetworkMessage(NetworkProtocolClient.PROTOCOL_SEND.CHOSEN_CATEGORY.ordinal()));
        outputStream.writeObject(name);
        quizController.pGUI.setCategoryNameLabel(name);
        quizController.pGUI.setCategoryIcon(name);
    }
    public void sendPlayerReady(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(new NetworkMessage(NetworkProtocolClient.PROTOCOL_SEND.PLAYER_READY.ordinal()));
        outputStream.writeObject((boolean)true);
    }
    public void parsePlayerReady(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.player.setReadyToStartGame((boolean) lastReadObject);
    }

    public void parseGetCategories(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        String[] categories = (String[]) lastReadObject;
        quizController.pGUI.setCategoryButtonText(categories);
    }
    public void parseGetOpponentName(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.pGUI.setOpponentName((String) lastReadObject);
    }
    public void parseChangeWindow(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.pGUI.changeWindow((String) lastReadObject);
    }
    public void parseScore(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.pGUI.setScorePlayer1((int) lastReadObject);
    }

    public void parseOpponentNotReady(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.player.setOpponentReady((boolean) lastReadObject);
        quizController.pGUI.categoryLabelPickCategory.setText("Waiting for other player");
    }

    public void parseSendQuestion(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        //Skickar från klient till server
        Object lastReadObject = (String) inputStream.readObject();
        String str = (String) lastReadObject;
        quizController.player.setCurrentQuestion("<html><center>" + str + "</center></html>");
        quizController.pGUI.questionLabel.setText("<html><center>" + str + "</center></html>");

        lastReadObject = inputStream.readObject();
        ArrayList<String> alternatives = (ArrayList<String>) lastReadObject;
        String[] alternativesStringArray = new String[alternatives.size()];

        int i = 0;
        for(String alt : alternatives)
        {
            alternativesStringArray[i] = alt;
            i++;
        }
        quizController.player.setCurrentAlternatives(alternativesStringArray);
        quizController.pGUI.answerButtons[0].setText("<html><center>" + alternativesStringArray[0] + "</center></html>");
        quizController.pGUI.answerButtons[1].setText("<html><center>" + alternativesStringArray[1] + "</center></html>");
        quizController.pGUI.answerButtons[2].setText("<html><center>" + alternativesStringArray[2] + "</center></html>");
        quizController.pGUI.answerButtons[3].setText("<html><center>" + alternativesStringArray[3] + "</center></html>");

        if(quizController.timer != null)
        {
            quizController.timer.getTimer().stop();
        }
        quizController.timer = new AnswerTimer(quizController);
        quizController.player.roundAndQuestionCounter(); // Updatera Player current round and question
    }

    public void parseAnswerResult(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        boolean result = ((boolean) lastReadObject);
        int buttonIndex = (int) inputStream.readObject();
        quizController.player.setCurrentAnsweredResult(result);
        if (buttonIndex != 4) {
            quizController.pGUI.changeAnsweredButtonColor(result, buttonIndex);
        }  // Uppdater score för spelaren i frågeomgången
        quizController.pGUI.setCurrentScoreBoard(quizController.player.getQuestionNr(), result);
        quizController.player.setAnswerResult(quizController.player.getRoundNr(),quizController.player.getQuestionNr(), result);
    }

    public void parseIsPlayerToChooseCategory(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.pGUI.changeCategoryWindowState((boolean) lastReadObject);
    }

    public void parseGetChosenCategory(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.pGUI.setCategoryNameLabel((String) lastReadObject);
        quizController.pGUI.setCategoryIcon((String) lastReadObject);
    }

    public void parseButtonResetColor(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.pGUI.changeAnsweredButtonReset((int) lastReadObject);
    }

    public void parseUpdateWinnerLabel(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        if ((boolean) lastReadObject) {
            quizController.pGUI.whoTurnLabel.setText(quizController.pGUI.player2NameLabel.getText() + " disconnected");
        }
    }
    public void parseResetStartNewRoundButton() {
        quizController.pGUI.scoreBoardStartButton.setText(quizController.pGUI.getScoreBoardStartButtonText());
    }
    public void parseDisableStartNewRoundButton() {
        quizController.pGUI.scoreBoardStartButton.setVisible(false);
    }
    public void parseCorrectAnswerIndex(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        quizController.pGUI.changeAnsweredButtonColor(true, (int) lastReadObject);
    }

    public void parseOpponentAllAnswers(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();

        int i = 0;
        int j;

        for (boolean[] round : (boolean[][]) lastReadObject) {
            j = 0;
            for (boolean currentQuestion : round){
                quizController.player.setOpponentAnswerResult(i,j, currentQuestion);
                quizController.pGUI.setScoreBoard(2,i,j, currentQuestion);

                j++;
            }
            i++;
        }

    }

    public void parseOpponentAnswersForRound(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        int playerId = (int)inputStream.readObject();
        boolean[] answerResults = (boolean[])inputStream.readObject();
        int round = (int)inputStream.readObject();
        int i = 0;
        for (boolean answerResult : answerResults){

            if(playerId == 2)
                quizController.player.setOpponentAnswerResult(round,i, answerResult);
            else
                quizController.player.setAnswerResult(round,i, answerResult);

            quizController.pGUI.setScoreBoard(playerId,round,i, answerResult);
            i++;
        }

    }

    public void parseUpdateRoundAnswerIcons() {
        quizController.pGUI.resetCurrentScoreBoard();//Nollställ plupparna vid ny questionrunda
    }

    public void parseRoundScores(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        int scoreplayer1 = (int) inputStream.readObject();
        int scoreplayer2 = (int) inputStream.readObject();

        quizController.pGUI.setScorePlayer1(scoreplayer1);
        quizController.pGUI.setScorePlayer2(scoreplayer2);
    }

    public void parseIsWinner(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        int isWinner = (int) inputStream.readObject();
        String newText = "";
        if(isWinner == 1)
        {
            newText = "You Lose!";
        }
        if(isWinner == 2)
        {
            newText = "You Win!";
        }
        if(isWinner == 3)
        {
            newText = "Draw!";
        }
        quizController.pGUI.whoTurnLabel.setText(newText);
    }

    public void parseGetProperties(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Integer[] properties = (Integer[]) inputStream.readObject();
        quizController.player.setNrOfQuestionsPerRound(properties[0]);
        quizController.player.setNrOfRounds(properties[1]);
        quizController.player.setPlayerAnswers(properties[1], properties[0]);
        quizController.pGUI.setNrOfRounds(properties[1]);
        quizController.pGUI.setNrOfCategories(properties[2]);
        quizController.pGUI.setNrOfQuestionsPerRound(properties[0]);
        quizController.pGUI.initWelcomeWindow();
        quizController.pGUI.initPlayWindow();
        quizController.pGUI.initCategoryWindow();
        quizController.pGUI.initScoreboardWindow();
    }
}