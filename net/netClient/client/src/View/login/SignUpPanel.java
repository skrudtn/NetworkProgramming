package View.login;

import Control.GUIController;
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
 * Created by skrud on 2017-10-02.
 */
public class SignUpPanel extends JPanel {
    private LoginFrame f;
    private GUIController gc;

    private JButton okBtn;
    private JButton idOverlapBtn;
    private JButton pwCheckBtn;
    private JButton backBtn;

    private JLabel pwLabel;
    private JLabel emailLabel;

    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JPasswordField pwTextField;
    private JPasswordField pwTextField2;

    private boolean pwFlag = false;
    private boolean idFlag = false;

    SignUpPanel(LoginFrame f) {
        this.f = f;
        gc = f.getController().getGUIController();
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

    private void initLabel() {
        JLabel idLabel = new JLabel("아이디\t: ");
        pwLabel = new JLabel("비밀번호\t: ");
        JLabel pwLabel2 = new JLabel("재입력\t: ");
        JLabel nameLabel = new JLabel("이름\t: ");
        emailLabel = new JLabel("이메일\t: ");

        idLabel.setBounds(Size.SUXMARGIN, Size.SUYMARGIN, 80, 40);
        idLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel.setBounds(Size.SUXMARGIN, Size.SUYMARGIN + 50, 80, 40);
        pwLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel2.setBounds(Size.SUXMARGIN, Size.SUYMARGIN + 100, 80, 40);
        pwLabel2.setFont(new Font("Serif", Font.BOLD, 15));
        nameLabel.setBounds(Size.SUXMARGIN, Size.SUYMARGIN + 150, 80, 40);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 15));
        emailLabel.setBounds(Size.SUXMARGIN, Size.SUYMARGIN + 200, 80, 40);
        emailLabel.setFont(new Font("Serif", Font.BOLD, 15));

        add(idLabel);
        add(pwLabel);
        add(pwLabel2);
        add(nameLabel);
        add(emailLabel);
    }

    private void initTextField() {
        idTextField = new JTextField();
        pwTextField = new JPasswordField();
        pwTextField2 = new JPasswordField();
        nameTextField = new JTextField();
        emailTextField = new JTextField();

        idTextField.setBounds(Size.SUXMARGIN + pwLabel.getWidth() + 10, Size.SUYMARGIN, 140, 40);
        idTextField.setFont(new Font("Serif", Font.BOLD, 15));
        pwTextField.setBounds(Size.SUXMARGIN + pwLabel.getWidth() + 10, Size.SUYMARGIN + 50, 140, 40);
        pwTextField.setFont(new Font("Serif", Font.BOLD, 15));
        pwTextField2.setBounds(Size.SUXMARGIN + pwLabel.getWidth() + 10, Size.SUYMARGIN + 100, 140, 40);
        pwTextField2.setFont(new Font("Serif", Font.BOLD, 15));
        nameTextField.setBounds(Size.SUXMARGIN + pwLabel.getWidth() + 10, Size.SUYMARGIN + 150, 140, 40);
        nameTextField.setFont(new Font("Serif", Font.BOLD, 15));
        emailTextField.setBounds(Size.SUXMARGIN + pwLabel.getWidth() + 10, Size.SUYMARGIN + 200, 140, 40);
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

        okBtn.setBounds(Size.SUXMARGIN + 140, emailLabel.getY() + emailLabel.getHeight() + 20, 140, 60);
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

    private void onPressedPWBtn() {
        String pw = String.valueOf(pwTextField.getPassword());
        String pw2 = String.valueOf(pwTextField2.getPassword());
        if (pw.equals(pw2) && !pw.equals("")) {
            pwFlag = true;
            showOption("Same PW", -1);
        } else {
            showOption("Check the PW", 0);
        }
    }

    private void onPressedBackBtn() {
        idTextField.setText("");
        pwTextField.setText("");
        pwTextField2.setText("");
        nameTextField.setText("");
        emailTextField.setText("");
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


    public void showOption(String s, int i) {
        JOptionPane.showMessageDialog(this, s, s, i);
    }
}