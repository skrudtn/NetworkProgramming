package View.Profile;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by skrud on 2017-10-23.
 */
public class PWChangePanel extends JPanel {
    private final static int XMARGIN = 50;
    private final static int YMARGIN = 80;

    private ProfileFrame f;

    private JButton okBtn;
    private JButton pwCheckBtn;
    private JButton backBtn;

    private JLabel idLabel;
    private JLabel pwLabel;
    private JLabel pwLabel2;

    private JTextField idTextField;
    private JPasswordField pwTextField;
    private JPasswordField pwTextField2;

    private boolean pwFlag = false;
    private boolean idFlag = false;
    PWChangePanel(ProfileFrame f) {
        this.f = f;
        initUI();
    }

    private void initUI() {
        setLayout(null);
        setBackground(Pallate.a);
        setVisible(true);
        initLabel();
        initTextField();
        initButton();
        initActionListener();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.loginBgImage.getImage(), 0, 0, null);
    }

    private void initLabel() {
        idLabel = new JLabel("ID ");
        pwLabel = new JLabel("PW");
        pwLabel2 = new JLabel("PW2");

        idLabel.setBounds(Size.SIGNUP_XMARGIN, Size.SIGNUP_YMARGIN, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        idLabel.setFont(MyFont.serif20);
        pwLabel.setBounds(idLabel.getX(), idLabel.getY()+idLabel.getHeight()+20, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        pwLabel.setFont(MyFont.serif20);
        pwLabel2.setBounds(pwLabel.getX(), pwLabel.getY()+pwLabel.getHeight()+20, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        pwLabel2.setFont(MyFont.serif20);

        idLabel.setForeground(Color.BLACK);
        pwLabel.setForeground(Color.BLACK);
        pwLabel2.setForeground(Color.BLACK);

        add(idLabel);
        add(pwLabel);
        add(pwLabel2);

    }

    private void initTextField() {

        idTextField = new JTextField(f.getController().getLoginController().getMyAccount().getId());
        pwTextField = new JPasswordField();
        pwTextField2 = new JPasswordField();

        idTextField.setBounds(pwLabel.getX()+ pwLabel.getWidth() + 10,idLabel.getY(), Size.SIGNUP_TEXT_W, idLabel.getHeight());
        idTextField.setFont(MyFont.serif18);
//        idTextField.setOpaque(false);
        idTextField.setForeground(Color.BLACK);
        idTextField.setEnabled(false);

        pwTextField.setBounds(idTextField.getX(), pwLabel.getY(), idTextField.getWidth(), idLabel.getHeight());
        pwTextField.setFont(MyFont.serif18);
//        pwTextField.setOpaque(false);
        pwTextField.setForeground(Color.BLACK);
        pwTextField2.setBounds(idTextField.getX(), pwLabel2.getY(), idTextField.getWidth(), idLabel.getHeight());
        pwTextField2.setFont(MyFont.serif18);
//        pwTextField2.setOpaque(false);
        pwTextField2.setForeground(Color.BLACK);

        add(idTextField);
        add(pwTextField);
        add(pwTextField2);
    }

    private void initButton() {

        okBtn = new JButton(MyImage.btn_changepw);
        pwCheckBtn = new JButton(MyImage.btn_check);
        backBtn = new JButton(MyImage.btn_cancel_sm);

        okBtn.setBounds(pwLabel2.getX(), pwLabel2.getY() + pwLabel2.getHeight() + 40, Size.ADD_FRIEND_BTN_W, Size.ADD_FRIEND_BTN_H);
        pwCheckBtn.setBounds(idTextField.getX() + idTextField.getWidth() + 20, pwTextField2.getY(), Size.CHECK_BTN, Size.CHECK_BTN);
        backBtn.setBounds(okBtn.getX()+okBtn.getWidth()+30, okBtn.getY(), okBtn.getWidth(), okBtn.getHeight());

        okBtn.setBorderPainted(false);
        okBtn.setFocusPainted(false);
        okBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        pwCheckBtn.setBorderPainted(false);
        pwCheckBtn.setFocusPainted(false);
        pwCheckBtn.setContentAreaFilled(false);

        add(okBtn);
        add(pwCheckBtn);
        add(backBtn);
    }

    private void initActionListener() {
        pwCheckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == pwCheckBtn) {
                    onPressedPWBtn();
                }
            }
        });
        pwCheckBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onPressedPWBtn();
                }
            }
        });

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == okBtn) {
                    onPressedOkbtn();
                }
            }
        });
        okBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onPressedOkbtn();
                }
            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backBtn) {
                    onPressedBackBtn();
                }
            }
        });
        backBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onPressedBackBtn();
                }
            }
        });

    }

    private void onPressedOkbtn() {
        String pw = getSHA256(String.valueOf(pwTextField.getPassword()));

        if (pwFlag) {
            f.getController().getLoginController().pwChange(f.getController().getLoginController().getMyAccount().getId(), pw);
            f.getCards().show(f.getContentPane(),"sp");
        }
    }


    private void onPressedPWBtn() {
        String pw = String.valueOf(pwTextField.getPassword());
        String pw2 = String.valueOf(pwTextField2.getPassword());
        if (pw.equals(pw2) && !pw.equals("")) {
            pwFlag = true;
            showOption("Same PW",JOptionPane.PLAIN_MESSAGE);
        } else{
            showOption("Check the PW",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onPressedBackBtn() {
        idTextField.setText("");
        pwTextField.setText("");
        pwTextField2.setText("");
        f.getCards().show(f.getContentPane(),"pp");
    }

    private String getSHA256(String str) {
        String SHA = "";
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            int byteLength = byteData.length;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteLength; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }

    public boolean isPwFlag() {
        return pwFlag;
    }

    public void setPwFlag(boolean pwFlag) {
        this.pwFlag = pwFlag;
    }

    public boolean isIdFlag() {
        return idFlag;
    }

    public void setIdFlag(boolean idFlag) {
        this.idFlag = idFlag;
    }

    public void showOption(String s, int i) {
        JOptionPane.showMessageDialog(this,s,s,i);
    }

}
