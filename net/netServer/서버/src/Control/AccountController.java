package Control;

import Model.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-08.
 */
class AccountController {
    private MainController controller;
    private DBController dc;
    private JsonController jc;
    private ArrayList<String> friends;
    private String searchId;

    AccountController(MainController controller){
        this.controller = controller;
        this.dc = controller.getDBController();
        this.jc = controller.getJsonController();
        friends = new ArrayList<>();
    }

    void addFriends(String str){
        friends.add(str);
    }
    void rmFriends(String str){
        friends.remove(str);
    }

    int searchId(String data) {
        int ack = Ack.searchIdRej;
        String id = jc.str2si(data);
        if(dc.selectId(id)){
            searchId = id;
            ack = Ack.searchIdAck;
        }
        return ack;
    }

    int addFriend(String data) {
        int ack = Ack.addFriendRej;
        if(dc.insertFriend(searchId)){
            ack = Ack.addFriendAck;
        }
        return ack;
    }

    String getFriends() {
        return jc.friends2str(dc.getFriends());
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    void reqFriend() {
        Socket s = null;
        if (LoginInfo.getInstance().isConnectedId(searchId)) {
            for (ClientModel clientModel : controller.getClientModels()) {
                if (clientModel.getId().equals(searchId)) {
                    sendAck(clientModel.getSocket(), Ack.addFriendAck);
                    sendStr(clientModel.getSocket(), jc.getReqFriendStr(new Event(
                            controller.getLoginController().getId(),searchId,"friend")));
                }
            }
        } else {
            SharedData.getInstance().addEvent(new Event(controller.getLoginController().getId(), searchId, "friend"));
        }
    }

    private void sendStr(Socket s, String str){
        try {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendAck(Socket s, int ack){
        try {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(ack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
