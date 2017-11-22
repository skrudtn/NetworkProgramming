package View.ClassDiagram;

import Model.ClassDiagramModel.Association;
import Model.StaticModel.MyFont;
import Model.StaticModel.Pallate;
import View.MainView.DrawPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-09-13.
 */
public class Clazz extends JPanel {
    protected final int TEXTHEIGHT = 30;
    protected final int WIDTH = 140;

    protected int x, y;
    protected int attY=TEXTHEIGHT;
    protected int metY=TEXTHEIGHT;

    protected JLabel nameLabel;
    protected JLabel attLabel;
    protected JLabel metLabel;
    protected JTextField nameTextField;
    protected JTextField attTextField;
    protected JButton attBtn;
    protected JButton metBtn;
    protected ArrayList<JTextField > aList;
    protected ArrayList<JTextField> mList;
    protected ArrayList<Association> acList;

    protected ArrayList<Integer> pointInClazzes;
    protected DrawPanel dp;
    public Clazz(DrawPanel dp,int mousex, int mousey) {
        this.dp = dp;
        this.x = mousex;
        this.y = mousey;
        acList = new ArrayList<>();
        mList = new ArrayList<>(1);
        aList = new ArrayList<>(1);
        pointInClazzes = new ArrayList<>();
        initUI();
    }

    public void clloneClazz(String clName, ArrayList<String> a, ArrayList<String> m, ArrayList<Association> acList, ArrayList<Integer> pointInClazzes){
        nameTextField.setText(clName);
        this.acList = acList;
        this.pointInClazzes = pointInClazzes;
        for(int i =0; i<a.size()-1; i++){
            reSizeClassPanel(2);
        }
        for(int i=0; i<m.size()-1;i++ ){
            reSizeClassPanel(3);
        }
        for(int i=0; i<a.size(); i++){
            aList.get(i).setText(a.get(i));
        }
        for(int i=0; i<m.size(); i++){
            mList.get(i).setText(m.get(i));
        }

    }

    public void initUI() {
        this.setBounds(x, y, WIDTH+TEXTHEIGHT, 3*TEXTHEIGHT);
        this.setOpaque(true);
        this.setBackground(Pallate.a);
        this.setLayout(null);
        initLabel();
        initTextField();
        initBtn();
        addAction();
    }
    protected void initBtn() {
        attBtn = getImageBtn();
        metBtn = getImageBtn();
        attBtn.setBounds(WIDTH, attLabel.getY(), TEXTHEIGHT,TEXTHEIGHT);
        attBtn.setFont(new Font("Serif", Font.BOLD,18));
        metBtn.setBounds(WIDTH, metLabel.getY(), TEXTHEIGHT,TEXTHEIGHT);
        metBtn.setFont(new Font("Serif", Font.BOLD,18));
        add(attBtn);
        add(metBtn);
    }

