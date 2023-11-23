package QuizServerSide.Questions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Questions {
    /*ArrayList<String> questions =new ArrayList<>();
    ArrayList<String[]> alternativ =new ArrayList<>();
    ArrayList<String> alternative =new ArrayList<>();
    ArrayList<String> kategori =new ArrayList<>();*/
    //Konstruktor
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



