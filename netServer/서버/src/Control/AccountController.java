package Control;

import Model.Ack;

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

    public int searchId(String data) {
        int ack = Ack.searchIdRej;
        String id = jc.si2str(data);
        if(dc.searchId(id)){
            searchId = id;
            ack = Ack.searchIdAck;
        }
        return ack;
    }

    public int addFriend(String data) {
        int ack = Ack.addFriendRej;
        if(dc.insertFriend(searchId)){
            ack = Ack.addFriendAck;
        }
        return ack;
    }

}
