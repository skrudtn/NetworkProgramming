package View.Profile;

import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;
import jdk.nashorn.internal.objects.annotations.Constructor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-16.
 */
public class ProfilePanel extends JPanel{
    private ProfileFrame f;
    private JLabel emailLabel;
    private JButton logoutBtn;
    private JButton friendBtn;
    private JButton pwcBtn;
    private String id;
    private String name;
    private String email;

    ProfilePanel(ProfileFrame f){
        this.f =f;
        f.setTitle("Profile");
        id = f.getController().getLoginController().getMyAccount().getId();
        name = f.getController().getLoginController().getMyAccount().getName();
        email = f.getController().getLoginController().getMyAccount().getEmail();
        this.setLayout(null);
        initLabel();
        initBtn();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.loginBgImage.getImage(), 0, 0, null);
    }

    private void initLabel(){
        JLabel idLabel = new JLabel("ID          | "+id);
        JLabel nameLabel = new JLabel("NAME  | "+name);
        emailLabel = new JLabel("EMAIL | "+email);

        idLabel.setFont(MyFont.serif20);
        emailLabel.setFont(MyFont.serif20);
        nameLabel.setFont(MyFont.serif20);

        idLabel.setForeground(Color.BLACK);
        emailLabel.setForeground(Color.BLACK);
        nameLabel.setForeground(Color.BLACK);

        idLabel.setBounds(Size.PROFILE_XMARGIN,Size.PROFILE_YMARGIN,Size.PROFILE_LABEL_W,Size.PROFILE_LABEL_H);
        nameLabel.setBounds(Size.PROFILE_XMARGIN,idLabel.getY()+idLabel.getHeight(),Size.PROFILE_LABEL_W,Size.PROFILE_LABEL_H);
        emailLabel.setBounds(Size.PROFILE_XMARGIN,nameLabel.getY()+nameLabel.getHeight(),Size.PROFILE_LABEL_W,Size.PROFILE_LABEL_H);
        this.add(idLabel);
        this.add(emailLabel);
        this.add(nameLabel);
    }

    private void initBtn() {
        logoutBtn= new JButton(MyImage.btn_signout);
        pwcBtn = new JButton(MyImage.btn_changepw);
        friendBtn = new JButton(MyImage.btn_addfriend);

        friendBtn.setBorderPainted(false);
        friendBtn.setFocusPainted(false);
        friendBtn.setContentAreaFilled(false);
        friendBtn.setBounds(Size.PROFILE_XMARGIN,emailLabel.getY()+emailLabel.getHeight()+40,Size.ADD_FRIEND_BTN_W,Size.ADD_FRIEND_BTN_H);

        pwcBtn.setBorderPainted(false);
        pwcBtn.setFocusPainted(false);
        pwcBtn.setContentAreaFilled(false);
        pwcBtn.setBounds(friendBtn.getX()+friendBtn.getWidth()+20,friendBtn.getY(),Size.ADD_FRIEND_BTN_W,Size.ADD_FRIEND_BTN_H);

        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBounds(f.getWidth()-(Size.SIGN_OUT_BTN_W+20),f.getHeight()-(Size.SIGN_OUT_BTN_H+40),Size.SIGN_OUT_BTN_W,Size.SIGN_OUT_BTN_H);

        add(pwcBtn);
        add(friendBtn);
        add(logoutBtn);
        addAction();
    }
    private void addAction() {
        logoutBtn.addActionListener(e -> {
            if (e.getSource() == logoutBtn) {
                f.getController().getNetworkController().sendStr(f.getController().
                        getJsonController().getLogoutStr(f.getController().getLoginController().getMyAccount().getId()));
            }
        });
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

}
