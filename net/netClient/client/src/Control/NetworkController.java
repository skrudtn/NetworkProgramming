package Control;

import Model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Model.ClassDiagramModel.CDModel;
import Model.StaticModel.Ack;

/**
 * Created by skrud on 2017-09-27.
 */
public class NetworkController {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 10001;
    private MainController controller = null;

    private Socket socket;

    public NetworkController(MainController controller) {
        this.controller = controller;
        socket = null;
        connect();
    }

    private void connect() {
        try {
            socket = new Socket(IP, PORT);
            new Thread( new ReceivedThread(controller,socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendStr(String str) {
        try {
            System.out.println(str);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
