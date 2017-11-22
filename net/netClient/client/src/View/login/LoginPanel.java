package View.login;

import Control.GUIController;
import Model.StaticModel.MyFont;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
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
        JLabel idLabel = new JLabel("아이디");
        JLabel pwLabel = new JLabel("비밀번호");
        JLabel rememberIDLabel = new JLabel("아이디저장");

        idLabel.setBounds(Size.LABELXMARGIN, Size.LABELYMARGIN, 60, 30);
        idLabel.setFont(MyFont.serif);
//        idLabel.setFont(new MyFont().getNBG());
        pwLabel.setBounds(Size.LABELXMARGIN, Size.LABELYMARGIN+ 65, 60, 30);
        pwLabel.setFont(MyFont.serif);
        rememberIDLabel.setBounds(Size.LABELXMARGIN+ 30, Size.LABELYMARGIN+ 130, 100, 30);

        add(idLabel);
        add(pwLabel);
//        add(rememberIDLabel);
    }

    private void initTextField() {
        idTextField = new JTextField();
        pwTextField = new JPasswordField();

        idTextField.setBounds(Size.LABELXMARGIN, Size.LABELYMARGIN + 30, 220, 30);
        pwTextField.setBounds(Size.LABELXMARGIN, Size.LABELYMARGIN + 95, 220, 30);

        add(idTextField);
        add(pwTextField);
    }

    private void initButton() {
        loginBtn = new JButton("로그인");
        signUpBtn = new JButton("회원가입");
        pwChangeBtn = new JButton("비밀번호찾기");
        JCheckBox jCheckBox = new JCheckBox();

        jCheckBox.setBounds(Size.LABELXMARGIN, Size.LABELYMARGIN + 130, 25, 30);
        loginBtn.setBounds(295, Size.LABELYMARGIN + 20, 80, 95);
        loginBtn.setBackground(Pallate.e);
        loginBtn.setForeground(Pallate.a);
        signUpBtn.setBounds(Size.LABELXMARGIN + 10, 330, 120, 50);
        signUpBtn.setBounds(Size.LABELXMARGIN + 10, 330, 120, 50);
        signUpBtn.setBackground(Pallate.e);
        signUpBtn.setForeground(Pallate.a);
        pwChangeBtn.setBounds(Size.LABELXMARGIN + 150, 330, 120, 50);
        pwChangeBtn.setBounds(Size.LABELXMARGIN + 150, 330, 120, 50);
        pwChangeBtn.setBackground(Pallate.e);
        pwChangeBtn.setForeground(Pallate.a);

//        add(jCheckBox);
        add(loginBtn);
        add(signUpBtn);
//        add(pwChangeBtn);
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
