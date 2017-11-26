package View.login;

import Control.GUIController;
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
 * Created by skrud on 2017-09-29.
 */
public class LoginPanel extends JPanel {
    private JTextField idTextField;
    private JPasswordField pwTextField;
    private JButton loginBtn;
    private JButton signUpBtn;
    private JButton pwChangeBtn;

    private JLabel logoLabel;
    private JLabel idLabel;
    private JLabel pwLabel ;
    private LoginFrame f;
    private GUIController gc;
    LoginPanel(LoginFrame f) {
        this.f = f;
        gc = f.getController().getGUIController();
        initLogin();
    }


    private void initLogin() {
        initUI();
    }


    private void initUI() {
        setLayout(null);
        setBackground(Pallate.a);
        initLabel();
        initTextField();
        initButton();

        autoLogin();
        this.setVisible(true);
    }

    private void initLabel() {
        logoLabel = new JLabel(MyImage.logo);
        idLabel = new JLabel("ID");
        pwLabel = new JLabel("PW");

        idLabel.setBounds(Size.SIGNIN_XMARGIN, Size.SIGNIN_YMARGIN, Size.SIGNIN_ID_W, Size.SIGNIN_ID_H);
        idLabel.setFont(MyFont.serif20);
        idLabel.setForeground(Color.WHITE);
        pwLabel.setBounds(Size.SIGNIN_XMARGIN,idLabel.getY()+idLabel.getHeight()+10, Size.SIGNIN_ID_W, Size.SIGNIN_ID_H);
        pwLabel.setFont(MyFont.serif20);
        pwLabel.setForeground(Color.WHITE);
        logoLabel.setBounds(Size.LOGO_X,Size.LOGO_Y,Size.LOGO_W,Size.LOGO_H);

//        rememberIDLabel.setBounds(Size.LABELXMARGIN+ 30, Size.LABELYMARGIN+ 130, 100, 30);

        add(idLabel);
        add(pwLabel);
        add(logoLabel);
//        add(rememberIDLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.loginBgImage.getImage(), 0, 0, null);
    }

    private void initTextField() {
        idTextField = new JTextField();
        pwTextField = new JPasswordField();

        idTextField.setBounds(idLabel.getX()+idLabel.getWidth()+20, Size.SIGNIN_YMARGIN, Size.SIGNIN_TEXT_W, idLabel.getHeight());
        pwTextField.setBounds(idTextField.getX(), idTextField.getY()+idTextField.getHeight()+10, Size.SIGNIN_TEXT_W, pwLabel.getHeight());
        idTextField.setOpaque(false);
        pwTextField.setOpaque(false);
        idTextField.setForeground(Color.WHITE);
        pwTextField.setForeground(Color.WHITE);
        idTextField.setFont(MyFont.serif20);
        pwTextField.setFont(MyFont.serif20);

        add(idTextField);
        add(pwTextField);
    }

    private void initButton() {
        loginBtn = new JButton(MyImage.btn_signin_lg);
        signUpBtn = new JButton(MyImage.btn_signup_lg);

        loginBtn.setBounds(Size.SIGNIN_XMARGIN, pwLabel.getY()+pwLabel.getHeight()+40, Size.SIGNIN_BTN_W, Size.SIGNIN_BTN_H);
        signUpBtn.setBounds(Size.SIGNIN_XMARGIN, loginBtn.getY()+loginBtn.getHeight()+20, Size.SIGNIN_BTN_W, Size.SIGNIN_BTN_H);
        add(loginBtn);
        add(signUpBtn);

        loginBtn.setContentAreaFilled(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        signUpBtn.setBorderPainted(false);
        signUpBtn.setFocusPainted(false);
        signUpBtn.setContentAreaFilled(false);

        buttonActionListen();
    }

    private void buttonActionListen() {
        pwTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    onPressedLoginBtn();
                }
            }
        });
        loginBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    onPressedLoginBtn();
                }
            }
        });
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == loginBtn){
                    onPressedLoginBtn();
                }
            }
        });
        signUpBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    onPressedSignUpBtn();
                }
            }
        });
        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == signUpBtn){
                    onPressedSignUpBtn();
                }
            }
        });
//        pwChangeBtn.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                super.keyPressed(e);
//                if(e.getKeyCode() == KeyEvent.VK_ENTER){
//                    onPressedPwChangeBtn();
//                }
//            }
//        });
//        pwChangeBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(e.getSource() == pwChangeBtn){
//                    onPressedPwChangeBtn();
//                }
//            }
//        });
        idTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    onPressedLoginBtn();
                }
            }
        });
        pwTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    onPressedLoginBtn();
                }
            }
        });
    }

    private void onPressedLoginBtn(){
        String id = idTextField.getText();
        String pw = String.valueOf(pwTextField.getPassword());
        if (!id.equals("") && !pw.equals("")) {
            pw = getSHA256(pw);
            f.getController().getLoginController().login(id,pw);
        }
    }
    private void onPressedSignUpBtn(){
        idTextField.setText("");
        pwTextField.setText("");
        gc.signUpView();
    }

    private void onPressedPwChangeBtn() {
        idTextField.setText("");
        pwTextField.setText("");
        gc.pwChangeView();
    }
    public void showOption(String text, int option){
        JOptionPane.showMessageDialog(this,text,text,option);
    }

    private String getSHA256(String str) {
        String SHA = "";
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            int byteLength = byteData.length;
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }
    private void autoLogin(){
        idTextField.setText("ksna");
        pwTextField.setText("1");
//        onPressedLoginBtn();
    }
}
