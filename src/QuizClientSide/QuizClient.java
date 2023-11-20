package QuizClientSide;

import java.io.*;
import java.net.Socket;

public class QuizClient {

    String ip="127.0.0.1";
    //String ip = "192.168.1.97";
    int port = 52703;
    Socket socket;
    PrintWriter pw;
    BufferedReader br;
    ObjectInputStream ois;

    public QuizClient() {

        try {
            this.socket = new Socket(ip, port);
            this.pw = new PrintWriter(socket.getOutputStream(), true);
            //this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String sendAndGetMessage(String data) {
        pw.println(data);
        return readStringMessage();
    }

    public String readStringMessage() {
        String temp;
        try {
            //while ((temp = br.readLine()) == null) {
            while ((temp = (String)ois.readObject()) == null) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public String[] sendAndGetArrayMessage(String data) {
        pw.println(data);
        return readArrayMessage();
    }

    public String[] readArrayMessage() {
        String[] temp;
        try {
            //while ((temp = br.readLine()) == null) {
            while ((temp = (String[])ois.readObject()) == null) {
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }


    public static void main(String[] args) {
        new QuizClient();
    }
}
