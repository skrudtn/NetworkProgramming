package Control;

import Model.LoginModel;

/**
 * Created by skrud on 2017-10-02.
 */
/*
 * 컨트롤러들을 관리하는 객체
 */

public class MainController {

    private GUIController GUIController = null;
    private NetworkController networkController = null;
    private JsonController jsonController = null;
    private LoginController loginController = null;
    private LoginModel myAccount;

    public MainController() {
        initController();
    }

    private void initController() {
        GUIController = new GUIController(this);
        jsonController = new JsonController(this);
        networkController = new NetworkController(this);
        loginController = new LoginController(this);
        myAccount = null;
    }

    public GUIController getGUIController() {
        return GUIController;
    }

    public void setGUIController(GUIController GUIController) {
        this.GUIController = GUIController;
    }

    public NetworkController getNetworkController() {
        return networkController;
    }

    public void setNetworkController(NetworkController networkController) {
        this.networkController = networkController;
    }

    public JsonController getJsonController() {
        return jsonController;
    }

    public void setJsonController(JsonController jsonController) {
        this.jsonController = jsonController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public LoginModel getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(LoginModel myAccount) {
        this.myAccount = myAccount;
    }
}
