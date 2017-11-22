package Control;

import Model.LoginModel;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * Created by skrud on 2017-10-02.
 */
public class LoginController {
    private JsonController jc;
    private NetworkController nc;

    private LoginModel myAccount;
    public LoginController(MainController controller) {
        jc= controller.getJsonController();
        nc = controller.getNetworkController();
        myAccount = new LoginModel();
    }

    public void signup(String id, String pw, String name, String email) {
        LoginModel signUp = new LoginModel(id, pw, name, email);
        String str = jc.signupString2JSONString(signUp);
        nc.sendStr(str);

    }

    public void login(String id, String pw) {
        String loginInfo = jc.login2JSONString(id, pw);
        nc.sendStr(loginInfo);
    }

    public void suOverlap(String id){
        String idInfo = jc.su2JSONString(id);
        nc.sendStr(idInfo);
    }
    public void pwcOverlap(String id){
        String idInfo = jc.pwc2JSONString(id);
        nc.sendStr(idInfo);
    }

    public void pwChange(String id, String pw){
        String pwInfo = jc.pw2JSONString(id,pw);
        nc.sendStr(pwInfo);
    }

    public LoginModel getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(LoginModel myAccount) {
        this.myAccount = myAccount;
    }

}
