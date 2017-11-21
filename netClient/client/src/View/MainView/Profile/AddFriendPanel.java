package View.MainView.Profile;

import Control.MainController;
import Model.StaticModel.Pallate;

import javax.swing.*;

/**
 * Created by skrud on 2017-11-16.
 */
public class AddFriendPanel extends JPanel {
    private ProfileFrame f;
    private MainController controller;
    public AddFriendPanel(ProfileFrame f) {
        this.f=f;
        this.controller = f.getController();
        initUI();
    }

    public AddFriendPanel(MainController controller) {
        this.controller = controller;
        initUI();
    }

    public void initUI(){
        this.setBackground(Pallate.a);

    }

}
