package View.login;

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

    private LoginFrame f;

    private JButton okBtn;
    private JButton pwCheckBtn;
    private JButton backBtn;
    private JButton idOverlapBtn;

    private JLabel idLabel;
    private JLabel pwLabel;
    private JLabel pwLabel2;

    private JTextField idTextField;
    private JPasswordField pwTextField;
    private JPasswordField pwTextField2;

    boolean pwFlag = false;
    boolean idFlag = false;

    public PWChangePanel(LoginFrame f) {
        this.f = f;
        initUI();
    }

    public void initUI() {
        setLayout(null);
        setBackground(new Color(255, 255, 236));
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
        idTextField = new JTextField();
        pwTextField = new JPasswordField();
        pwTextField2 = new JPasswordField();

        idTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN, 140, 40);
        idTextField.setFont(new Font("Serif", Font.BOLD, 15));
        pwTextField.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 50, 140, 40);
        pwTextField.setFont(new Font("Serif", Font.BOLD, 15));
        pwTextField2.setBounds(XMARGIN + pwLabel.getWidth() + 10, YMARGIN + 100, 140, 40);
        pwTextField2.setFont(new Font("Serif", Font.BOLD, 15));

        add(idTextField);
        add(pwTextField);
        add(pwTextField2);
    }

    public void initButton() {
        okBtn = new JButton("변경");
        idOverlapBtn = new JButton("ID확인");
        pwCheckBtn = new JButton("비번확인");
        backBtn = new JButton("뒤로");

        okBtn.setBounds(XMARGIN + 140, pwLabel2.getY() + 3*pwLabel2.getHeight() + 20, 140, 60);
        idOverlapBtn.setBounds(idTextField.getX() + idTextField.getWidth() + 10, idTextField.getY(), 90, 30);
        pwCheckBtn.setBounds(idOverlapBtn.getX(), pwTextField2.getY(), 90, 30);
        backBtn.setBounds(15, 15, 60, 40);

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

        if (!id.equals("") && !pw.equals("") && !pw2.equals("")&& pwFlag && idFlag) {
            f.getController().getLoginController().pwChange(id, pw);
            f.getController().getGUIController().loginView();
        }
    }

    private void onPressedIdBtn() {
        String id = idTextField.getText();
        if (!id.equals("")) {
            f.getController().getLoginController().pwcOverlap(id);
        }
    }

    private void onPressedPWBtn() {
        String pw = String.valueOf(pwTextField.getPassword());
        String pw2 = String.valueOf(pwTextField2.getPassword());
        if (pw.equals(pw2)) {
            pwFlag = true;
        }
    }

    private void onPressedBackBtn() {
        idTextField.setText("");
        pwTextField.setText("");
        pwTextField2.setText("");
        f.getController().getGUIController().loginView();
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

}
