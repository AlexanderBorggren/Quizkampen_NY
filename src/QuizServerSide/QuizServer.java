package QuizServerSide;

import java.io.*;
import java.net.Socket;

public class QuizServer extends Thread {

    int port = 52703;

    Socket s;
    QuizServerProtocol qsp;

    public QuizServer(Socket s, QuizServerProtocol qsp){

        this.s = s;
        this.qsp = qsp;
    }




    @Override
    public void run() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
             ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
             PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        ){
            System.out.println("Connected");
            String inData = br.readLine();
            //pw.println(qsp.sendQuestion());
            oos.writeObject(qsp.sendQuestion());
            inData = br.readLine();
            oos.writeObject(qsp.sendAnswers());

            while ((inData = br.readLine()) != null) {
                System.out.println(inData);
                //Spel logik.........
                oos.writeObject(qsp.processInput(inData));

            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
