package Control;

import Model.ClientModel;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-02.
 */
public class MainController {
    private DBController DBController = null;
    private JsonController jsonController = null;
    private AccountController accountController = null;
    private LoginController loginController = null;
    private UMLController umlController = null;
    private ArrayList<ClientModel> clientModels = null;

    MainController(){
        jsonController = new JsonController();
        DBController = new DBController();
        umlController = new UMLController(this);
        loginController = new LoginController(this);
        accountController = new AccountController(this);
    }


    public AccountController getAccountController() {
        return accountController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public UMLController getUmlController() {
        return umlController;
    }

    public void setClientModel(ArrayList<ClientModel> clientModel) {
        this.clientModels = clientModel;
    }

    public ArrayList<ClientModel> getClientModels() {
        return clientModels;
    }

    public Control.DBController getDBController() {
        return DBController;
    }

    public void setDBController(Control.DBController DBController) {
        this.DBController = DBController;
    }

    public JsonController getJsonController() {
        return jsonController;
    }

    public void setJsonController(JsonController jsonController) {
        this.jsonController = jsonController;
    }

    public void setAccountController(AccountController accountController) {
        this.accountController = accountController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setUmlController(UMLController umlController) {
        this.umlController = umlController;
    }

    public void setClientModels(ArrayList<ClientModel> clientModels) {
        this.clientModels = clientModels;
    }
}
