package Control;

import Model.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private String id;

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

    String getFriends(String id) {
        return jc.friends2str(dc.getFriends(id));
    }

    public String getMyFriends() {
        return jc.friends2str(dc.getFriends());
    }
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    private Socket getMemberSocket(String id) {
        Socket s = null;

        for (ClientModel clientModel : controller.getClientModels()) {
            if (clientModel.getId().equals(id)) {
                s = clientModel.getSocket();
            }
        }
        return s;
    }

    void reqFriend() {
        Socket s = null;
        if (LoginInfo.getInstance().isConnectedId(searchId)) {
            s = getMemberSocket(searchId);
            sendStr(s,jc.getAckStr(Ack.addFriendAck));
            sendStr(s, jc.getReqFriendStr(new Event(
            id,searchId,"friend")));
        } else {
            SharedData.getInstance().addEvent(new Event(id, searchId, "friend"));
        }
    }

    public int resFriend(String data) {
        int ack = Ack.acceptFriend;
        ResFriendPacket rfp = jc.str2resFriend(data);
        if(rfp.isOk()){
            dc.insertFriend(rfp.getDes());
        } else{
            ack = Ack.rejectFriend;
        }
        Socket s = null;
        if(LoginInfo.getInstance().isConnectedId(rfp.getDes())){
            s = getMemberSocket(rfp.getDes());
            sendStr(s,jc.getAckStr(ack));
            if(ack == Ack.acceptFriend){
                sendStr(s,getFriends(rfp.getDes()));
            }
        }

        return ack;
    }

    public int memberManage(String data) {
        int ack = Ack.updateAuthoRej;
        System.out.println(data);
        MemManagePacket mmp = jc.str2memManage(data);
        if(dc.updateAutho(mmp)){
            ack= Ack.updateAuthoAck;
        }

        return ack;
    }

    public int removeFriend(String data) {
        int ack = Ack.rmFriendRej;
        ArrayList<String> friends = jc.str2friends(data);
        if(dc.deleteFriends(friends)){
            ack = Ack.rmFriendAck;
            for(String str: friends) {
                if (LoginInfo.getInstance().isConnectedId(str)) {
                    Socket s = getMemberSocket(str);
                    sendStr(s, jc.getAckStr(ack));
                    sendStr(s, getMyFriends());
                }
            }
        }
        return ack;
    }

    private void sendStr(Socket s, String str){
        try {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            str = controller.getCryptoController().getAesEncodedText(str);
            dos.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public void setId(String id) {
        this.id = id;
    }

}
