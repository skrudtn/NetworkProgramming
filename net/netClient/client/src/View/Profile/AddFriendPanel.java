package View.Profile;

import Control.MainController;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by skrud on 2017-11-16.
 */
public class AddFriendPanel extends JPanel {
    private ProfileFrame f;
    private MainController controller;
    private JList friendJList;
    private JButton btn;
    private JTextField searchField;

    public AddFriendPanel(ProfileFrame f) {
        this.f=f;
        this.controller = f.getController();
        this.setLayout(null);
        initUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.loginBgImage.getImage(), 0, 0, null);
    }

    public void initUI(){
        JLabel nameLabel = new JLabel("Add Friend");
        JLabel friendLabel = new JLabel("List of Friends");
        btn = new JButton(MyImage.btn_ok);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==btn){
                    onPressOk();
                }
            }
        });

        searchField = new JTextField(30);
        searchField.setFont(MyFont.serif20);
        searchField.setOpaque(false);
        searchField.setForeground(Color.WHITE);
        searchField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onPressOk();
                }
            }
        });
        add(searchField);
        Vector<String> vector = new Vector<>();
        for(String str: controller.getFriends()){
            vector.add(str);
        }

        friendJList = new JList(vector);
        JScrollPane jScrollPane = new JScrollPane(friendJList);

        nameLabel.setBounds(Size.AFP_XMARGIN,Size.AFP_YMARGIN,Size.CLAZZWIDTH,Size.BACKBTNHEIGHT);
        nameLabel.setOpaque(false);
        nameLabel.setFont(MyFont.serif20);
        nameLabel.setForeground(Color.WHITE);
        searchField.setBounds(nameLabel.getX(),nameLabel.getY()+nameLabel.getHeight(),nameLabel.getWidth(),30);
        btn.setBounds(searchField.getX()+searchField.getWidth()+10,searchField.getY(),Size.BACKBTNWIDTH,searchField.getHeight());
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        friendLabel.setBounds(nameLabel.getX(),searchField.getY()+searchField.getHeight(),Size.CLAZZWIDTH,Size.BACKBTNHEIGHT);
        friendLabel.setOpaque(false);
        friendLabel.setFont(MyFont.serif20);
        friendLabel.setForeground(Color.WHITE);
        jScrollPane.setBounds(nameLabel.getX(), friendLabel.getY()+friendLabel.getHeight(), f.getWidth()-Size.SIGNUP_XMARGIN*2, Size.MENUBARWIDTH);

        add(friendLabel);
        add(btn);
        add(searchField);
        add(nameLabel);
        add(jScrollPane);
    }
    private void onPressOk(){
        String data = searchField.getText();
        String str = f.getController().getJsonController().getSearchId(data);
        String id = f.getController().getLoginController().getMyAccount().getId();
        ArrayList<String> friends = f.getController().getFriends();
        boolean isFriend = false;
        for(String friend: friends){
            if(data.equals(friend)){
                isFriend = true;
            }
        }
        if(!data.equals("") && !data.equals(id) &&  !isFriend) {
            f.getController().getNetworkController().sendStr(str);
        } else if(data.equals(id)){
            f.getController().getGUIController().searchIdStateUpdate("Can not add myself",0);
        } else if(isFriend){
            f.getController().getGUIController().searchIdStateUpdate("Already friends",0);
        } else {
            f.getController().getGUIController().searchIdStateUpdate("Check the id",0);
        }
    }

    public void showOption(String s, int i) {
        System.out.println(s);
        JOptionPane.showMessageDialog(this, s, s, i);
    }
}
