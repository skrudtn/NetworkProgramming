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

    public LoginController(MainController controller) {
        jc= controller.getJsonController();
        nc = controller.getNetworkController();
    }

    public void signup(String id, String pw, String name, String email) {
        LoginModel signUp = new LoginModel(id, pw, name, email);
        String signUpInfo = jc.signupString2JSONString(signUp);
        System.out.println(signUpInfo);
        nc.sendStr(signUpInfo);

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

}
