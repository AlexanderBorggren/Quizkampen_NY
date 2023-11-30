package QuizClientSide;

public class QuizPlayer {
    private boolean readyToStartGame = false;
    String currentQuestion;
    String[] currentAlternatives;
    boolean currentAnsweredResult;
    boolean opponentReady;

    //Implement for keep track on which question and which round we are in.
    int nrOfQuestionsPerRound = 2; // Hämta från server
    int nrOfRounds = 2; // Hämta från server
    int questionNr = -1;
    int roundNr = 0;
    Boolean[][] answers;
    Boolean[][] opponentAnswers;

    public QuizPlayer(){}

    //Räknar vilken fråga o round vi är på. Anropas från protokoll varje gång man tar emot en fråga.
    public void roundAndQuestionCounter(){
        if((this.questionNr == this.nrOfQuestionsPerRound-1) && (this.roundNr==this.nrOfRounds-1)) {
            this.questionNr = -1;
            this.roundNr = 0;
        }
        this.questionNr++;
        if(this.questionNr>this.nrOfQuestionsPerRound-1) {
            this.roundNr++;
            this.questionNr = 0;
        }
    }

    public void setAnswerResult(int round, int question, boolean answer) {
        this.answers[round][question] = answer;
    }

    public void setOpponentAnswerResult(int round, int question, boolean answer) {
        this.opponentAnswers[round][question] = answer;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public int getRoundNr() {
        return roundNr;
    }

    public boolean getReadyToStartGame() {
        return readyToStartGame;
    }

    public void setReadyToStartGame(boolean readyToStartGame) {
        this.readyToStartGame = readyToStartGame;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void setCurrentAlternatives(String[] currentAlternatives) {
        this.currentAlternatives = currentAlternatives;
    }

    public void setCurrentAnsweredResult(boolean currentAnsweredResult) {
        this.currentAnsweredResult = currentAnsweredResult;
    }
    public void setOpponentReady(boolean opponentReady) {
        this.opponentReady = opponentReady;
    }

    public void setNrOfQuestionsPerRound(int nrOfQuestionsPerRound) {
        this.nrOfQuestionsPerRound = nrOfQuestionsPerRound;
    }

    public void setNrOfRounds(int nrOfRounds) {
        this.nrOfRounds = nrOfRounds;
    }

    public void setPlayerAnswers(int nrOfRounds, int nrOfQuestionsPerRound) {
        this.answers = new Boolean[nrOfRounds][nrOfQuestionsPerRound];
        this.opponentAnswers = new Boolean[nrOfRounds][nrOfQuestionsPerRound];
    }
}
