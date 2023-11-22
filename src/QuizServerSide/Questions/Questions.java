package QuizServerSide.Questions;

import java.util.ArrayList;

public class Questions {
    /*ArrayList<String> questions =new ArrayList<>();
    ArrayList<String[]> alternativ =new ArrayList<>();
    ArrayList<String> answer =new ArrayList<>();
    ArrayList<String> kategori =new ArrayList<>();*/

    String category;
    String question;
    String[]answer;
    String correctAnswer;

    Questions(String category,String question,String[]answer, String correctAnswer){
        this.category=category;
        this.question=question;
        this.answer=answer;
        this.correctAnswer=correctAnswer;

        ArrayList<Questions>questionsAndAnswers=new ArrayList<>();

    }
}


