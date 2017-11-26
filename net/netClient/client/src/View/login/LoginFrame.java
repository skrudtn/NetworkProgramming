package View.login;

import Control.MainController;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-10-02.
 */
public class LoginFrame extends JFrame {
    private MainController controller = null;
    private CardLayout cards;
    private LoginPanel lp;
    private SignUpPanel sup;


    public LoginFrame(MainController controller){
        this.controller = controller;
        cards =  new CardLayout();
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setSize(Size.LOGIN_FRAME_W, Size.LOGIN_FRAME_H);
        setTitle("Sign In");
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(cards);
        lp = new LoginPanel(this);
        sup = new SignUpPanel(this);
        getContentPane().add("login",lp);
        getContentPane().add("signup",sup);
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

}
