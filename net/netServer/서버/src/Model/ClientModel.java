package Model;

import java.net.Socket;

/**
 * Created by skrud on 2017-11-21.
 */
public class ClientModel {
    private Socket socket;
    private String id;
    public ClientModel(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
