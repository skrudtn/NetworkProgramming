package Control;

import Model.Ack;
import Model.ClazzModel;
import Model.LoginModel;

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

    private GUIController gc = null;
    private JsonController jc = null;

    private Socket socket;

    public NetworkController(MainController controller) {
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

    public void sendLoginInfo(String loginInfo) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(loginInfo);
            recvAck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void push(ArrayList<ClazzModel> cmList, String cdName) {
        String jsonString = jc.cd2str(cmList, cdName);
        System.out.println(jsonString);
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("쏜다");
            dos.writeUTF(jsonString);
            recvAck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvAck() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int ack = dataInputStream.readInt();
            System.out.println(ack);
            switch (ack) {
                case Ack.error:
                    break; //에러
                case Ack.lAck:
                    gc.mainView(recvLoginModel());
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
                case Ack.sACK:
                    System.out.println("가입 완료");
                    gc.login();
                    break;
                case Ack.sREJ:
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
                    gc.login();
                    break;
                case Ack.pwCREJ:
                    System.out.println("변경실패");
                    break;
                case Ack.insertACK:
                    System.out.println("push 성공");
                    break;
                case Ack.insertREJ:
                    System.out.println("push 실패");
                    break;

                default: break;

            }

    } catch(IOException e)

    {
        e.printStackTrace();
    }
}

    private LoginModel recvLoginModel() {
        LoginModel myAccout=null;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            myAccout = jc.loginString2loginModel(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myAccout;
    }
}
