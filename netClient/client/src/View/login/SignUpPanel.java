package View.login;

import Control.GUIController;
import Model.StaticModel.Pallate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by skrud on 2017-10-02.
 */
public class SignUpPanel extends JPanel {
    private final static int XMARGIN = 50;
    private final static int YMARGIN = 80;

    private LoginFrame f;
    private GUIController gc;

    private JButton okBtn;
    private JButton idOverlapBtn;
    private JButton pwCheckBtn;
    private JButton backBtn;

    private JLabel idLabel;
    private JLabel pwLabel;
    private JLabel pwLabel2;
    private JLabel emailLabel;
    private JLabel nameLabel;
    private JLabel stateLabel;

    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JPasswordField pwTextField;
    private JPasswordField pwTextField2;

    private boolean pwFlag = false;
    private boolean idFlag= false;

    public SignUpPanel(LoginFrame f) {
        this.f = f;
        gc = f.getController().getGUIController();
        initUI();
    }

    public void initUI() {
        setLayout(null);
        setBackground(Pallate.b);
        setVisible(true);
        initLabel();
        initTextField();
        initButton();
        initActionListener();
    }

    private void initLabel() {
        idLabel = new JLabel("아이디\t: ");
        pwLabel = new JLabel("비밀번호\t: ");
        pwLabel2 = new JLabel("재입력\t: ");
        nameLabel = new JLabel("이름\t: ");
        emailLabel = new JLabel("이메일\t: ");
        stateLabel = new JLabel();

        idLabel.setBounds(XMARGIN, YMARGIN, 80, 40);
        idLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel.setBounds(XMARGIN, YMARGIN + 50, 80, 40);
        pwLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel2.setBounds(XMARGIN, YMARGIN + 100, 80, 40);
        pwLabel2.setFont(new Font("Serif", Font.BOLD, 15));
        nameLabel.setBounds(XMARGIN, YMARGIN + 150, 80, 40);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 15));
        emailLabel.setBounds(XMARGIN, YMARGIN + 200, 80, 40);
        emailLabel.setFont(new Font("Serif", Font.BOLD, 15));
        stateLabel.setBounds(XMARGIN*2,YMARGIN/2, 120,20);
        stateLabel.setFont(new Font("Serif", Font.BOLD, 14));

        add(idLabel);
        add(pwLabel);
        add(pwLabel2);
        add(nameLabel);
        add(emailLabel);
        add(stateLabel);
    }

    private void initTextField() {
        idTextField = new JTextField();
        pwTextField = new JPasswordField();
        pwTextField2 = new JPasswordField();
        nameTextField = new JTextField();
        emailTextField = new JTextField();

        idTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN, 140, 40);
        idTextField.setFont(new Font("Serif", Font.BOLD, 15));
        pwTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 50, 140, 40);
        pwTextField.setFont(new Font("Serif", Font.BOLD, 15));
        pwTextField2.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 100, 140, 40);
        pwTextField2.setFont(new Font("Serif", Font.BOLD, 15));
        nameTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 150, 140, 40);
        nameTextField.setFont(new Font("Serif", Font.BOLD, 15));
        emailTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 200, 140, 40);
        emailTextField.setFont(new Font("Serif", Font.BOLD, 15));

        add(idTextField);
        add(pwTextField);
        add(pwTextField2);
        add(nameTextField);
        add(emailTextField);
    }

    private void initButton() {
        okBtn = new JButton("가입하기");
        idOverlapBtn = new JButton("중복확인");
        pwCheckBtn = new JButton("비번확인");
        backBtn = new JButton("뒤로");

        okBtn.setBounds(XMARGIN + 140, emailLabel.getY()+emailLabel.getHeight()+20, 140, 60);
        okBtn.setBackground(Pallate.e);
        okBtn.setForeground(Pallate.a);
        idOverlapBtn.setBounds(idTextField.getX() + idTextField.getWidth() + 10, idTextField.getY(), 90, 30);
        idOverlapBtn.setBackground(Pallate.e);
        idOverlapBtn.setForeground(Pallate.a);
        pwCheckBtn.setBounds(idOverlapBtn.getX(), pwTextField2.getY(), 90, 30);
        pwCheckBtn.setBackground(Pallate.e);
        pwCheckBtn.setForeground(Pallate.a);
        backBtn.setBounds(15, 15, 60, 40);
        backBtn.setBackground(Pallate.e);
        backBtn.setForeground(Pallate.a);

        add(okBtn);
        add(idOverlapBtn);
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
        idOverlapBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == idOverlapBtn) {
                    onPressedIdBtn();
                }
            }
        });
        idOverlapBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onPressedIdBtn();
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
        String id = idTextField.getText();
        String pw = getSHA256(String.valueOf(pwTextField.getPassword()));
        String pw2 = getSHA256(String.valueOf(pwTextField2.getPassword()));
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        if (!id.equals("") && !pw.equals("") && !pw2.equals("")
                && !name.equals("") && !email.equals("") && pwFlag && idFlag) {
            f.getController().getLoginController().signup(id, pw, name, email);
            gc.loginView();
        }
    }

    private void onPressedIdBtn() {

        String id = idTextField.getText();
        if (!id.equals("")) {
            f.getController().getLoginController().suOverlap(id);
        }
    }

    private void onPressedPWBtn(){
        String pw = String.valueOf(pwTextField.getPassword());
        String pw2 = String.valueOf(pwTextField2.getPassword());
        if (pw.equals(pw2)) {
            pwFlag = true;
            stateLabel.setText("Same PW");
        }
    }
    private void onPressedBackBtn(){
        idTextField.setText("");
        pwTextField.setText("");
        pwTextField2.setText("");
        nameTextField.setText("");
        emailTextField.setText("");
        stateLabel.setText("");
        gc.loginView();
    }

    public String getSHA256(String str) {
        String SHA = "";
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            int byteLength = byteData.length;
            StringBuffer sb = new StringBuffer();
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

    public JLabel getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(JLabel stateLabel) {
        this.stateLabel = stateLabel;
    }
}