package QuizServerSide;


import QuizServerSide.Questions.ArrayOfQuestions;
import QuizServerSide.Questions.Questions;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class QuizServerGame implements Serializable {

    //GAME
    QuizServerPlayer playerOne;
    QuizServerPlayer playerTwo;
    int currentRound = 0;
    int totalRounds = 0;
    int numberOfQuestionsPerRound = 2;
    int currentQuestionWithinRound = 0;
    int nrOfCategories = 3;
    int delayStartNewQuestion;
    String[] categories; // = new String[nrOfCategories];
    boolean categoriesGotten = false;
    String currentCategory;
    String currentQuestion;
    ArrayList<String> currentAnswerAlternatives = new ArrayList<>();
    String currentCorrectAnswer;
    ArrayOfQuestions aq;
    Properties p = new Properties();
    String filename = "src/Quizkampen.properties";

    QuizServerGame() {
        aq = new ArrayOfQuestions();
        readPropertyFile();
        try {
            this.numberOfQuestionsPerRound = Integer.parseInt(p.getProperty("NrOfQuestions", "2"));
            this.totalRounds = Integer.parseInt(p.getProperty("NrOfRounds", "2"));
            this.nrOfCategories = Integer.parseInt(p.getProperty("nrOfCategories", "3"));
            this.delayStartNewQuestion = Integer.parseInt(p.getProperty("DelayStartNewQuestion", "3000"));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        categories = new String[nrOfCategories];
    }

    public void readPropertyFile() {
        try {
            p.load(new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentAnswerAlternatives(ArrayList<String> currentAnswerAlternatives) {
        this.currentAnswerAlternatives = currentAnswerAlternatives;
    }

    public ArrayList<String> getCurrentAnswerAlternatives() {
        return currentAnswerAlternatives;
    }

    public void setCurrentCorrectAnswer(String currentCorrectAnswer) {
        this.currentCorrectAnswer = currentCorrectAnswer;
    }

    public String getCurrentCorrectAnswer() {
        return currentCorrectAnswer;
    }

    public void setNumberOfQuestionsPerRound(int numberOfQuestionsPerRound) {
        this.numberOfQuestionsPerRound = numberOfQuestionsPerRound;
    }

    public int getNumberOfQuestionsPerRound() {
        return numberOfQuestionsPerRound;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public ArrayOfQuestions getAq() {
        return aq;
    }

    public void setAq(ArrayOfQuestions aq) {
        this.aq = aq;
    }
}
