package QuizServerSide.Questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayOfQuestions {
    ArrayList<Questions> questionsAndAlternatives = new ArrayList<>();

    public ArrayOfQuestions() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/QuizServerSide/Questions/Questions"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String category = line;
                String question = reader.readLine();
                String[] alternative = reader.readLine().split(";");
                String correctalternative = reader.readLine();

                questionsAndAlternatives.add(new Questions(category, question, alternative, correctalternative));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Questions generateRandomQuestion(String categoryFromUser){
       Questions question;
       ArrayList <Questions> qn=new ArrayList<>();

       for (Questions questions : questionsAndAlternatives){
            if ((questions.category).equals(categoryFromUser)){
                qn.add(questions);
            }
        }

       Collections.shuffle(qn);

       return qn.get(0);



    }

    /*public String[] randomizeAnswerAlternatives(String[] answers){





        return answers;
    }

     */

    public String[] randomizeAnswerAlternatives(String[] answers) {
        List<String> shuffledAlternatives = new ArrayList<>(Arrays.asList(answers));

        Collections.shuffle(shuffledAlternatives);

        return shuffledAlternatives.toArray(new String[0]);
    }







    public static void main(String[] args) {
        //Skapar en arraylist för frågor samt svar
        //Läsa in frågor och svar
        ArrayOfQuestions aq=new ArrayOfQuestions();
        Questions q;

        q=aq.generateRandomQuestion("Sci-fi:");
        System.out.println(q.question);
        System.out.println(q.correctAlternative);
        //if (aq.checkAnswer(q.correctAlternative, q.question))
            //System.out.println("Winner");



        String[] shuffledAlternatives = aq.randomizeAnswerAlternatives(q.alternative);

        System.out.println("");
        for (int i = 0; i < shuffledAlternatives.length; i++) {
            System.out.println((i + 1) + ". " + shuffledAlternatives[i]);
        }


        /*Scanner scanner = new Scanner(System.in);
        //Ställer vardera fråga, kontrollerar sedan svar
        for (Questions questions : aq.questionsAndAlternatives) {
            System.out.println(questions.category + "-" + questions.question);

            for (int i = 0; i < questions.alternative.length; i++) {
                System.out.println((i + 1) + "." + questions.alternative[i]);
            }
            //Knappar blir det ju i GUI men kanske kopplar dem till siffror först?
            System.out.print("Skriv svarsnummer: ");
            int userAnswerIndex = scanner.nextInt() - 1;

            if (questions.alternative[userAnswerIndex].equals(questions.correctalternative)) {
                System.out.println("Rätt\n");
            } else {
                System.out.println("Fel" + questions.correctalternative + "\n");
            }


        }*/

    }
}