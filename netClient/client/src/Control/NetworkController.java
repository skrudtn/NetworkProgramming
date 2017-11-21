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
    private GUIController gc = null;
    private JsonController jc = null;

    private Socket socket;

    public NetworkController(MainController controller) {
        this.controller = controller;
        jc = controller.getJsonController();
        gc = controller.getGUIController();
        socket = null;
        connect();
    }

    private void connect() {
        try {
            socket = new Socket(IP, PORT);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendStr(String str) {
        try {
            System.out.println(str);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(str);
            recvAck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void recvAck() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int ack = dataInputStream.readInt();
            System.out.println("ack " + ack);
            switch (ack) {
                case Ack.error:
                    break; //에러
                case Ack.lAck:
                    gc.newDisplayView();
                    recvLoginModel();
                    recvSearchData(true);
                    recvFriends();
                    break; //로그인 성공
                case Ack.pREJ:
                    gc.loginStateUpdate("Invalid PW", false);
                    break;// 비밀번호 틀림
                case Ack.iREJ:
                    gc.loginStateUpdate("Invalid ID", false);
                    break;
                case Ack.oACK:
                    gc.signupStateUpdate("Available ID", true);
                    gc.setOverLapFlag(0);
                    break;
                case Ack.oREJ:
                    gc.signupStateUpdate("Existing id", false);
                    break;
                case Ack.signUpACK:
                    gc.loginView();
                    break;
                case Ack.signUpREJ:
                    gc.signupStateUpdate("Failed Sign Up", false);
                    break;
                case Ack.pwCACK:
                    gc.loginView();
                    break;
                case Ack.pwCREJ:
                    gc.pwChangeStateUpdate("Password Change Failed", 0);
                    break;
                case Ack.pushACK:
                    recvSearchData(true);
                    gc.showMainMessage("Push Completed", -1);
                    gc.push();
                    break;
                case Ack.pushREJ:
                    gc.showMainMessage("Push Failed", 0);
                    break;
                case Ack.cloneACK:
                    recvCloneData();
                    gc.cllone();
                    break;
                case Ack.cloneREJ:
                    gc.showMainMessage("Clone Failed", 0);
                    break;
                case Ack.searchAck:
                    recvSearchData(false);
                    break;
                case Ack.searchRej:
                    controller.setSdms(null);
                    gc.searchUpdate();
                    break;
                case Ack.loginDupliAck:
                    gc.loginStateUpdate("Duplicate login", false);
                    break;
                case Ack.logoutAck:
                    gc.signOut();
                    break;
                case Ack.searchIdAck:

                    break;
                case Ack.searchIdRej:

                    break;
                case Ack.addFriendAck:

                    break;
                case Ack.addFriendRej:

                    break;
                case Ack.overlapRepoACK: // repo 생성가능
                    recvRepoData();
                    recvSearchData(true);
                    break;
                case Ack.overlapRepoREJ: // repo 중복
                    gc.showRepoMessage(0);
                    break;
                case Ack.repoACK: // version 선택
                    recvRepoData();
                    break;
                case Ack.repoREJ: // version 선택불가능
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvRepoData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            RepoModel rm = jc.str2repo(str);
            controller.getUmlController().setRepoModel(rm);
            gc.versionView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvFriends() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            ArrayList<String> friends = jc.str2friends(str);
            controller.setFriends(friends);
            gc.searchUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void recvSearchData(boolean isMysmds) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            ArrayList<SearchRepoModel> smds = jc.str2smds(str);
            if (isMysmds) {
                controller.setMySdms(smds);
                gc.myRepoUpdate();
            } else {
                controller.setSdms(smds);
                gc.searchUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void recvLoginModel() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            controller.getLoginController().setMyAccount(jc.str2lm(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvCloneData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            CDModel cdModel = jc.str2cdm(str);
            controller.getUmlController().setCdModel(cdModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
