package QuizServerSide;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class QuizServerGame implements Serializable {
    //NETWORK
    NetworkProtocol networkProtocol;

    //GAME
    QuizServerPlayer playerOne;
    QuizServerPlayer playerTwo;
    int currentRound = 0;
    int totalRounds = 0;
    int numberOfQuestionsPerRound = 2;
    String currentQuestion;
    ArrayList<String> currentAnswerAlternatives = new ArrayList<>();
    String currentCorrectAnswer;

    QuizServerGame()
    {

    }
    /*
    QuizServerGame(QuizServerPlayer playerOne, QuizServerPlayer playerTwo)
    {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

     */

    public void setCurrentRound(int currentRound) { this.currentRound = currentRound; }
    public int getCurrentRound() { return currentRound; }

    public void setTotalRounds(int totalRounds) { this.totalRounds = totalRounds; }
    public int getTotalRounds() { return totalRounds; }

    public void setCurrentQuestion(String currentQuestion) { this.currentQuestion = currentQuestion; }
    public String getCurrentQuestion() { return currentQuestion; }

    public void setCurrentAnswerAlternatives(ArrayList<String> currentAnswerAlternatives) { this.currentAnswerAlternatives = currentAnswerAlternatives; }
    public ArrayList<String> getCurrentAnswerAlternatives() { return currentAnswerAlternatives; }

    public void setCurrentCorrectAnswer(String currentCorrectAnswer) { this.currentCorrectAnswer = currentCorrectAnswer; }
    public String getCurrentCorrectAnswer() { return currentCorrectAnswer; }

    public void setNumberOfQuestionsPerRound(int numberOfQuestionsPerRound) { this.numberOfQuestionsPerRound = numberOfQuestionsPerRound; }
    public int getNumberOfQuestionsPerRound() { return numberOfQuestionsPerRound; }
    public NetworkProtocol getNetworkProtocol() { return networkProtocol; }
}

