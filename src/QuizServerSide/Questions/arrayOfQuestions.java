package QuizServerSide.Questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class arrayOfQuestions {
    public static void main(String[] args) {
        //Skapar en arraylist för frågor samt svar
        ArrayList<Questions> questionsAndalternatives = new ArrayList<>();
        //Läsa in frågor och svar
        try (BufferedReader reader = new BufferedReader(new FileReader("src/QuizServerSide/Questions/Questions"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String category = line;
                String question = reader.readLine();
                String[] alternative = reader.readLine().split(";");
                String correctalternative = reader.readLine();

                questionsAndalternatives.add(new Questions(category, question, alternative, correctalternative));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        //Ställer vardera fråga, kontrollerar sedan svar
        for (Questions questions:questionsAndalternatives){
            System.out.println(questions.category+"-"+questions.question);

            for (int i=0;i<questions.alternative.length;i++){
                System.out.println((i+1)+"."+questions.alternative[i]);
            }
            //Knappar blir det ju i GUI men kanske kopplar dem till siffror först?
            System.out.print("Skriv svarsnummer: ");
            int userAnswerIndex=scanner.nextInt()-1;

            if (questions.alternative[userAnswerIndex].equals(questions.correctalternative)) {
                System.out.println("Rätt\n");
            } else{
                System.out.println("Fel"+questions.correctalternative+"\n");
            }



        }

    }
}
