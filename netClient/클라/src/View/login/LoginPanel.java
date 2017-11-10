package View.login;

import Control.GUIController;
import Model.Pallate;

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

    private JLabel stateLabel;
    private JTextField idTextField;
    private JPasswordField pwTextField;
    private JButton loginBtn;
    private JButton signUpBtn;
    private JButton pwChangeBtn;
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
        setBackground(Pallate.b);
        initLabel();
        initTextField();
        initButton();

        this.setVisible(true);
    }

    private void initLabel() {
        JLabel idLabel = new JLabel("아이디");
        JLabel pwLabel = new JLabel("비밀번호");
        JLabel rememberIDLabel = new JLabel("아이디저장");
        stateLabel = new JLabel();

        idLabel.setBounds(XMARGIN, YMARGIN, 60, 30);
        idLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel.setBounds(XMARGIN, YMARGIN + 65, 60, 30);
        pwLabel.setFont(new Font("Serif", Font.BOLD, 15));
        rememberIDLabel.setBounds(XMARGIN + 30, YMARGIN + 130, 100, 30);
        stateLabel.setBounds(XMARGIN+30,YMARGIN/2, 120,20);
        stateLabel.setFont(new Font("Serif", Font.BOLD, 14));

        add(idLabel);
        add(pwLabel);
        add(rememberIDLabel);
        add(stateLabel);
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
        JCheckBox jCheckBox = new JCheckBox();

        jCheckBox.setBounds(XMARGIN, YMARGIN + 130, 25, 30);
        loginBtn.setBounds(295, YMARGIN + 20, 80, 95);
        loginBtn.setBackground(Pallate.e);
        loginBtn.setForeground(Pallate.a);
        signUpBtn.setBounds(XMARGIN + 10, 330, 120, 50);
        signUpBtn.setBounds(XMARGIN + 10, 330, 120, 50);
        signUpBtn.setBackground(Pallate.e);
        signUpBtn.setForeground(Pallate.a);
        pwChangeBtn.setBounds(XMARGIN + 150, 330, 120, 50);
        pwChangeBtn.setBounds(XMARGIN + 150, 330, 120, 50);
        pwChangeBtn.setBackground(Pallate.e);
        pwChangeBtn.setForeground(Pallate.a);

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
        stateLabel.setText("");
        gc.signUpView();
    }

    private void onPressedPwChangeBtn() {
        idTextField.setText("");
        pwTextField.setText("");
        stateLabel.setText("");
        gc.pwChangeView();
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

    public JLabel getStateLabel() {
        return stateLabel;
    }
}
