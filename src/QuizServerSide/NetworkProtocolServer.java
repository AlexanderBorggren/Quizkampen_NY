package QuizServerSide;

import QuizServerSide.Questions.Questions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class NetworkProtocolServer {
    NetworkProtocolServer(QuizServerPlayer player){
        this.player = player;
    }
    QuizServerPlayer player;
    enum PROTOCOL_SEND
    {
        PLAYER_READY,
        SEND_CATEGORIES,
        OPPONENT_NAME,
        SEND_QUESTION,
        SEND_ANSWER_RESULT,
        CHANGE_WINDOW,
        SCORE,
        OPPONENT_NOT_READY,
        PLAYER_TO_CHOOSE_CATEGORY,
        SEND_CATEGORY_TO_OPPONENT,
        SEND_BUTTON_RESET_COLOR,
        UPDATE_WINNER_LABEL,
        RESET_START_NEW_ROUND_BUTTON,
        DISABLE_START_NEW_ROUND_BUTTON,
        CORRECT_ANSWER_INDEX,
        OPPONENT_ALL_ANSWERS,
        OPPONENT_CURRENT_ROUND_ANSWERS,
        RESET_ANSWER_RESULT_ICONS_FOR_ROUND,
        SEND_ROUND_SCORE,
        IS_WINNER,
        SEND_PROPERTIES,
    }

    public void parseSetPlayerName(ObjectInputStream inputStream, QuizServerPlayer player) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        player.setPlayerName((String)lastReadObject);
        player.setReady(true);
    }
    public boolean parseAnswerQuestion(ObjectInputStream inputStream, QuizServerPlayer player) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        player.setReady(true);
        boolean result=player.currentQuestion.checkAnswer((String)lastReadObject, player, player.game);
        lastReadObject=inputStream.readObject();
        player.lastAnsweredButtonIndex=(int) lastReadObject;

        return result;
    }
    public void parseChosenCategory(ObjectInputStream inputStream, QuizServerPlayer player) throws IOException, ClassNotFoundException {
        Object lastReadObject = inputStream.readObject();
        player.game.setCurrentCategory((String)lastReadObject);
    }
    public void parseGetReady(ObjectInputStream input, QuizServerPlayer quizServerPlayer) throws IOException, ClassNotFoundException {
        Object lastReadObject = input.readObject();
        quizServerPlayer.setReady((boolean)lastReadObject);
    }
    public void sendOpponentName(ObjectOutputStream outputStream, String name) throws IOException {
        outputStream.writeObject(new NetworkMessage(NetworkProtocolServer.PROTOCOL_SEND.OPPONENT_NAME.ordinal()));
        outputStream.writeObject((String)name);
    }
    public void sendCategories(ObjectOutputStream outputStream, String[] categories) throws IOException{
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SEND_CATEGORIES.ordinal()));
        outputStream.writeObject(categories);
    }
    public void sendQuestion(ObjectOutputStream outputStream, Questions question) throws IOException{
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SEND_QUESTION.ordinal()));
        outputStream.writeObject(question.getQuestion());
        outputStream.writeObject(question.getAlternative());
    }
    public void sendAnswerResult(ObjectOutputStream outputStream, boolean result) throws IOException{
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SEND_ANSWER_RESULT.ordinal()));
        outputStream.writeObject(result);
        outputStream.writeObject(player.lastAnsweredButtonIndex);
    }
    public void sendChangeWindow(ObjectOutputStream outputStream,String windowNumber) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.CHANGE_WINDOW.ordinal()));
        outputStream.writeObject(windowNumber);
    }
    public void sendScore(ObjectOutputStream outputStream,int score) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SCORE.ordinal()));
        outputStream.writeObject(score);
    }
    public void sendOpponentNotReady(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.OPPONENT_NOT_READY.ordinal()));
        outputStream.writeObject((boolean)false);
    }
    public void sendIsPlayerToChooseCategory(ObjectOutputStream outputStream, boolean pickCategory) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.PLAYER_TO_CHOOSE_CATEGORY.ordinal()));
        outputStream.writeObject(pickCategory);
    }
    public void sendCategoryToOpponent(ObjectOutputStream outputStream, String category) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SEND_CATEGORY_TO_OPPONENT.ordinal()));
        outputStream.writeObject(category);
    }
    public void sendButtonResetColor(ObjectOutputStream outputStream,int buttonIndex) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SEND_BUTTON_RESET_COLOR.ordinal()));
        outputStream.writeObject(buttonIndex);
    }
    public void sendUpdateWinnerLabel(ObjectOutputStream outputStream, boolean winner) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.UPDATE_WINNER_LABEL.ordinal()));
        outputStream.writeObject(winner);
    }
    public void sendResetStartNewRoundButton(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.RESET_START_NEW_ROUND_BUTTON.ordinal()));
    }
    public void sendDisableStartNewRoundButton(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.DISABLE_START_NEW_ROUND_BUTTON.ordinal()));
    }
    public void sendCorrectAnswerIndex(ObjectOutputStream outputStream, int buttonIndex) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.CORRECT_ANSWER_INDEX.ordinal()));
        outputStream.writeObject(buttonIndex);
    }
    public void sendAnswersForRound(ObjectOutputStream outputStream, int playerId, boolean[] roundAnswers, int currentRound) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.OPPONENT_CURRENT_ROUND_ANSWERS.ordinal()));
        outputStream.writeObject(playerId);
        outputStream.writeObject(roundAnswers);
        outputStream.writeObject(currentRound);
    }
    public void sendResetAnswerResultIconsForRound(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.RESET_ANSWER_RESULT_ICONS_FOR_ROUND.ordinal()));
    }

    public void sendRoundScores(ObjectOutputStream outputStream, int player1Score, int player2Score) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SEND_ROUND_SCORE.ordinal()));
        outputStream.writeObject(player1Score);
        outputStream.writeObject(player2Score);
    }

    public void sendIsWinner(ObjectOutputStream outputStream, int isWinner) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.IS_WINNER.ordinal()));
        outputStream.writeObject(isWinner);
    }
    public void sendProperties(ObjectOutputStream outputStream, Integer[] properties) throws IOException {
        outputStream.writeObject(new NetworkMessage(PROTOCOL_SEND.SEND_PROPERTIES.ordinal()));
        outputStream.writeObject(properties);
    }
}
