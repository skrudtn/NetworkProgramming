package View.MainView;

import Control.MainController;
import Model.SearchRepoModel;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Size;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by skrud on 2017-10-27.
 */

public class SearchRepoPanel extends JPanel {
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JButton deleteBtn;

    private MainController controller;
    private String title;
    private String id;
    private String date;
    private String repoNo;

    SearchRepoPanel(MainController controller, SearchRepoModel sdm){
        this.controller = controller;
        imageLabel = new JLabel(MyImage.btn_repository);
        titleLabel = new JLabel();
        idLabel = new JLabel();
        deleteBtn = new JButton(MyImage.btn_delete);
        titleLabel.setForeground(Color.BLACK);
        idLabel.setForeground(Color.BLACK);
//        this.setOpaque(false);
//        this.setBorder(new );
        this.title = sdm.getTitle();
        this.id=sdm.getId();
        this.date = sdm.getDate();
        date =date.substring(0,16);
        repoNo = sdm.getRepoNo();
        setBorder(new EmptyBorder(0,0,0,0));
        setLayout(null);

        imageLabel.setBounds(50,20,Size.BTN_REPO_W,Size.BTN_REPO_H);
        titleLabel.setBounds(Size.RHEIGHT+80,0,Size.RWIDTH-(Size.RHEIGHT*3),Size.RHEIGHT/2);
        idLabel.setBounds(titleLabel.getX(),titleLabel.getHeight(),Size.RWIDTH-(Size.RHEIGHT*2),Size.RHEIGHT/2);
        deleteBtn.setBounds(titleLabel.getX()+titleLabel.getWidth()+30,20,Size.BTN_DELETE,Size.BTN_DELETE);



        titleLabel.setText(title+"  ");
        titleLabel.setFont(MyFont.serif30);
        idLabel.setText(id+"  |  "+date);
        idLabel.setFont(MyFont.serif23);

        deleteBtn.setBorderPainted(false);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setContentAreaFilled(false);

        add(imageLabel);
        add(titleLabel);
        add(idLabel);
        if(id.equals(controller.getLoginController().getMyAccount().getId())) {
            add(deleteBtn);
        }
//        titleLabel.add(deleteBtn);

        action();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.repoBgImage.getImage(), 0, 0, null);
    }
    private void action(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource()==this){
                    repoAction();
                }
            }
        });
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource()==imageLabel){
                    repoAction();
                }
            }
        });
        idLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == idLabel){
                    repoAction();
                }
            }
        });
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == titleLabel){
                    repoAction();
                }
            }
        });
        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == deleteBtn){
                    onClickedDelete();
                }
            }
        });


    }
    private void repoAction(){
        String str = controller.getJsonController().getRepoDataStr(title,id,repoNo);
        controller.getNetworkController().sendStr(str);
    }

    private void onClickedDelete(){
        System.out.println("onClickedDelete");
        controller.getGUIController().deleteView(repoNo);
    }

}
