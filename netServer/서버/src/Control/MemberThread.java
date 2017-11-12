package Control;

import Model.*;
import sun.rmi.runtime.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-01.
 */
public class MemberThread implements Runnable {

    private MainController controller;
    private Socket socket;
    private ArrayList<Socket> clientList;
    private JsonController jc;
    private DBController dc;
    private LoginController lc;
    private UMLContorller uc;
    private AccountController ac;

    public MemberThread(Socket socket, ArrayList<Socket> clientList) {
        this.socket = socket;
        this.clientList = clientList;
        memberInit();
    }

    private void memberInit() {
        controller = new MainController();
        jc = controller.getJsonController();
        dc = controller.getDBController();
        lc = controller.getLoginController();
        uc = controller.getUmlContorller();
        ac = controller.getAccountController();
    }

    @Override
    public void run() {
        while(checkDataType());
        logout();
    }

    private void logout() {
        try {
            System.out.format("%s : %s 접속 종료 \n", socket.getInetAddress(), lc.getId());
            LoginInfo.getInstance().rmConnectId(lc.getMyLoginModel().getId());
            dc.closeConnect();
            clientList.remove(socket);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDataType(){
        boolean rt = true;
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String data = dis.readUTF();
            System.out.format("%s\n",data);
            String type = jc.getJsonType(jc.string2JSONObject(data));
            int ack =0;
            switch (type) {
                case "login":
                    ack = lc.login(data);
                    sendAck(ack);
                    if(ack == Ack.lAck){
                        sendStr(lc.getLoginModel());
                        sendStr(lc.getSearchModel());
                        sendStr(ac.getFriends());
                    }
                    break;
                case "logout":
                    sendAck(Ack.logoutAck);
                    logout();
                    break;
                case "signup":
                    sendAck(lc.signup(data));
                    break;
                case "suId":
                    sendAck(lc.suOverlap(data));
                    break;
                case "pwcId":
                    sendAck(lc.pwcOverlap(data));
                    break;
                case "pw":
                    sendAck(lc.pwChange(data));
                    break;
                case "push":
                    ack = uc.push(data);
                    sendAck(ack);
                    if(ack == Ack.pushACK) {
                        sendStr(lc.getSearchModel());
                    }
                    break;
                case "clone":
                    ack = uc.cllone(data);
                    sendAck(ack);
                    if(ack == Ack.cloneACK){
                        sendStr(uc.getCloneModel());
                    }
                    break;
                case "search":
                    ack = uc.search(data);
                    sendAck(ack);
                    if(ack == Ack.searchAck){
                        sendStr(uc.getSearchModels());
                    }
                    break;
                case "addFriend":
                    sendAck(ac.addFriend(data));
                    break;
                case "searchId":
                    sendAck(ac.searchId(data));
                    break;
            }
        } catch (IOException e) {
            rt = false;
        }
        return rt;
    }

    private void sendAck(int ack) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(ack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendStr(String str) {
        System.out.format("%s",str);
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
