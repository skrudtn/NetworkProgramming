package View.MainView;

import Control.MainController;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Size;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by skrud on 2017-11-28.
 */
public class RepoDeleteFrame extends JFrame{
    private MainController controller;
    private JPanel jPanel;
    private JLabel jLabel;
    private JButton ok;
    private JButton rej;
    private String repoNo;
    public RepoDeleteFrame(MainController controller,String repoNo){
        this.controller = controller;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(Size.DELETE_REPO_W, Size.DELETE_REPO_H);
        setResizable(false);
        setLocationRelativeTo(null);
        this.repoNo = repoNo;

        this.setTitle("Delete");
        jPanel = new JPanel();
        jPanel.setBackground(Color.WHITE);
        jPanel.setLayout(null);
        setContentPane(jPanel);
        jLabel = new JLabel("Delete this Repository");
        jLabel.setBounds(0,0,getWidth(),getHeight()/2);
        jLabel.setFont(MyFont.serif);
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        jPanel.add(jLabel);
        ok = new JButton(MyImage.btn_delete_ok);
        ok.setBounds(getWidth()/2-Size.DELETE_OK_W/2,getHeight()/2-10,Size.DELETE_OK_W,Size.DELETE_OK_H);
        rej = new JButton(MyImage.btn_delete_rej);
        rej.setBounds(getWidth()/2+10,getHeight()/2-10,Size.DELETE_OK_W,Size.DELETE_OK_H);

        ok.setContentAreaFilled(false);
        ok.setFocusPainted(false);
        ok.setBorderPainted(false);
        rej.setContentAreaFilled(false);
        rej.setFocusPainted(false);
        rej.setBorderPainted(false);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==ok){
                    onPressedOk();
                }
            }
        });
        rej.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==rej){
                    onPressedRej();
                }
            }
        });
        jPanel.add(ok);
//        jPanel.add(rej);
        setVisible(true);
    }
    private void onPressedOk(){
        String str = controller.getJsonController().getDeleteStr(repoNo);
        controller.getNetworkController().sendStr(str);
        this.dispose();
    }
    private void onPressedRej(){
        this.dispose();
    }
}
