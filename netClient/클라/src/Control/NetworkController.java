package Control;

import Model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Model.Ack;

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

    public void connect() {
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
            System.out.println("ack" +ack);
            switch (ack) {
                case Ack.error:
                    break; //에러
                case Ack.lAck:
                    gc.newDisplayView();
                    recvLoginModel();
                    recvReposiData();
                    break; //로그인 성공
                case Ack.pREJ:
                    gc.loginStateUpdate("Invalid PW");
                    break;// 비밀번호 틀림
                case Ack.iREJ:
                    gc.loginStateUpdate("Invalid ID");
                    break;
                case Ack.oACK:
                    gc.signupStateUpdate("Available ID");
                    gc.setOverLapFlag(0);
                    break;
                case Ack.oREJ:
                    gc.signupStateUpdate("Existing id");
                    break;
                case Ack.signUpACK:
                    gc.loginView();
                    break;
                case Ack.signUpREJ:
                    gc.signupStateUpdate("Failed Sign Up");
                    break;
                case Ack.pwOACK:
                    System.out.println("비번 변경가능");
                    gc.pwChangeStateUpdate("Existing id");
                    gc.setOverLapFlag(1);
                    break;
                case Ack.pwOREJ:
                    gc.pwChangeStateUpdate("Nonexistent ID");
                    break;
                case Ack.pwCACK:
                    gc.loginView();
                    break;
                case Ack.pwCREJ:
                    System.out.println("변경실패");
                    break;
                case Ack.pushACK:
                    gc.push();
                    System.out.println("push 성공");
                    break;
                case Ack.pushREJ:
                    System.out.println("push 실패");
                    break;
                case Ack.cloneACK:
                    recvCloneData();
                    gc.cllone();
                    break;
                case Ack.cloneREJ:
                    System.out.println("clone 실패");
                    break;
                case Ack.searchAck:
                    recvSearchData();
                    break;
                case Ack.searchRej:
                    controller.setSdms(null);
                    gc.searchUpdate();
                    break;
                case Ack.loginDupliAck :
                    gc.loginStateUpdate("Duplicate login");
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
                default:
                    break;
            }

        } catch (IOException e)

        {
            e.printStackTrace();
        }
    }

    private void recvSearchData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            ArrayList<SearchDataModel> smds = jc.str2smds(str);
            controller.setSdms(smds);
            gc.searchUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void recvReposiData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            ArrayList<SearchDataModel> smds = jc.str2smds(str);
            controller.setReposiData(smds);
            gc.repositoryUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvLoginModel() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            controller.setMyAccount(jc.str2lm(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvCloneData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            controller.setCdModel(jc.str2cdm(str));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
