package Control;

import Model.Ack;
import Model.ClientModel;
import Model.LoginInfo;
import Model.RepoModel.RepoModel;
import Model.SharedData;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by skrud on 2017-11-22.
 */
public class ReceivedThread implements Runnable {
    private MainController controller;
    private JsonController jc;
    private LoginController lc;
    private DBController dc;
    private AccountController ac;
    private UMLController uc;
    private MemberThread memberThread;
    private Socket socket;
    private ClientModel client;

    public ReceivedThread(MainController controller, Socket socket, MemberThread memberThread) {
        this.controller = controller;
        this.lc = controller.getLoginController();
        this.jc = controller.getJsonController();
        this.dc = controller.getDBController();
        this.uc = controller.getUmlController();
        this.ac = controller.getAccountController();

        this.memberThread = memberThread;
        this.socket = socket;
        this.client = memberThread.getClient();
    }


    @Override
    public void run() {

        while (checkDataType()) ;
        quit();
    }

    private void quit() {
        try {
            System.out.format("%s : %s 접속 종료 \n", socket.getInetAddress(), lc.getId());
            initMember();
            dc.closeConnect();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initMember(){
        if(lc.getId() !=null) {
            LoginInfo.getInstance().rmConnectId(lc.getId());
        }
        uc = new UMLController(controller);
        ac = new AccountController(controller);
        lc = new LoginController(controller);
        controller.getClientModels().remove(memberThread.getClient());
        memberThread.getClientList().remove(client);

    }
    private void logout() {
        memberThread.sendAck(Ack.logoutAck);
        System.out.format("%s : %s 로그아웃\n", socket.getInetAddress(), lc.getId());

    }

    private boolean checkDataType() {
        boolean rt = true;
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String data = dis.readUTF();
            System.out.format("%s\n", data);
            String type = jc.getJsonType(jc.str2JSONObject(data));
            int ack = 0;
            System.out.println(type);
            switch (type) {
                case "login":
                    ack = lc.login(data);
                    memberThread.sendAck(ack);
                    if (ack == Ack.lAck) {
                        memberThread.sendStr(lc.getLoginModelStr());
                        memberThread.sendStr(lc.getSearchModel());
                        memberThread.sendStr(ac.getMyFriends());
                        memberThread.sendStr(jc.getEventsStr(SharedData.getInstance().getEvents(lc.getId())));
                        client.setId(lc.getId());
                    }
                    break;
                case "logout":
                    logout();
                    rt=false;
                    break;
                case "signup":
                    memberThread.sendAck(lc.signup(data));
                    break;
                case "suId":
                    memberThread.sendAck(lc.suOverlap(data));
                    break;
                case "pwcId":
                    memberThread.sendAck(lc.pwcOverlap(data));
                    break;
                case "pw":
                    memberThread.sendAck(lc.pwChange(data));
                    break;
                case "push":
                    ack = uc.push(data);
                    memberThread.sendAck(ack);
                    if (ack == Ack.pushACK) {
                        memberThread.sendStr(lc.getSearchModel());
                    }
                    break;
                case "clone":
                    ack = uc.cllone(data);
                    memberThread.sendAck(ack);
                    if (ack == Ack.cloneACK) {
                        memberThread.sendStr(uc.getCloneModel());
                    }
                    break;
                case "search":
                    ack = uc.search(data);
                    memberThread.sendAck(ack);
                    if (ack == Ack.searchAck) {
                        memberThread.sendStr(uc.getSearchModels());
                    }
                    break;
                case "searchId":
                    ack = ac.searchId(data);
                    memberThread.sendAck(ack);
                    if(ack == Ack.searchIdAck) {
                        ac.reqFriend();
                    }
                    break;
                case "repoPacket":
                    ack = uc.isExistRepo(data);
                    memberThread.sendAck(ack);
                    if (ack == Ack.overlapRepoACK) {
                        RepoModel repoModel = uc.createRepo(data);
                        memberThread.sendStr(jc.rm2str(repoModel, lc.getId()));
                        memberThread.sendStr(lc.getSearchModel());
                    }
                    break;
                case "repoModel":
                    ack = uc.isExistRepoData(data);
                    memberThread.sendAck(ack);
                    if (ack == Ack.repoACK) {
                        memberThread.sendStr(uc.getRepoStr(data));
                    }
                    break;
                case "friendRes":
                    ack = ac.resFriend(data);
                    if(Ack.acceptFriend == ack) {
                        memberThread.sendAck(ack);
                        memberThread.sendStr(ac.getMyFriends());
                    }
                    break;
                case "memberManage":
                    ack = ac.memberManage(data);
                    memberThread.sendAck(ack);
                    if(Ack.updateAuthoAck == ack){

                    }
                default: break;
            }
        } catch (IOException e) {
            rt = false;
        }
        return rt;
    }

}
