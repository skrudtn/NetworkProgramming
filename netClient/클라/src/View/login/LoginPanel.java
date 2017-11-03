package View.login;

import Control.GUIController;

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
    private static final int XMARGIN = 60;
    private static final int YMARGIN = 120;

    private JLabel idLabel;
    private JLabel pwLabel;
    private JLabel rememberIDLabel;
    private JTextField idTextField;
    private JPasswordField pwTextField;
    private JButton loginBtn;
    private JButton signUpBtn;
    private JButton pwChangeBtn;
    private JCheckBox jCheckBox;
    private LoginFrame f;
    private GUIController gc;
    public LoginPanel(LoginFrame f) {
        this.f = f;
        gc = f.getController().getGUIController();
        initLogin();
    }


    public void initLogin() {
        initUI();
    }


    public void initUI() {
        setLayout(null);
        setBackground(new Color(255, 255, 236));
        initLabel();
        initTextField();
        initButton();

        this.setVisible(true);
    }

    private void initLabel() {
        idLabel = new JLabel("아이디");
        pwLabel = new JLabel("비밀번호");
        rememberIDLabel = new JLabel("아이디저장");

        idLabel.setBounds(XMARGIN, YMARGIN, 60, 30);
        idLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel.setBounds(XMARGIN, YMARGIN + 65, 60, 30);
        pwLabel.setFont(new Font("Serif", Font.BOLD, 15));
        rememberIDLabel.setBounds(XMARGIN + 30, YMARGIN + 130, 100, 30);

        add(idLabel);
        add(pwLabel);
        add(rememberIDLabel);
    }

    private void initTextField() {
        idTextField = new JTextField();
        pwTextField = new JPasswordField();

        idTextField.setBounds(XMARGIN, YMARGIN + 30, 220, 30);
        pwTextField.setBounds(XMARGIN, YMARGIN + 95, 220, 30);

        add(idTextField);
        add(pwTextField);
    }

    private void initButton() {
        loginBtn = new JButton("로그인");
        signUpBtn = new JButton("회원가입");
        pwChangeBtn = new JButton("비밀번호찾기");
        jCheckBox = new JCheckBox();

        jCheckBox.setBounds(XMARGIN, YMARGIN + 130, 25, 30);
        loginBtn.setBounds(295, YMARGIN + 20, 80, 95);
        signUpBtn.setBounds(XMARGIN + 10, 330, 120, 50);
        pwChangeBtn.setBounds(XMARGIN + 150, 330, 120, 50);

        add(jCheckBox);
        add(loginBtn);
        add(signUpBtn);
        add(pwChangeBtn);
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
        pwChangeBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    onPressedPwChangeBtn();
                }
            }
        });
        pwChangeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == pwChangeBtn){
                    onPressedPwChangeBtn();
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

    public String getSHA256(String str) {
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
}
