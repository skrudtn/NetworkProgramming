package View.MainView;

import Control.MainController;
import Model.SearchDataModel;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

/**
 * Created by skrud on 2017-10-27.
 */

public class ResultPanel extends JPanel {
    private static final int RWIDTH = 600;
    private static final int RHEIGHT = 120;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel dateLabel;

    private MainController controller;
    private String title;
    private String id;
    private String date;
    private String cdNo;

    public ResultPanel(MainController controller, SearchDataModel sdm){
        this.controller = controller;
        titleLabel = new JLabel();
        idLabel = new JLabel();
        dateLabel = new JLabel();

        this.title = sdm.getTitle();
        this.id=sdm.getId();
        this.date = sdm.getDate();
        this.cdNo = sdm.getCdNo();


        setBorder(new LineBorder(Color.BLACK,1));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(3,1));

        titleLabel.setBounds(0,0,RWIDTH,RHEIGHT/3);
        idLabel.setBounds(0,titleLabel.getHeight(),RWIDTH,RHEIGHT/3);
        dateLabel.setBounds(0,idLabel.getY()+idLabel.getHeight(),RWIDTH,RHEIGHT/3);

        titleLabel.setText(title+"  "+sdm.getCdNo());
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        idLabel.setText(id);
        idLabel.setFont(new Font("Serif", Font.BOLD, 15));
        dateLabel.setText(date);

        add(titleLabel);
        add(idLabel);
        add(dateLabel);

        action();
    }

    private void action(){
        idLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == idLabel){
                    cloneClick();
                }
            }
        });
        dateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == dateLabel){
                    cloneClick();
                }
            }
        });
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == titleLabel){
                    cloneClick();
                }
            }
        });

    }
    private void cloneClick(){
        String str = controller.getJsonController().getCloneStr(id,title,date,cdNo);
        controller.getNetworkController().sendStr(str);
    }

}
