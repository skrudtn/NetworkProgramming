package View.Profile;

import Control.MainController;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * Created by skrud on 2017-11-16.
 */
public class AddFriendPanel extends JPanel {
    private ProfileFrame f;
    private MainController controller;
    private JList friendJList;
    private JButton btn;
    private TextField searchField;

    public AddFriendPanel(ProfileFrame f) {
        this.f=f;
        this.controller = f.getController();
        this.setLayout(null);
        initUI();
    }

    public AddFriendPanel(MainController controller) {
        this.controller = controller;
        initUI();
    }

    public void initUI(){
        this.setBackground(Pallate.a);
        JLabel nameLabel = new JLabel("Add Friend");
        JLabel friendLabel = new JLabel("Friend List");
        btn = new JButton();
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==btn){
                    onPressOk();
                }
            }
        });
        searchField = new TextField(30);
        searchField.setFont(new Font("Serif", Font.BOLD, 15));
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

        nameLabel.setBounds(Size.SUXMARGIN,0,Size.CLAZZWIDTH,Size.BACKBTNHEIGHT);
        nameLabel.setBackground(Color.black);
        searchField.setBounds(nameLabel.getX(),nameLabel.getY()+nameLabel.getHeight(),nameLabel.getWidth(),30);
        btn.setBounds(searchField.getX()+searchField.getWidth()+10,searchField.getY(),Size.BACKBTNWIDTH,searchField.getHeight());
        friendLabel.setBounds(Size.SUXMARGIN,searchField.getY()+searchField.getHeight(),Size.CLAZZWIDTH,Size.BACKBTNHEIGHT);
        jScrollPane.setBounds(Size.SUXMARGIN, friendLabel.getY()+friendLabel.getHeight(), Size.FRAMEWIDTH-Size.SUXMARGIN*2, Size.MENUBARWIDTH);

        add(friendLabel);
        add(btn);
        add(searchField);
        add(nameLabel);
        add(jScrollPane);
    }
    private void onPressOk(){
        String data = searchField.getText();
        String str = f.getController().getJsonController().getSearchId(data);
        if(!data.equals("")) {
            f.getController().getNetworkController().sendStr(str);
        } else if(!data.equals(f.getController().getLoginController().getMyAccount().getId())){
            f.getController().getGUIController().searchIdStateUpdate("Can not add myself",0);
        }
    }

    public void showOption(String s, int i) {
        JOptionPane.showMessageDialog(this, s, s, i);
    }
}
