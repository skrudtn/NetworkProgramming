package View.ClassDiagram;

import Control.MainController;
import Model.Association;
import Model.ClazzModel;
import Model.Pallate;
import View.MainView.DrawPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by skrud on 2017-09-13.
 */
public class Clazz extends JPanel {
    private final int TEXTHEIGHT = 30;
    private final int WIDTH = 180;

    private int x, y;
    private int attY=TEXTHEIGHT;
    private int metY=TEXTHEIGHT;

    private JLabel nameLabel;
    private JLabel attLabel;
    private JLabel metLabel;

    private JTextField nameTextField;
    private JButton attBtn;
    private JButton metBtn;
    private ArrayList<JTextField > aList;
    private ArrayList<JTextField> mList;
    private ArrayList<Association> acList;
    private ArrayList<Integer> pointInClazzes;
    private DrawPanel dp;
    public Clazz(DrawPanel dp,int mousex, int mousey) {
        this.dp = dp;
        this.x = mousex;
        this.y = mousey;
        acList = new ArrayList<>();
        this.acList = new ArrayList<>();
        mList = new ArrayList<>(1);
        aList = new ArrayList<>(1);
        pointInClazzes = new ArrayList<>();
        initUI();
    }

    public void ClloneClazz(String clName, ArrayList<String> a, ArrayList<String> m, ArrayList<Association> acList, ArrayList<Integer> pointInClazzes){
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

    private void initUI() {
        this.setBounds(x, y, WIDTH+TEXTHEIGHT, 3*TEXTHEIGHT);
        this.setOpaque(true);
        this.setBackground(Pallate.a);
        this.setLayout(null);
        initLabel();
        initTextField();
        initBtn();
        addAction();
    }
    private void initBtn() {
        attBtn = new JButton("+");
        metBtn = new JButton("+");
        attBtn.setBounds(WIDTH, TEXTHEIGHT, TEXTHEIGHT,TEXTHEIGHT);
        attBtn.setFont(new Font("Serif", Font.BOLD,18));
        metBtn.setBounds(WIDTH, 2*TEXTHEIGHT, TEXTHEIGHT,TEXTHEIGHT);
        metBtn.setFont(new Font("Serif", Font.BOLD,18));
        add(attBtn);
        add(metBtn);
    }

    private void initLabel(){
        nameLabel = new JLabel();
        attLabel = new JLabel();
        metLabel = new JLabel();

        nameLabel.setBounds(0,0,WIDTH, TEXTHEIGHT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBorder(new LineBorder(Color.black,1));
        attLabel.setBounds(0,TEXTHEIGHT, WIDTH, TEXTHEIGHT);
        attLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attLabel.setBorder(new LineBorder(Color.black,1));
        metLabel.setBounds(0,2*TEXTHEIGHT,WIDTH,TEXTHEIGHT);
        metLabel.setHorizontalAlignment(SwingConstants.CENTER);
        metLabel.setBorder(new LineBorder(Color.black,1));

        add(nameLabel);
        add(attLabel);
        add(metLabel);
    }


    private void initTextField() {
        nameTextField = new JTextField(){
            public void setBorder(Border border){}
        };
        JTextField attTextField = new JTextField() {
            public void setBorder(Border border) {
            }
        };
        JTextField metTextField = new JTextField() {
            public void setBorder(Border border) {
            }
        };

        nameTextField.setEnabled(true);
        nameTextField.setBounds(1,1 , WIDTH-2, TEXTHEIGHT-2);
        nameLabel.add(nameTextField);

        attTextField.setEnabled(true);
        attTextField.setBounds(1, 1, WIDTH-2, TEXTHEIGHT-2);
        attLabel.add(attTextField);
        aList.add(attTextField);

        metTextField.setBounds(1, 1, WIDTH-2, TEXTHEIGHT-2);
        metTextField.setEnabled(true);
        metLabel.add(metTextField);
        mList.add(metTextField);
    }

    private void addAction() {
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
                    System.out.println(this.getClass().getName());
                    System.out.println("delete");
                    delete();
                }
            }
        });
    }
    private void delete(){
        dp.removeClassPanel(this);
    }
    private void reSizeClassPanel(int flag) {
        if (flag == 2) {
            this.setBounds(x, y, WIDTH+TEXTHEIGHT, this.getHeight() + TEXTHEIGHT);
            JTextField newT = new JTextField(){
              public void setBorder(Border border){}
            };
            newT.setBounds(1,attY+1, WIDTH-2,TEXTHEIGHT-2);
            aList.add(newT);

            attY += TEXTHEIGHT;
            attLabel.add(newT);
            attLabel.setBounds(attLabel.getX(), attLabel.getY(), attLabel.getWidth(), attLabel.getHeight()+TEXTHEIGHT);

            metLabel.setBounds(0,attY+TEXTHEIGHT,WIDTH,metLabel.getHeight());
            metBtn.setBounds(WIDTH,attY+TEXTHEIGHT,TEXTHEIGHT,TEXTHEIGHT);
            repaint();
        } else if (flag == 3) {
            this.setBounds(x, y, WIDTH+TEXTHEIGHT, this.getHeight() + TEXTHEIGHT);
            JTextField newT = new JTextField(){
                public void setBorder(Border border){}
            };
            newT.setBounds(1,metY+1,WIDTH-2,TEXTHEIGHT-2);
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

    public ArrayList<Association> getAcList() {
        return acList;
    }

    public void setAcList(ArrayList<Association> acList) {
        this.acList = acList;
    }
    public void addAcList(Association ac){
        this.acList.add(ac);
    }
    public void rmAcList(Association ac){
        acList.remove(ac);
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
}
