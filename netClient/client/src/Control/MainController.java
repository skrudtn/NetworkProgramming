package Control;

import Model.ClassDiagramModel.CDModel;
import Model.LoginModel;
import Model.SearchDataModel;

import java.util.ArrayList;

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
    private CDModel cdModel;
    private ArrayList<SearchDataModel> sdms=null;
    private ArrayList<SearchDataModel> reposiData=null;
    private ArrayList<String> friends;

    public MainController() {
        initController();
        sdms = new ArrayList<>();
        reposiData = new ArrayList<>();

    }
    public void clientStart(){
//        GUIController.newLoginView(this);
        GUIController.drawViewTest();
//        GUIController.displayViewTest();
    }

    private void initController(){
        jsonController = new JsonController();
        GUIController = new GUIController(this);
        networkController = new NetworkController(this);
        loginController = new LoginController(this);
        myAccount = new LoginModel();
        cdModel = new CDModel();
        friends = new ArrayList<>();
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

    public CDModel getCdModel() {
        return cdModel;
    }

    public void setCdModel(CDModel cdModel) {
        this.cdModel = cdModel;
    }

    public ArrayList<SearchDataModel> getSdms() {
        return sdms;
    }

    public void setSdms(ArrayList<SearchDataModel> sdms) {
        this.sdms = sdms;
    }

    public ArrayList<SearchDataModel> getReposiData() {
        return reposiData;
    }

    public void setReposiData(ArrayList<SearchDataModel> reposiData) {
        this.reposiData = reposiData;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
}
