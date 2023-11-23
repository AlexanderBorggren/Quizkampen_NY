package QuizServerSide.Questions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Questions {
    /*ArrayList<String> questions =new ArrayList<>();
    ArrayList<String[]> alternativ =new ArrayList<>();
    ArrayList<String> alternative =new ArrayList<>();
    ArrayList<String> kategori =new ArrayList<>();*/

    String category;
    String question;
    String[] alternative;
    String correctalternative;

    Questions(String category, String question, String[] alternative, String correctalternative) {
        this.category = category;
        this.question = question;
        this.alternative = alternative;
        this.correctalternative = correctalternative;
    }
}

public class arrayOfQuestions{

        ArrayList<Questions>questionsAndalternatives=new ArrayList<>();

        try (BufferedReader reader=new BufferedReader(new FileReader("src/DataBase/Questions"))){
            String line;
            while ((line=reader.readLine()!=null)) {
                String category = line;
                String question = reader.readLine();
                String[] alternative = reader.readLine().split(";");
                String correctalternative=reader.readLine();

                questionsAndalternatives.add(new Questions(category,question,alternative,correctalternative));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}



