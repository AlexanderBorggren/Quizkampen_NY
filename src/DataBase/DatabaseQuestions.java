package DataBase;

import java.io.*;
import java.util.ArrayList;

public class DatabaseQuestions {
    public static void main(String[] args) {

        readFromFile("src/DataBase/Questions");


        readFromFile("src/DataBase/Answer");
    }

    private static void readFromFile(String fileName) {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(fileName + ":"+"\n");
        for (String line : data) {
            System.out.println(line);
        }
        System.out.println();
    }
}
