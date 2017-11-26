package Control;

import Model.Ack;
import Model.LoginInfo;
import Model.LoginModel;
import Model.RepoModel.SearchPacket;

/**
 * Created by skrud on 2017-11-08.
 */
public class LoginController {
    private MainController controller;
    private JsonController jc;
    private DBController dc;
    private LoginModel myLoginModel;
    private String id;
    public LoginController(MainController controller){
        this.controller = controller;
        jc =controller.getJsonController();
        dc = controller.getDBController();
    }


    public int signup(String data) {
        int ack = Ack.signUpREJ;
        LoginModel dbdata = jc.str2lm(data);
        if (dc.signup(dbdata)) {
            ack = Ack.signUpACK;
        }
        return ack;
    }

    public int suOverlap(String id) {
        int ack = Ack.oACK;
        LoginModel dbdata = jc.str2lm(id);
        // DB에서 아이디 중복확인
        if (dc.overLapDB(dbdata.getId())) {
            ack = Ack.oREJ;// 중복
        }
        return ack;
    }

    public int pwcOverlap(String id) {
        int ack = Ack.pwOREJ;
        LoginModel dbdata = jc.str2lm(id);
        // DB에서 아이디 중복확인
        if (dc.overLapDB(dbdata.getId())) {
            ack = Ack.pwOACK; //아이디 존재
        }
        return ack;
    }

    public int pwChange(String pw) {
        int ack = Ack.pwCREJ;;
        LoginModel dbdata = jc.str2lm(pw);
        // DB에서 pw change
        if (dc.pwDB(dbdata)) {
            ack = Ack.pwCACK;// 중복
        }
        return ack;
    }
    public int login(String data) {
        LoginModel loginModel = jc.str2lm(data);
        LoginModel dbLm = dc.loginDB(loginModel);
        String dbpw = dbLm.getPw();
        int ack = 0;
        if (!(dbpw.equals(""))) {
            if (dbpw.equals(loginModel.getPw())) {
                synchronized (MemberThread.class) { // duplicate login synchronized
                    if (!isDupliLogin(loginModel.getId())) {
                        ack = Ack.lAck;
                        LoginInfo.getInstance().addConnectId(loginModel.getId());
                        this.id = dbLm.getId();
                        controller.getAccountController().setId(id);
                        this.myLoginModel = dbLm;
                    } else {
                        ack = Ack.loginDupliAck;
                    }
                }
            } else {
                ack = Ack.pREJ;
            }
        } else {
            ack = Ack.iREJ;
        }
        return ack;
    }

    public String getLoginModelStr() {
        return jc.lm2str(myLoginModel);
    }
    public String getSearchModel(){
        return jc.sms2str(dc.search(new SearchPacket(myLoginModel.getId(),"UserID")));
    }

    public boolean isDupliLogin(String id){
        System.out.println("접속 리스트");
        for(String s: LoginInfo.getInstance().getconnectedIds()){
            System.out.println(s);
            if(s.equals(id)){
                return true; // duplicate login
            }
        }
        return false;
    }

    public LoginModel getMyLoginModel() {
        return myLoginModel;
    }

    public void setMyLoginModel(LoginModel myLoginModel) {
        this.myLoginModel = myLoginModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
