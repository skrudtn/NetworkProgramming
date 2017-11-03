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

        System.out.println("gc.push");
    }

    public void cllone(){
        mainFrame.setTitle(controller.getCdModel().getCdName());
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
        mainFrame.getDrawContentPanel().getDrawPanel().cllone();
        System.out.println("gc.clone");
    }
    public void search(){
//        mainFrame.getCardLayout().show(mainFrame.getContentPane(), "search");
        searchPanel = new SearchPanel(mainFrame);
    }

    public void displayViewTest() {
        mainFrame = new MainFrame(controller);
    }

    public void searchUpdate() {
        System.out.println("Search Update");
        mainFrame.getDisplayPanel().getNoticePanel().initResultPanel();
        mainFrame.getDisplayPanel().getNoticePanel().addResultPanel(controller.getSdms());
        mainFrame.getDisplayPanel().getNoticeScrollPanel().setViewportView(mainFrame.getDisplayPanel().getNoticePanel());
        mainFrame.getDisplayPanel().repaint();
    }
    public void repositoryUpdate() {
        System.out.println("repository Update");
        mainFrame.getDisplayPanel().getReposiPanel().initResultPanel();
        mainFrame.getDisplayPanel().getReposiPanel().addResultPanel(controller.getReposiData());
        mainFrame.getDisplayPanel().getRepoScrollPanel().setViewportView(mainFrame.getDisplayPanel().getReposiPanel());
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

}
