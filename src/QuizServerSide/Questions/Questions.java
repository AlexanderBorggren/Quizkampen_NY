package QuizServerSide.Questions;

import QuizServerSide.QuizServerGame;
import QuizServerSide.QuizServerPlayer;
import java.util.ArrayList;

public class Questions {
    String category;
    String question;
    ArrayList<String> alternative;
    String correctAlternative;

    Questions(String category, String question, ArrayList<String> alternative, String correctAlternative) {
        this.category = category;
        this.question = question;
        this.alternative = alternative;
        this.correctAlternative = correctAlternative;
    }
    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAlternative() {
        return alternative;
    }

    public boolean checkAnswer(String answer, QuizServerPlayer player, QuizServerGame game){
        if((answer != null) && (!answer.isEmpty())) {
            answer = answer.substring(14, answer.length() - 16); // remove html tags
        }
        boolean result = answer.equals(correctAlternative);
        player.getAnswers()[player.getCurrentRound()][player.getCurrentQuestionWithinRound()] = result;
        return result;
    }
    public int correctAnswerIndex(){
        for(int i = 0; i < alternative.size(); i++){
            if(alternative.get(i).equals(correctAlternative)){
                return i;
            }
        }
        return 0;
    }
}






