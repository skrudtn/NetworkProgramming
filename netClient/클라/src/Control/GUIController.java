package Control;

import Model.LoginModel;
import View.MainView.DrawFrame;
import View.login.LoginFrame;

/**
 * Created by skrud on 2017-10-02.
 */
public class GUIController {
    private MainController controller = null;
    private LoginFrame loginFrame = null;

    public DrawFrame getDrawFrame() {
        return drawFrame;
    }

    private DrawFrame drawFrame = null;

    private int comboMode;
    public int getComboMode() {
        return comboMode;
    }

    public void setComboMode(int comboMode) {
        this.comboMode = comboMode;
    }

    public GUIController(MainController controller) {
        this.controller = controller;
        comboMode = 0;
    }

    public void newLoginView() {
        loginFrame = new LoginFrame(controller);
    }
    public void login(){
        loginFrame.getCardLayout().show(loginFrame.getContentPane(), "login");
    }

    public void mainView(LoginModel loginModel) {
        loginFrame.dispose();
        controller.setMyAccount(loginModel);
        drawFrame = new DrawFrame(controller);
    }
    public void test() {
        controller.setMyAccount(new LoginModel());
        drawFrame = new DrawFrame(controller);
    }

    public void setOverLapFlag(int i){
        if(i==0) {
            loginFrame.getSup().setIdFlag(true);
        } else if(i==1){
            loginFrame.getPwcp().setIdFlag(true);
        }
    }

    public void push(){
        System.out.println("gc.push");
//        drawFrame.getCardLayout().show();
    }

}
