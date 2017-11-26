package Control;

import Model.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-01.
 */
public class MemberThread implements Runnable {

    private MainController controller;
    private ClientModel client;
    private Socket socket;
    private ArrayList<ClientModel> clientList;

    public MemberThread(ClientModel clientModel, ArrayList<ClientModel> clientList) {
        this.client = clientModel;
        this.socket = clientModel.getSocket();
        this.clientList = clientList;
        memberInit();
    }

    private void memberInit() {
        controller = new MainController();
        controller.setClientModel(clientList);
    }

    @Override
    public void run() {
        new Thread( new ReceivedThread(controller,socket, this)).start();
        System.out.format("client List\n");
        int index = 0;
        for (ClientModel c : controller.getClientModels()) {
            System.out.format("%d : %s ", index, c.getSocket().getLocalAddress());
            System.out.println(c.getId());
            index++;
        }

    }

    void sendAck(int ack) {
        try {
            System.out.println(ack);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(ack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendStr(String str) {
        System.out.format("%s",str);
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientModel getClient() {
        return client;
    }

    public ArrayList<ClientModel> getClientList() {
        return clientList;
    }
}
