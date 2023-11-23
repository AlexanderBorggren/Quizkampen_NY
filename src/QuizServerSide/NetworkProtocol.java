package QuizServerSide;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

//ISSUES WITH THIS:
//Need to code proper buffer storing and buffer reading to work as originally intended (Think will still be useful)

//This class handles sending and parsing packets(NetworkMessage class)
//Reads the first byte which controls what(structure) to send/parse based on the function connected to that specific number of first byte
public class NetworkProtocol {


    public void parsePacketServer(NetworkMessage networkMessage){
        byte firstByteReceived = networkMessage.getFirstByte();

        switch(firstByteReceived) {
            //Hexadecimal
            case 0x00:
                //parsePlayer();
                break;
            case 0x01:
                //parseAnswerQuestion();
                break;
            case 0x02:
                //parseChooseCategory();
                break;
            case 0x03:
                break;
            case 0x04:
                break;
            case 0x05:
                break;
            case 0x06:
                break;
            case 0x07:
                break;
            case 0x08:
                break;
            case 0x09:
                break;
            case 0x0A:
                break;
            case 0x0B:
                break;
            case 0x0C:
                break;
            case 0x0D:
                break;
            case 0x0E:
                break;
            case 0x0F:
                break;
            case 0x10:
                break;
        }
    }

    public void sendPacketServer(ObjectOutputStream objectOutputStream, NetworkMessage networkMessage) throws IOException {
        objectOutputStream.writeObject(networkMessage);
    }

    public void parsePacketClient(NetworkMessage networkMessage){
        byte firstByteReceived = networkMessage.getFirstByte();

        switch(firstByteReceived) {
            //Hexadecimal
            case 0x00:
                //parsePlayer();
                break;
            case 0x01:
                parseAnswerQuestion();
                break;
            case 0x02:
                //parseChooseCategory();
                break;
            case 0x03:
                break;
            case 0x04:
                break;
            case 0x05:
                break;
            case 0x06:
                break;
            case 0x07:
                break;
            case 0x08:
                break;
            case 0x09:
                break;
            case 0x0A:
                break;
            case 0x0B:
                break;
            case 0x0C:
                break;
            case 0x0D:
                break;
            case 0x0E:
                break;
            case 0x0F:
                break;
            case 0x10:
                break;
        }
    }

    public void sendPacketClient(ObjectOutputStream objectOutputStream, NetworkMessage networkMessage) throws IOException {
        objectOutputStream.writeObject(networkMessage);
    }

    public void parseAnswerQuestion(){
        System.out.println("Answer question");
    }


}
