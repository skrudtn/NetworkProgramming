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
    final static String IP = "127.0.0.1";
    final static int PORT = 10001;
    private MainController controller=null;
    private GUIController gc = null;
    private JsonController jc = null;

    private Socket socket;

    public NetworkController(MainController controller) {
        this.controller = controller;
        jc = controller.getJsonController();
        gc = controller.getGUIController();
        socket = null;
        connect();

//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                try {
//                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                    dos.writeUTF(jc.getShotDown());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("shutdown");
//            }
//        });
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
//            System.out.println(ack);
            switch (ack) {
                case Ack.error:
                    break; //에러
                case Ack.lAck:
                    recvLoginModel();
                    gc.newDisplayView();
                    break; //로그인 성공
                case Ack.pREJ:
                    System.out.println("비밀번호 틀림");
                    break;// 비밀번호 틀림
                case Ack.iREJ:
                    System.out.println("아이디 틀림");
                    break;
                case Ack.oACK:
                    System.out.println("아이디 생성 가능");
                    gc.setOverLapFlag(0);
                    break;
                case Ack.oREJ:
                    System.out.println("아이디 중복");
                    break;
                case Ack.signUpACK:
                    System.out.println("가입 완료");
                    gc.loginView();
                    break;
                case Ack.signUpREJ:
                    System.out.println("가입 실패");
                    break;
                case Ack.pwOACK:
                    System.out.println("비번 변경가능");
                    gc.setOverLapFlag(1);
                    break;
                case Ack.pwOREJ:
                    System.out.println("존재안함");
                    break;
                case Ack.pwCACK:
                    System.out.println("변경완료");
                    gc.loginView();
                    break;
                case Ack.pwCREJ:
                    System.out.println("변경실패");
                    break;
                case Ack.pushACK:
                    System.out.println("push 성공");
                    break;
                case Ack.pushREJ:
                    System.out.println("push 실패");
                    break;
                case Ack.cloneACK:
                    System.out.println("push 성공");
                    break;
                case Ack.cloneREJ:
                    System.out.println("push 실패");
                    break;
                case Ack.searchAck:
                    System.out.println("Search 성공");
                    recvSearchData();
                    break;
                case Ack.searchRej:
                    System.out.println("Search실패");
                    break;

                default: break;

            }

    } catch(IOException e)

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
            gc.displayUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvLoginModel() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            controller.setMyAccount(jc.str2lm(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
