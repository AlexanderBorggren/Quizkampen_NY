package QuizServerSide.Questions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Questions {
    /*ArrayList<String> questions =new ArrayList<>();
    ArrayList<String[]> alternativ =new ArrayList<>();
    ArrayList<String> answer =new ArrayList<>();
    ArrayList<String> kategori =new ArrayList<>();*/

    String category;
    String question;
    String[] answer;
    String correctAnswer;

    Questions(String category, String question, String[] answer, String correctAnswer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }
}

public class arrayOfQuestions{

        ArrayList<Questions>questionsAndAnswers=new ArrayList<>();

        try (BufferedReader reader=new BufferedReader(new FileReader("src/DataBase/Questions"))){
            String line;
            while ((line=reader.readLine()!=null)) {
                String category = line;
                String question = reader.readLine();
                String[] answer = reader.readLine().split(";");
                String correctAnswer=reader.readLine();

                questionsAndAnswers.add(new Questions(category,question,answer,correctAnswer));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}



