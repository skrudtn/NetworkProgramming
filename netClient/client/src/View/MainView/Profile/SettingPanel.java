package View.MainView.Profile;

import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-16.
 */
public class SettingPanel extends JPanel {
    private ProfileFrame f;
    private JButton friendBtn;
    private JButton pwcBtn;

    SettingPanel(ProfileFrame f) {
        this.f = f;
        this.setBackground(Pallate.a);

        initBtn();
    }

    private void initBtn() {
        pwcBtn = new JButton(getImage("client\\Image\\pwChangeBtn.png"));
        friendBtn = new JButton(getImage("client\\Image\\friendBtn.png"));

        pwcBtn.setBorderPainted(false);
        pwcBtn.setFocusPainted(false);
        pwcBtn.setContentAreaFilled(false);
        friendBtn.setBorderPainted(false);
        friendBtn.setFocusPainted(false);
        friendBtn.setContentAreaFilled(false);
        this.add(pwcBtn);
        this.add(friendBtn);
        addAction();
    }

    private void addAction(){

        friendBtn.addActionListener(e -> {
            if (e.getSource() == friendBtn) {
                f.getCards().show(f.getContentPane(), "afp");
            }
        });
        pwcBtn.addActionListener(e -> {
            if (e.getSource() == pwcBtn) {
                f.getCards().show(f.getContentPane(), "pwcp");
            }
        });
    }
    private ImageIcon getImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image changeImage = image.getScaledInstance(Size.FRAMEWIDTH/3-10,Size.FRAMEHEIGHT/5-10, Image.SCALE_SMOOTH);
        return new ImageIcon(changeImage);
    }

}
