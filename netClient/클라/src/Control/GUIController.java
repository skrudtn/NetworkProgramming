package Control;

import Model.LoginModel;
import View.MainView.MainFrame;
import View.MainView.SearchPanel;
import View.login.LoginFrame;

/**
 * Created by skrud on 2017-10-02.
 */
public class GUIController {
    private MainController controller = null;
    private JsonController jc;

    private LoginFrame loginFrame = null;
    private MainFrame mainFrame = null;
    private SearchPanel searchPanel =null;

    private int comboMode;
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public int getComboMode() {
        return comboMode;
    }

    public void setComboMode(int comboMode) {
        this.comboMode = comboMode;
    }

    public GUIController(MainController controller) {
        this.controller = controller;
        jc = controller.getJsonController();
        comboMode = 0;
    }

    public void newLoginView() {
        loginFrame = new LoginFrame(controller);
    }
    public void loginView(){
        loginFrame.getCardLayout().show(loginFrame.getContentPane(), "login");
    }
    public void signUpView(){
        loginFrame.getCardLayout().show(loginFrame.getContentPane(), "signup");
    }
    public void pwChangeView(){
        loginFrame.getCardLayout().show(loginFrame.getContentPane(), "pwchange");
    }
    public void newDisplayView(){
        loginFrame.dispose();
        mainFrame = new MainFrame(controller);
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"display");
    }
    public void displayView(){
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"display");
    }
    public void drawView(){
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
    }

    public void test() {
        LoginModel l = new LoginModel();
        l.setId("ksna");
        controller.setMyAccount(l);
        mainFrame = new MainFrame(controller);
    }

    public void drawViewTest(){
        mainFrame = new MainFrame(controller);
    }
    public void searchTest(){
        mainFrame = new MainFrame(controller);
        SearchPanel sf = new SearchPanel(mainFrame);
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
//        mainFrame.getCardLayout().show();
    }

    public void clone(String id, String dir){
        System.out.println("gc.clone");
    }
    public void search(){
//        mainFrame.getCardLayout().show(mainFrame.getContentPane(), "search");
        searchPanel = new SearchPanel(mainFrame);
    }

    public void displayViewTest() {
        mainFrame = new MainFrame(controller);
    }

    public void displayUpdate() {
        System.out.println("DisPlay Update");
        mainFrame.getDisplayPanel().getNoticePanel().addResultPanel();
        mainFrame.getDisplayPanel().repaint();
    }
}
