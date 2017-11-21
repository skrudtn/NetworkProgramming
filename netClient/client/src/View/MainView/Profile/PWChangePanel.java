package View.MainView.Profile;

import Model.StaticModel.Pallate;
import View.login.LoginFrame;

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

    private void initLabel() {
        JLabel idLabel = new JLabel("아이디\t: ");
        pwLabel = new JLabel("비밀번호\t: ");
        pwLabel2 = new JLabel("재입력\t: ");
        idLabel.setBounds(XMARGIN, YMARGIN, 80, 40);
        idLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel.setBounds(XMARGIN, YMARGIN + 50, 80, 40);
        pwLabel.setFont(new Font("Serif", Font.BOLD, 15));
        pwLabel2.setBounds(XMARGIN, YMARGIN + 100, 80, 40);
        pwLabel2.setFont(new Font("Serif", Font.BOLD, 15));

        add(idLabel);
        add(pwLabel);
        add(pwLabel2);
    }

    private void initTextField() {
        idTextField = new JTextField(f.getController().getLoginController().getMyAccount().getId());
        pwTextField = new JPasswordField();
        pwTextField2 = new JPasswordField();

        idTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN, 140, 40);
        idTextField.setFont(new Font("Serif", Font.BOLD, 15));
        idTextField.setEnabled(false);
        pwTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 50, 140, 40);
        pwTextField.setFont(new Font("Serif", Font.BOLD, 15));
        pwTextField2.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 100, 140, 40);
        pwTextField2.setFont(new Font("Serif", Font.BOLD, 15));

        add(idTextField);
        add(pwTextField);
        add(pwTextField2);
    }

    private void initButton() {
        okBtn = new JButton("변경");
        pwCheckBtn = new JButton("비번확인");
        backBtn = new JButton("뒤로");

        okBtn.setBounds(XMARGIN + 140, pwLabel2.getY() + 3*pwLabel2.getHeight() + 20, 140, 60);
        okBtn.setBackground(Pallate.e);
        okBtn.setForeground(Pallate.a);
        pwCheckBtn.setBounds(pwTextField2.getX()+pwTextField.getWidth()+20, pwLabel2.getY() , 90, 30);
        pwCheckBtn.setBackground(Pallate.e);
        pwCheckBtn.setForeground(Pallate.a);
        backBtn.setBounds(15, 15, 60, 40);
        backBtn.setBackground(Pallate.e);
        backBtn.setForeground(Pallate.a);

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
        f.getCards().show(f.getContentPane(),"sp");
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
