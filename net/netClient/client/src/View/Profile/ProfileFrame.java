package View.Profile;

import Control.MainController;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-08.
 */
public class ProfileFrame extends JFrame {
    private CardLayout cards;
    private PWChangePanel pwcp;
    private ProfilePanel pp;
    private AddFriendPanel afp;
    private MainController controller;
    public ProfileFrame(MainController controller){
        this.controller = controller;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(Size.LOGIN_FRAME_W, Size.LOGIN_FRAME_H);
        setResizable(false);
        setLocationRelativeTo(null);

        cards =  new CardLayout();
        getContentPane().setLayout(cards);

        setSize(Size.LOGIN_FRAME_W, Size.LOGIN_FRAME_H);
        setLocationRelativeTo(null);

        pwcp = new PWChangePanel(this);
        pp = new ProfilePanel(this);
        afp = new AddFriendPanel(this);

        getContentPane().add("pp",pp);
        getContentPane().add("pwcp",pwcp);
        getContentPane().add("afp", afp);
        setVisible(true);
    }

    public MainController getController() {
        return controller;
    }

    public CardLayout getCards() {
        return cards;
    }

    public PWChangePanel getPwcp() {
        return pwcp;
    }

    public ProfilePanel getPp() {
        return pp;
    }

    public AddFriendPanel getAfp() {
        return afp;
    }

}
