package Control;

import Model.LoginModel;
import View.MainView.*;
import View.login.LoginFrame;

/**
 * Created by skrud on 2017-10-02.
 */
public class GUIController {
    private MainController controller = null;
    private LoginFrame loginFrame = null;
    private ProfileFrame profileFrame = null;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    private MainFrame mainFrame = null;
    private SearchPanel searchPanel =null;
    private ProjectNameFrame projectNameFrame = null;
    private int comboMode;

    public GUIController(MainController controller) {
        this.controller = controller;
        comboMode = 0;
    }

    public void newLoginView(MainController controller) {
        this.controller = controller;
        loginFrame = new LoginFrame(controller);

    }
    public void loginView() {
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
        mainFrame.setResizable(false);
    }
    public void newProfileView(){
        profileFrame = new ProfileFrame(controller);
    }

    public void displayView(){
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"display");
        mainFrame.setTitle("");
        mainFrame.getDrawContentPanel().getDrawPanel().init();
    }
    public void drawView(){
        mainFrame.setTitle(controller.getCdModel().getCdName());
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
    }

    public void projectNameView(){
        projectNameFrame = new ProjectNameFrame(controller);
    }

    public void setOverLapFlag(int i){
        if(i==0) {
            loginFrame.getSup().setIdFlag(true);
        } else if(i==1){
            loginFrame.getPwcp().setIdFlag(true);
        }
    }


    public void push(){
        mainFrame.setTitle(controller.getCdModel().getCdName());
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
    }

    public void cllone(){
        mainFrame.setTitle(controller.getCdModel().getCdName());
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
        mainFrame.getDrawContentPanel().getDrawPanel().cllone();
    }
    public void search(){
//        mainFrame.getCardLayout().show(mainFrame.getContentPane(), "search");
        searchPanel = new SearchPanel(mainFrame);
    }
    public void searchUpdate() {
        mainFrame.getDisplayPanel().getNoticePanel().initResultPanel();
        mainFrame.getDisplayPanel().getNoticePanel().addResultPanel(controller.getSdms());
        mainFrame.getDisplayPanel().getNoticeScrollPanel().setViewportView(mainFrame.getDisplayPanel().getNoticePanel());
        mainFrame.getDisplayPanel().repaint();
    }
    public void repositoryUpdate() {
        mainFrame.getDisplayPanel().getRepoPanel().initResultPanel();
        mainFrame.getDisplayPanel().getRepoPanel().addResultPanel(controller.getReposiData());
        mainFrame.getDisplayPanel().getRepoScrollPanel().setViewportView(mainFrame.getDisplayPanel().getRepoPanel());
        mainFrame.getDisplayPanel().repaint();
    }
    public void signOut() {
        mainFrame.dispose();
        new MainController().clientStart();
    }


    public void test() {
        LoginModel l = new LoginModel();
        l.setId("ksna");
        controller.setMyAccount(l);
        mainFrame = new MainFrame(controller);
    }

    public void drawViewTest(){
        mainFrame = new MainFrame(controller);
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
    }
    public void searchTest(){
        mainFrame = new MainFrame(controller);
        SearchPanel sf = new SearchPanel(mainFrame);
    }

    public int getComboMode() {
        return comboMode;
    }

    public void setComboMode(int comboMode) {
        if(comboMode == 3){
            mainFrame.getDrawContentPanel().getDrawPanel().addCoordPanel();
        } else {
            mainFrame.getDrawContentPanel().getDrawPanel().rmCoordPanel();
        }
        this.comboMode = comboMode;
    }
    public void loginStateUpdate(String s){
        loginFrame.getLp().getStateLabel().setText(s);
    }

    public void signupStateUpdate(String s) {
        loginFrame.getSup().getStateLabel().setText(s);
    }

    public void pwChangeStateUpdate(String s) {
        loginFrame.getPwcp().getStateLabel().setText(s);
    }
}
