package View.MainView.Profile;

import Model.StaticModel.MyFont;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by skrud on 2017-11-16.
 */
public class ProfilePanel extends JPanel{
    private ProfileFrame f;
    private JLabel btnLabel;
    private JButton settingBtn;
    private JButton logoutBtn;
    ProfilePanel(ProfileFrame f){
        this.f =f;
        f.setTitle("Profile");
        this.setLayout(new GridLayout(5,1));
        this.setBackground(Pallate.a);
        initLabel();
        initBtn();
    }

    private void initLabel(){
        JLabel idLabel = new JLabel(f.getController().getLoginController().getMyAccount().getId());
        JLabel emailLabel = new JLabel(f.getController().getLoginController().getMyAccount().getEmail());
        JLabel dateLabel = new JLabel(f.getController().getLoginController().getMyAccount().getReg_date());
        JLabel nameLabel = new JLabel(f.getController().getLoginController().getMyAccount().getName());
        btnLabel = new JLabel();
        idLabel.setHorizontalAlignment(0);
        emailLabel.setHorizontalAlignment(0);
        dateLabel.setHorizontalAlignment(0);
        nameLabel.setHorizontalAlignment(0);
        btnLabel.setHorizontalAlignment(0);

        idLabel.setFont(MyFont.serif25);
        emailLabel.setFont(MyFont.serif25);
        dateLabel.setFont(MyFont.serif25);
        nameLabel.setFont(MyFont.serif25);

        this.add(idLabel);
        this.add(emailLabel);
        this.add(nameLabel);
        this.add(dateLabel);
        this.add(btnLabel);
    }

    private void initBtn() {
        settingBtn = new JButton(getImage("client\\Image\\settingBtn.png"));
        logoutBtn= new JButton(getImage("client\\Image\\logoutBtn.png"));

        settingBtn.setBorderPainted(false);
        settingBtn.setFocusPainted(false);
        settingBtn.setContentAreaFilled(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setContentAreaFilled(false);
        settingBtn.setBounds(5,5,Size.FRAMEWIDTH/3-10,Size.FRAMEHEIGHT/5-10);
        logoutBtn.setBounds(5+Size.FRAMEWIDTH/3*2,5,Size.FRAMEWIDTH/3-10,Size.FRAMEHEIGHT/5-10);
        btnLabel.add(settingBtn);
        btnLabel.add(logoutBtn);
        addAction();
    }
    private void addAction(){
        logoutBtn.addActionListener(e -> {
            if(e.getSource() == logoutBtn){
                f.getController().getNetworkController().sendStr(f.getController().
                        getJsonController().getLogoutStr(f.getController().getLoginController().getMyAccount().getId()));
            }
        });
        settingBtn.addActionListener(e -> {
            if(e.getSource() == settingBtn){
                f.setTitle("Setting");
                f.getCards().show(f.getContentPane(),"sp");
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