    protected void initLabel(){
        nameLabel = new JLabel();
        attLabel = new JLabel();
        metLabel = new JLabel();

        nameLabel.setBounds(0,0,WIDTH, TEXTHEIGHT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBorder(new LineBorder(Color.black,1));
        attLabel.setBounds(0,nameLabel.getY()+nameLabel.getHeight(), WIDTH, TEXTHEIGHT);
        attLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attLabel.setBorder(new LineBorder(Color.black,1));
        metLabel.setBounds(0,attLabel.getY()+attLabel.getHeight(),WIDTH,TEXTHEIGHT);
        metLabel.setHorizontalAlignment(SwingConstants.CENTER);
        metLabel.setBorder(new LineBorder(Color.black,1));

        add(nameLabel);
        add(attLabel);
        add(metLabel);
    }


    protected void initTextField() {
        nameTextField = new JTextField("name"){
            public void setBorder(Border border){}
        };
        attTextField = new JTextField("attribute") {
            public void setBorder(Border border) {
            }
        };
        JTextField metTextField = new JTextField("method") {
            public void setBorder(Border border) {
            }
        };

        nameTextField.setEnabled(true);
        nameTextField.setBounds(1,1 , WIDTH-2, TEXTHEIGHT-2);
        nameTextField.setFont(MyFont.serif);
        nameLabel.add(nameTextField);

        attTextField.setEnabled(true);
        attTextField.setBounds(1, 1, WIDTH-2, TEXTHEIGHT-2);
        attTextField.setFont(MyFont.serif);
        attLabel.add(attTextField);
        aList.add(attTextField);

        metTextField.setBounds(1, 1, WIDTH-2, TEXTHEIGHT-2);
        metTextField.setEnabled(true);
        metTextField.setFont(MyFont.serif);
        metLabel.add(metTextField);
        mList.add(metTextField);
    }

    protected void addAction() {
        attBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == attBtn){
                    reSizeClassPanel(2);
                }
            }
        });
        metBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == metBtn){
                    reSizeClassPanel(3);
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_DELETE){
                    delete();
                }
            }
        });
    }
    protected void delete(){
        dp.removeClassPanel(this);
    }
    protected void reSizeClassPanel(int flag) {
        if (flag == 2) {
            this.setBounds(x, y, WIDTH+TEXTHEIGHT, this.getHeight() + TEXTHEIGHT);
            JTextField newT = new JTextField("attribute"){
              public void setBorder(Border border){}
            };
            newT.setBounds(1,attY+1, WIDTH-2,TEXTHEIGHT-2);
            newT.setFont(MyFont.serif);
            aList.add(newT);

            attY += TEXTHEIGHT;
            attLabel.add(newT);
            attLabel.setBounds(attLabel.getX(), attLabel.getY(), attLabel.getWidth(), attLabel.getHeight()+TEXTHEIGHT);

            metLabel.setBounds(0,attY+TEXTHEIGHT,WIDTH,metLabel.getHeight());
            metBtn.setBounds(WIDTH,attY+TEXTHEIGHT,TEXTHEIGHT,TEXTHEIGHT);
            repaint();
        } else if (flag == 3) {
            this.setBounds(x, y, WIDTH+TEXTHEIGHT, this.getHeight() + TEXTHEIGHT);
            JTextField newT = new JTextField("method"){
                public void setBorder(Border border){}
            };
            newT.setBounds(1,metY+1,WIDTH-2,TEXTHEIGHT-2);
            newT.setFont(MyFont.serif);
            metY += TEXTHEIGHT;
            metLabel.setBounds(metLabel.getX(), metLabel.getY(), metLabel.getWidth(), metLabel.getHeight()+TEXTHEIGHT);
            mList.add(newT);
            metLabel.add(newT);
            repaint();
        }

    }
    public String getClazzName(){
        return nameTextField.getText();
    }
    public ArrayList<String> getAtt(){
        ArrayList<String> rt = new ArrayList<>(0);
        for(JTextField a: aList){
            rt.add(a.getText());
        }
        return rt;
    }

    public ArrayList<String> getMet(){
        ArrayList<String> rt = new ArrayList<>(0);
        for(JTextField m: mList){
            rt.add(m.getText());
        }
        return rt;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        repaint();
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        repaint();
    }

    protected JButton getImageBtn(){
        ImageIcon icon = new ImageIcon("client\\Image\\plusBtn.png");
        Image image = icon.getImage();
        Image changeImage = image.getScaledInstance(TEXTHEIGHT,TEXTHEIGHT,Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(changeImage));
        btn.setBackground(Pallate.a);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        return btn;
    }

    public ArrayList<Integer> getPointInClazzes() {
        return pointInClazzes;
    }

    public void setPointInClazzes(ArrayList<Integer> acPointInClazzList) {
        this.pointInClazzes= acPointInClazzList;
    }
    public void addPointInClazzes(int i){
        this.pointInClazzes.add(i);
    }
    public void rmPointInClazzes(int i){
        this.pointInClazzes.remove(i);
    }


    public ArrayList<Association> getAcList() {
        return acList;
    }

    public void setAcList(ArrayList<Association> acList) {
        this.acList = acList;
    }

    public void addAcList(Association ac) {
        this.acList.add(ac);
    }
    public void rmAcList(Association ac){
        this.acList.remove(ac);
    }
}
