package View.login;

import Control.MainController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-10-02.
 */
public class LoginFrame extends JFrame {
    private static final int FRAMEWIDTH = 400;
    private static final int FRAMEHEIGHT = 480;
    private MainController controller = null;
    private CardLayout cards;
    private LoginPanel lp;
    private SignUpPanel sup;
    private PWChangePanel pwcp;

    public LoginFrame(MainController controller){
        this.controller = controller;
        cards =  new CardLayout();
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        setTitle("로그인");
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(cards);
        lp = new LoginPanel(this);
        sup = new SignUpPanel(this);
        pwcp = new PWChangePanel(this);
        getContentPane().add("login",lp);
        getContentPane().add("signup",sup);
        getContentPane().add("pwchange",pwcp);
        setVisible(true);
    }

    public MainController getController() {
        return controller;
    }
    public void changePanel(){
        cards.next(this.getContentPane());
    }
    public CardLayout getCardLayout(){
        return cards;
    }
    public LoginPanel getLp() {
        return lp;
    }
    public SignUpPanel getSup() {
        return sup;
    }
    public PWChangePanel getPwcp() {
        return pwcp;
    }

}
