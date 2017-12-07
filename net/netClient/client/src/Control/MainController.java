package Control;

import Model.SearchRepoModel;
import Model.StaticModel.MyImage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private UMLController umlController = null;
    private CryptoController cryptoController =null;
    private EventsController eventsController= null;
    private ArrayList<SearchRepoModel> sdms=null;
    private ArrayList<SearchRepoModel> mySdms=null;
    private ArrayList<String> friends;

    public MainController() {
        initController();
        sdms = new ArrayList<>();
        mySdms = new ArrayList<>();
        MyImage.loadLoginImage();
        new Thread(new ImageLoadThread()).start();
    }

    public void clientStart(){
        GUIController.newLoginView(this);
//        GUIController.drawViewTest();
//        GUIController.displayViewTest();
    }

    private void initController(){
        jsonController = new JsonController();
        umlController = new UMLController();
        try {
            cryptoController = new CryptoController();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        eventsController = new EventsController(this);
        GUIController = new GUIController(this);
        networkController = new NetworkController(this);
        loginController = new LoginController(this);
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

    public ArrayList<SearchRepoModel> getSdms() {
        return sdms;
    }

    public void setSdms(ArrayList<SearchRepoModel> sdms) {
        this.sdms = sdms;
    }

    public ArrayList<SearchRepoModel> getMySdms() {
        return mySdms;
    }

    public void setMySdms(ArrayList<SearchRepoModel> mySdms) {
        this.mySdms = mySdms;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public UMLController getUmlController() {
        return umlController;
    }

    public void setUmlController(UMLController umlController) {
        this.umlController = umlController;
    }

    public EventsController getEventsController() {
        return eventsController;
    }

    public void setEventsController(EventsController eventsController) {
        this.eventsController = eventsController;
    }

    public CryptoController getCryptoController() {
        return cryptoController;
    }
}
