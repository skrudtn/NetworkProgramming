package Control;

import Model.ClassDiagramModel.CDModel;
import Model.RepoModel;
import View.MainView.*;
import View.MainView.Profile.ProfileFrame;
import View.login.LoginFrame;
import sun.dc.pr.PRError;

import javax.swing.*;

/**
 * Created by skrud on 2017-10-02.
 */
public class GUIController {
    private MainController controller = null;
    private LoginFrame loginFrame = null;
    private MainFrame mainFrame = null;
    private NewRepoFrame newRepoFrame = null;
    private ProfileFrame profileFrame = null;
    private VersionFrame versionFrame = null;
    private UMLController uc=null;

    private int comboMode;

    GUIController(MainController controller) {
        this.controller = controller;
        this.uc = controller.getUmlController();
        comboMode = 0;
    }

    void newLoginView(MainController controller) {
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

    void newDisplayView(){
        loginFrame.dispose();
        mainFrame = new MainFrame(controller);
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
        versionFrame.dispose();
        mainFrame.setTitle(controller.getUmlController().getRepoModel().getRepoName());
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
    }
    public void versionView(){
        if(newRepoFrame !=null) newRepoFrame.dispose();
        versionFrame = new VersionFrame(controller);
        if (controller.getUmlController().getRepoModel().isAutho()){
            versionFrame.permission();
        } else{
            versionFrame.noPermission();
        }
    }

    public void newRepoView(){
        newRepoFrame = new NewRepoFrame(controller);
    }
    public void disposeNewRepoView(){
        newRepoFrame.dispose();
    }

    void showMainMessage(String s, int i){
        JOptionPane.showMessageDialog(mainFrame,s,s,i);
    }
    void showRepoMessage(int i){
        JOptionPane.showMessageDialog(newRepoFrame, "Overlapped Repo Name", "Overlapped Repo Name",i);
    }

    public void setOverLapFlag(int i){
        if(i==0) {
            loginFrame.getSup().setIdFlag(true);
        } else if(i==1){
        }
    }


    public void push(){
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
    }

    public void cllone(){
        versionFrame.dispose();
        mainFrame.setTitle(uc.getCdModel().getCdName());
        mainFrame.getCardLayout().show(mainFrame.getContentPane(),"draw");
        mainFrame.getDrawContentPanel().getDrawPanel().cllone();
        if (controller.getUmlController().getRepoModel().isAutho()){
            mainFrame.getDrawContentPanel().getDrawPanel().permission();
        } else{
            mainFrame.getDrawContentPanel().getDrawPanel().noPermission();
        }
    }

    public void searchUpdate() {
        mainFrame.getDisplayPanel().getNoticePanel().initResultPanel();
        mainFrame.getDisplayPanel().getNoticePanel().addResultPanel(controller.getSdms());
        mainFrame.getDisplayPanel().getNoticeScrollPanel().setViewportView(mainFrame.getDisplayPanel().getNoticePanel());
        mainFrame.getDisplayPanel().repaint();
    }
    void myRepoUpdate() {
        mainFrame.getDisplayPanel().getRepoPanel().initResultPanel();
        mainFrame.getDisplayPanel().getRepoPanel().addResultPanel(controller.getMySdms());
        mainFrame.getDisplayPanel().getRepoScrollPanel().setViewportView(mainFrame.getDisplayPanel().getRepoPanel());
        mainFrame.getDisplayPanel().repaint();
    }
    void signOut() {
        profileFrame.dispose();
        mainFrame.dispose();
        new MainController().clientStart();
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
        if(!(comboMode == 1 || comboMode==2)){
            mainFrame.getDrawContentPanel().getDrawPanel().addCoordPanel();
        } else {
            mainFrame.getDrawContentPanel().getDrawPanel().rmCoordPanel();
        }
        this.comboMode = comboMode;
    }
    void loginStateUpdate(String s,boolean posi){
        if(posi){
            loginFrame.getLp().showOption(s,1);
        }else {
            loginFrame.getLp().showOption(s, 0);
        }
    }
    void signupStateUpdate(String s, boolean posi) {
        if(posi){
            loginFrame.getSup().showOption(s,1);
        }else {
            loginFrame.getSup().showOption(s, 0);
        }
    }

    public void displayViewTest() {
        mainFrame = new MainFrame(controller);
    }

    public void pwChangeStateUpdate(String s,int i) {
        profileFrame.getPwcp().showOption(s,i);
    }
}
