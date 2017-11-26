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
 * Created by skrud on 2017-10-02.
 */
public class SignUpPanel extends JPanel {
    private LoginFrame f;
    private GUIController gc;

    private JButton okBtn;
    private JButton idOverlapBtn;
    private JButton pwCheckBtn;
    private JButton backBtn;

    private JLabel idLabel;
    private JLabel pwLabel;
    private JLabel pwLabel2;
    private JLabel nameLabel;
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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.loginBgImage.getImage(), 0, 0, null);
    }

    private void initLabel() {
        idLabel = new JLabel("ID ");
        pwLabel = new JLabel("PW ");
        pwLabel2 = new JLabel("PW2 ");
        nameLabel = new JLabel("Name ");
        emailLabel = new JLabel("E-mail ");

        idLabel.setBounds(Size.SIGNUP_XMARGIN, Size.SIGNUP_YMARGIN, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        idLabel.setFont(MyFont.serif20);
        pwLabel.setBounds(idLabel.getX(), idLabel.getY()+idLabel.getHeight()+10, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        pwLabel.setFont(MyFont.serif20);
        pwLabel2.setBounds(pwLabel.getX(), pwLabel.getY()+pwLabel.getHeight()+10, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        pwLabel2.setFont(MyFont.serif20);
        nameLabel.setBounds(pwLabel2.getX(), pwLabel2.getY()+pwLabel2.getHeight()+10, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        nameLabel.setFont(MyFont.serif20);
        emailLabel.setBounds(nameLabel.getX(), nameLabel.getY()+nameLabel.getHeight()+10, Size.SIGNUP_LABEL_W, Size.SIGNUP_LABEL_H);
        emailLabel.setFont(MyFont.serif20);

        idLabel.setForeground(Color.WHITE);
        pwLabel.setForeground(Color.WHITE);
        pwLabel2.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);

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

        idTextField.setBounds(pwLabel.getX()+ pwLabel.getWidth() + 10,idLabel.getY(), Size.SIGNUP_TEXT_W, idLabel.getHeight());
        idTextField.setFont(MyFont.serif18);
        idTextField.setOpaque(false);
        idTextField.setForeground(Color.WHITE);
        pwTextField.setBounds(idTextField.getX(), pwLabel.getY(), idTextField.getWidth(), idLabel.getHeight());
        pwTextField.setFont(MyFont.serif18);
        pwTextField.setOpaque(false);
        pwTextField.setForeground(Color.WHITE);
        pwTextField2.setBounds(idTextField.getX(), pwLabel2.getY(), idTextField.getWidth(), idLabel.getHeight());
        pwTextField2.setFont(MyFont.serif18);
        pwTextField2.setOpaque(false);
        pwTextField2.setForeground(Color.WHITE);
        nameTextField.setBounds(idTextField.getX(), nameLabel.getY(), idTextField.getWidth(), idLabel.getHeight());
        nameTextField.setFont(MyFont.serif18);
        nameTextField.setOpaque(false);
        nameTextField.setForeground(Color.WHITE);
        emailTextField.setBounds(idTextField.getX(), emailLabel.getY(), idTextField.getWidth(), idLabel.getHeight());
        emailTextField.setFont(MyFont.serif18);
        emailTextField.setOpaque(false);
        emailTextField.setForeground(Color.WHITE);
        add(idTextField);
        add(pwTextField);
        add(pwTextField2);
        add(nameTextField);
        add(emailTextField);
    }

    private void initButton() {
        okBtn = new JButton(MyImage.btn_signup_sm);
        idOverlapBtn = new JButton(MyImage.btn_check);
        pwCheckBtn = new JButton(MyImage.btn_check);
        backBtn = new JButton(MyImage.btn_cancel_sm);

        okBtn.setBounds(emailLabel.getX(), emailLabel.getY() + emailLabel.getHeight() + 40, Size.SIGNUP_SBTN_W, Size.SIGNUP_SBTN_H);
        idOverlapBtn.setBounds(idTextField.getX() + idTextField.getWidth() + 20, idTextField.getY(), Size.CHECK_BTN, Size.CHECK_BTN);
        pwCheckBtn.setBounds(idOverlapBtn.getX(), pwTextField2.getY(), Size.CHECK_BTN, Size.CHECK_BTN);
        backBtn.setBounds(okBtn.getX()+okBtn.getWidth()+30, okBtn.getY(), okBtn.getWidth(), okBtn.getHeight());

        okBtn.setBorderPainted(false);
        okBtn.setFocusPainted(false);
        okBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        idOverlapBtn.setBorderPainted(false);
        idOverlapBtn.setFocusPainted(false);
        idOverlapBtn.setContentAreaFilled(false);
        pwCheckBtn.setBorderPainted(false);
        pwCheckBtn.setFocusPainted(false);
        pwCheckBtn.setContentAreaFilled(false);

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
            showOption("PWs must match", 0);
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