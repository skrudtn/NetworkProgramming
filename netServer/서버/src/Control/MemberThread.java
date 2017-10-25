package Control;

import Model.Ack;
import Model.CDModel;
import Model.ClazzModel;
import Model.LoginModel;

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
    private DataInputStream dataInputStream;
    private JsonController jc;
    private DBController dc;
    private String id;

    public MemberThread(Socket socket, ArrayList<Socket> clientList) {
        this.socket = socket;
        this.clientList = clientList;
        memberInit();
    }

    private void memberInit() {
        controller = new MainController();
        jc = controller.getJsonController();
        dc = controller.getDBController();
    }

    public MainController getController() {
        return controller;
    }

    @Override
    public void run() {
        System.out.format("%s 접속 \n", socket.getInetAddress());
        while(socket.isConnected()) {
            checkDataType();
        }
    }


    private void checkDataType() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String data = dis.readUTF();
            System.out.println(data);
            String type = jc.getJsonType(jc.string2JSONObject(data));
            if (type.equals("login")) {
                login(data);
            } else if (type.equals("signup")) {
                signup(data);
            } else if (type.equals("suId")) {
                suOverlap(data);
            } else if (type.equals("pwcId")) {
                pwcOverlap(data);
            } else if (type.equals("pw")) {
                pwChange(data);
            } else if (type.equals("classDiagram")){
                cdControl(data);
//            } else if (type.equals("cd")){
//                cdName(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void signup(String data) {
        int flag = 0;
        LoginModel dbdata = jc.str2lm(data);
        if (dc.signup(dbdata)) {
            flag = Ack.sACK;
        } else flag = Ack.sREJ;
        sendAck(flag);
    }

    private void suOverlap(String id) {
        LoginModel dbdata = jc.str2lm(id);
        int flag = 0;
        // DB에서 아이디 중복확인
        if (dc.overLapDB(dbdata.getId())) {
            flag = Ack.oREJ;// 중복
        } else {
            flag = Ack.oACK; //중복아님
        }
        sendAck(flag);
    }

    private void pwcOverlap(String id) {
        LoginModel dbdata = jc.str2lm(id);
        int flag = 0;
        // DB에서 아이디 중복확인
        if (dc.overLapDB(dbdata.getId())) {
            flag = Ack.pwOACK; //아이디 존재
        } else {
            flag = Ack.pwOREJ; //존재 안함
        }
        sendAck(flag);
    }

    private void pwChange(String pw) {
        LoginModel dbdata = jc.str2lm(pw);
        int flag = 0;
        // DB에서 pw change
        if (dc.pwDB(dbdata)) {
            flag = Ack.pwCACK;// 중복
        } else {
            flag = Ack.pwCREJ; //중복아님
        }
        sendAck(flag);
    }

    private void login(String data) {
        LoginModel loginModel = jc.str2lm(data);
        LoginModel dbLm = dc.loginDB(loginModel);
        String dbpw = dbLm.getPw();
        int ack = 0;
        if (!(dbpw.equals(""))) {
            if (dbpw.equals(loginModel.getPw())) {
                ack = Ack.lAck;
            } else {
                ack = Ack.pREJ;
            }
        } else {
            ack = Ack.iREJ;
        }
        sendAck(ack);

        if(ack == Ack.lAck){
            sendLoginModel(dbLm);
        }
    }

    private void sendAck(int flag) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendLoginModel(LoginModel dbLm) {
        String str = jc.loginModel2JSONString(dbLm);
        this.id = dbLm.getId();
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cdControl(String data){
        CDModel cdm = jc.str2cdModel(data);
        int ack=0;
//        dc.insertCDData(cdm, id);
        if(dc.insertCDData(cdm, "ksna")){
            ack = Ack.insertACK;
        } else{
            ack = Ack.insertREJ;
        }
        sendAck(ack);
    }
}
