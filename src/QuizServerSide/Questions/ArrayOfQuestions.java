package QuizServerSide.Questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ArrayOfQuestions {
    HashMap<String, ArrayList<Questions>> allQuestionsFromFile = new HashMap<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Questions> allQuestionsFromFileInList = new ArrayList<>();

    public ArrayOfQuestions() {
        System.out.println("Reading questions from file....");
        try (BufferedReader reader = new BufferedReader(new FileReader("src/QuizServerSide/Questions/Questions"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String category = line;
                if (!categories.contains(category)) {
                    categories.add(line);
                }
                String question = reader.readLine();
                String[] alternative = reader.readLine().split(";");
                ArrayList<String> listAlternatives = new ArrayList<>(Arrays.asList(alternative));
                Collections.shuffle(listAlternatives);
                String correctalternative = reader.readLine();

                allQuestionsFromFileInList.add(new Questions(category, question, listAlternatives, correctalternative));
            }
            generateHashMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void generateHashMap(){
        for(int i = 0; i<categories.size(); i++){
            ArrayList<Questions> tempList = new ArrayList<>();
            for(int j = 0; j<allQuestionsFromFileInList.size(); j++) {
                if (allQuestionsFromFileInList.get(j).getCategory().equals(categories.get(i))){
                    tempList.add(allQuestionsFromFileInList.get(j));
                }
                    allQuestionsFromFile.put(categories.get(i), tempList);
            }
        }
    }
    public Questions generateRandomQuestion(String categoryFromUser) {
        ArrayList<Questions> questionsWithCurrentCategory;
        //Populate a new list with questions for specific category
        // (allQuestionsFromFile is the one keeping tabs on already asked questions)
        questionsWithCurrentCategory = allQuestionsFromFile.get(categoryFromUser);

        Collections.shuffle(questionsWithCurrentCategory);

        Questions tempQuestion = questionsWithCurrentCategory.get(0);

        questionsWithCurrentCategory.remove(0);
        allQuestionsFromFile.put(categoryFromUser, questionsWithCurrentCategory);
        System.out.println(tempQuestion.question);
        return tempQuestion;
    }
    public String[] randomizeCategoryAlternatives(int nrOfCategories) {
        Collections.shuffle(this.categories);
        String[] nrOfRandomCategories = new String[nrOfCategories];
        for (int i = 0; i < nrOfCategories; i++) {
            nrOfRandomCategories[i] = this.categories.get(i);
        }
        return nrOfRandomCategories;
    }
}