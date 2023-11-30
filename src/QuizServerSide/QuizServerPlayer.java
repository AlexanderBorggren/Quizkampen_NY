package QuizServerSide;

import QuizServerSide.Questions.Questions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class QuizServerPlayer extends Thread implements Serializable {

    private final int INIT=0;
    private final int LOBBY=1;
    private final int CATEGORY = 2;
    private final int GAME = 3;
    private final int SCORE = 4;
    private final int END = 5;
    int status = INIT;

    //NETWORK
    transient Socket socket;
    transient ObjectInputStream input;
    transient ObjectOutputStream output;
    QuizServerGame game;
    NetworkProtocolServer serverProtocol;

    //GAME
    int score;
    boolean categoryPicker;
    QuizServerPlayer opponent;
    String playerName;
    Questions currentQuestion;
    boolean ready;
    boolean newQuestionGenerated;
    int lastAnsweredButtonIndex;
    int currentQuestionWithinRound;
    int currentRound = 0;
    int[]scoresPerRound=new int[2];
    boolean[][] answers;

    public QuizServerPlayer(Socket socket, QuizServerGame game, boolean categoryPicker) {
        this.game = game;
        this.socket = socket;
        this.serverProtocol = new NetworkProtocolServer(this);
        this.categoryPicker = categoryPicker;
        this.answers = new boolean[game.totalRounds][game.numberOfQuestionsPerRound];
        try {
            output = new ObjectOutputStream(this.socket.getOutputStream());
            input = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setOpponent(QuizServerPlayer opponent) {
        this.opponent = opponent;
    }

    public void setScore(int score) throws IOException {
        this.score += score;
        getNetworkProtocolServer().sendScore(output,score);
    }
    public String getPlayerName() {
        return playerName;
    }
    public NetworkProtocolServer getNetworkProtocolServer() {
        return serverProtocol;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ObjectOutputStream getOutputStream() {
        return output;
    }

    public boolean getReady() {
        return ready;
    }
    public void setReady(boolean ready) {
        this.ready = ready;
    }
    public boolean getCategoryPicker() {
        return categoryPicker;
    }
    public void switchCategoryPicker(){
        if (categoryPicker) {
            categoryPicker = false;
        } else {
            categoryPicker = true;
        }
    }
    public boolean isNewQuestionGenerated() {
        return newQuestionGenerated;
    }

    public void setNewQuestionGenerated(boolean newQuestionGenerated) {
        this.newQuestionGenerated = newQuestionGenerated;
    }
    public boolean[][] getAnswers() {
        return answers;
    }
    public int getCurrentQuestionWithinRound() {
        return currentQuestionWithinRound;
    }
    public int getCurrentRound() {
        return currentRound;
    }

    public int[] checkWhoWonRound(boolean[] answersCurrentRound,boolean[]opponentAnswerCurrentRound,int[]scoresPerRound){

        int[] scoresPerQuestion = new int[2];

        for(int i = 0; i < answersCurrentRound.length; i++) {
            if (answersCurrentRound[i] && opponentAnswerCurrentRound[i]) {
                scoresPerQuestion[0]++;
                scoresPerQuestion[1]++;
            } else if (answersCurrentRound[i] && !(opponentAnswerCurrentRound[i])) {
                scoresPerQuestion[0]++;
            } else if (!(answersCurrentRound[i]) && opponentAnswerCurrentRound[i]) {
                scoresPerQuestion[1]++;
            }
        }
        if (scoresPerQuestion[0] > scoresPerQuestion[1]) {
            scoresPerRound[0]++;

        } else if (scoresPerQuestion[0] < scoresPerQuestion[1]) {
            scoresPerRound[1]++;
        }
        else {
            scoresPerRound[0]++;
            scoresPerRound[1]++;
        }
        return scoresPerRound;
    }

    public void startNewQuestion(boolean whosTurn) throws IOException, ClassNotFoundException, InterruptedException {
        // Plocka nästa fråga från databas
        serverProtocol.sendButtonResetColor(output, lastAnsweredButtonIndex);
        while (!isNewQuestionGenerated() && !whosTurn) {
            Thread.sleep(1);
        }
        if (whosTurn) {
            currentQuestion = game.getAq().generateRandomQuestion(game.getCurrentCategory());
            opponent.currentQuestion = currentQuestion;
            setNewQuestionGenerated(true);
            opponent.setNewQuestionGenerated(true);
        }
        //skickar fråga och alternativ
        serverProtocol.sendQuestion(output, currentQuestion);
        setReady(false);
        //ta emot svar
        Object lastReadObject = input.readObject();
        if (lastReadObject instanceof NetworkMessage) {
            boolean correctAnswer = serverProtocol.parseAnswerQuestion(input, this); //ParseAnswer calls Question.checkAnswer()
            if (correctAnswer){
                setScore(1);
            }
            else {
                serverProtocol.sendCorrectAnswerIndex(output, currentQuestion.correctAnswerIndex());
            }
            //validera svar mot correctanswer och skicka tillbaks
            serverProtocol.sendAnswerResult(output, correctAnswer);
        }
        while(true) {
            Thread.sleep(1);
            if (getReady() && (opponent.getReady())) {
                break;
            }
        }
        if(whosTurn){
            setNewQuestionGenerated(false);
            opponent.setNewQuestionGenerated(false);
        }
        Thread.sleep(game.delayStartNewQuestion);

    }

    public void run()
    {
        try {
            if(status == INIT){
                Integer[] propertiesToSend = {game.getNumberOfQuestionsPerRound(),game.getTotalRounds(), game.getNrOfCategories()};
                serverProtocol.sendProperties(output, propertiesToSend);

                status=LOBBY;
                output.flush();
            }
            while (true) {
                if (status == LOBBY) {
                    //Player set name
                    Object lastReadObject = input.readObject();
                    System.out.println(lastReadObject);
                    if (lastReadObject instanceof NetworkMessage) {
                        serverProtocol.parseSetPlayerName(input, this);
                        serverProtocol.sendOpponentNotReady(output);
                        serverProtocol.sendChangeWindow(output, "1");

                        while (true) {
                            Thread.sleep(1); // Sleep because else it will skip while loop because optimization
                            if(getReady() && ((opponent != null) && opponent.getReady())) {
                                break;
                            }
                        }
                        //Send opponent name to update GUI label
                        serverProtocol.sendOpponentName(output, opponent.getPlayerName());

                        status = CATEGORY;
                        output.flush();
                    }
                }
                if (status == CATEGORY) {
                    currentQuestionWithinRound = 0;
                    serverProtocol.sendResetAnswerResultIconsForRound(output);

                    if(categoryPicker) {
                        game.categories = game.aq.randomizeCategoryAlternatives(game.nrOfCategories);
                        serverProtocol.sendCategories(output, game.categories);
                    }
                    if(opponent.getReady()) {
                        serverProtocol.sendIsPlayerToChooseCategory(output, categoryPicker);
                    }
                    if(getCategoryPicker()) {
                        Object lastReadObject = input.readObject();
                        if (lastReadObject instanceof NetworkMessage) {
                            serverProtocol.parseChosenCategory(input, this);
                            serverProtocol.sendChangeWindow(output, "2");
                            opponent.getNetworkProtocolServer().sendChangeWindow(opponent.getOutputStream(), "2");
                            //Send Current Category to Opponent
                            opponent.getNetworkProtocolServer().sendCategoryToOpponent(opponent.getOutputStream(), game.currentCategory);
                        }
                    }
                    status = GAME;
                    output.flush();
                }

                if (status == GAME) {
                    startNewQuestion(getCategoryPicker());
                    currentQuestionWithinRound++;

                    if (currentQuestionWithinRound == game.numberOfQuestionsPerRound) {
                        serverProtocol.sendChangeWindow(output, "3");

                        status = SCORE;
                        output.flush();
                    }
                }

                if(status == SCORE) {
                    checkWhoWonRound(answers[currentRound],opponent.answers[currentRound],scoresPerRound);
                    serverProtocol.sendRoundScores(output, scoresPerRound[0],scoresPerRound[1]);
                    serverProtocol.sendAnswersForRound(output, 1, answers[currentRound], currentRound);
                    serverProtocol.sendAnswersForRound(output, 2, opponent.answers[currentRound], currentRound);

                    currentRound++;
                    serverProtocol.sendResetStartNewRoundButton(output);
                    switchCategoryPicker();

                    if (currentRound != game.getTotalRounds()) {
                        setReady(false);
                        //Wait for both player to press "Start new round" from score window
                        Object lastReadObject = input.readObject();
                        if (lastReadObject instanceof NetworkMessage) {
                            serverProtocol.parseGetReady(input, this);
                        }
                        while (true) {
                            Thread.sleep(1); //sleep because optimization
                            if (getReady() && (opponent.getReady())) {
                                break;
                            }
                        }
                        serverProtocol.sendChangeWindow(output, "1");
                        status = CATEGORY;
                        output.flush();
                    }
                    else {
                        serverProtocol.sendDisableStartNewRoundButton(output);

                        if (currentRound == game.getTotalRounds()) {
                            //You lose
                            if(scoresPerRound[0] < scoresPerRound[1]){
                                serverProtocol.sendIsWinner(output, 1);
                            }
                            //You win
                            else if(scoresPerRound[0] > scoresPerRound[1]){
                                serverProtocol.sendIsWinner(output, 2);
                            }
                            //Draw
                            else {
                                serverProtocol.sendIsWinner(output, 3);
                            }
                        }
                        status = END;
                        output.flush();
                    }
                }
            }
        }catch (IOException ex) {
            System.out.println(getPlayerName() + " lost connection");
            try {
                opponent.getNetworkProtocolServer().sendChangeWindow(opponent.getOutputStream(),"3");
                opponent.getNetworkProtocolServer().sendUpdateWinnerLabel(opponent.getOutputStream(),true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}