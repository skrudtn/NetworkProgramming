package Control;

import java.net.Socket;

/**
 * Created by skrud on 2017-10-02.
 */
public class MainController {
    private DBController DBController = null;
    private JsonController jsonController = null;
    private AccountController accountController = null;
    private LoginController loginController = null;
    private UMLContorller umlContorller = null;

    MainController(){
        jsonController = new JsonController();
        DBController = new DBController();
        umlContorller = new UMLContorller(this);
        loginController = new LoginController(this);
        accountController = new AccountController(this);
    }

    DBController getDBController() {
        return DBController;
    }

    JsonController getJsonController() {
        return jsonController;
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public UMLContorller getUmlContorller() {
        return umlContorller;
    }
}
