package QuizServerSide;

import java.io.Serializable;

public class NetworkMessage implements Serializable {

    public NetworkMessage(int networkCode)
    {
        this.networkCode = networkCode;
    }
    int networkCode = 0;

    public int getNetworkCode() { return networkCode; }
}
