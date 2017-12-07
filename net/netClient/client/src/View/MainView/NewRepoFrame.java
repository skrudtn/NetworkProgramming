package View.MainView;

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
 * Created by skrud on 2017-11-01.
 */

public class NewRepoFrame extends JFrame {
    private static final int TEXTHEIGHT = 30;
    private static final int BTNWIDTH = 90;
    private static final int XMARGIN = 50;
    private static final int YMARGIN = 50;

    private MainController controller;
    private JPanel conPanel;
    private JTextField textField;
    private JList friendJList;
    private JButton btn;

    public NewRepoFrame(MainController controller) {
        this.controller = controller;
        setSize(Size.LOGIN_FRAME_W, Size.LOGIN_FRAME_H);
        setTitle("Create New Repository");
        setResizable(false);
        setLocationRelativeTo(null);

        conPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(MyImage.loginBgImage.getImage(), 0, 0, null);
            }
        };
        conPanel.setLayout(null);
        conPanel.setBackground(Pallate.a);
        initUI();
        initBtn();
        setContentPane(conPanel);
        setVisible(true);
    }


    private void initBtn() {
        btn = new JButton(MyImage.btn_ok);
        btn.setBounds(textField.getX() + textField.getWidth() + 20, textField.getY(), BTNWIDTH, TEXTHEIGHT);
        btn.setBackground(Pallate.a);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        conPanel.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn) action();
            }
        });

    }

    private void initUI() {
        JLabel nameLabel = new JLabel("Repository Name");
        nameLabel.setBounds(XMARGIN, YMARGIN, Size.CLAZZWIDTH, TEXTHEIGHT);
        nameLabel.setFont(MyFont.serif);

        textField = new JTextField(25);
        textField.setBounds(XMARGIN, nameLabel.getY() + nameLabel.getHeight() + 20, Size.CLAZZWIDTH, TEXTHEIGHT);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    action();
            }
        });

        JLabel authoLabel = new JLabel("Add Member");
        authoLabel.setBounds(XMARGIN, textField.getY() + TEXTHEIGHT + 10, Size.CLAZZWIDTH, TEXTHEIGHT);
        authoLabel.setFont(MyFont.serif);

        Vector<String> vector = new Vector<>();
        for (String str : controller.getFriends()) {
            vector.add(str);
        }

        friendJList = new JList(vector);
        JScrollPane jScrollPane = new JScrollPane(friendJList);
        jScrollPane.setBounds(XMARGIN, authoLabel.getY() + TEXTHEIGHT + 10, Size.CLAZZWIDTH, TEXTHEIGHT * 3);
        conPanel.add(textField);
        conPanel.add(nameLabel);
        conPanel.add(jScrollPane);
        conPanel.add(authoLabel);
    }


    private void action() {
        String repoName = textField.getText();
        if (!repoName.equals("")) {
            ArrayList<String> authorities = null;
            if (!friendJList.isSelectionEmpty()) {
                authorities = (ArrayList<String>) friendJList.getSelectedValuesList();
            }
            controller.getNetworkController().sendStr(controller.getJsonController().getSearchRepoStr(repoName, controller.getLoginController().getMyAccount().getId(), authorities));

        } else {
            JOptionPane.showMessageDialog(this, "Please enter a Repository Name", "Please enter a Repository Name", 0);
        }
    }

}
