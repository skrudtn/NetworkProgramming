package View.MainView;

import Control.MainController;
import Model.Event;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by skrud on 2017-11-23.
 */
public class StatusPanel extends JPanel {
    private Event event;
    private JButton okBtn;
    private JButton rejBtn;
    private MainController controller;
    private StatusFrame f;
    StatusPanel(MainController controller, Event e, StatusFrame f){
        this.f=f;
        this.controller = controller;
        this.event = e;
        String str = "";
        if(e.getType().equals("friend")) str = "Friend request came from "+e.getSrc();
        else if(e.getType().equals("member")) str = "Project "+e.getData()+" proposal came from "+e.getSrc();

        JLabel jLabel = new JLabel(str);
        jLabel.setBackground(Color.white);
        jLabel.setFont(MyFont.serif20);
        jLabel.setBounds(0, 0, Size.LOGIN_FRAME_W-(Size.STATUSBTN*2), Size.STATUSH);
        okBtn = new JButton(MyImage.btn_status_ok);
        rejBtn = new JButton(MyImage.btn_status_rej);
        okBtn.setBounds(Size.LOGIN_FRAME_W-(Size.STATUSBTN*2),0,Size.STATUSBTN,Size.STATUSH);
        rejBtn.setBounds(Size.LOGIN_FRAME_W-(Size.STATUSBTN),0,Size.STATUSBTN,Size.STATUSH);
        okBtn.setBorderPainted(false);
        okBtn.setFocusPainted(false);
        okBtn.setContentAreaFilled(false);
        rejBtn.setBorderPainted(false);
        rejBtn.setFocusPainted(false);
        rejBtn.setContentAreaFilled(false);

        add(jLabel);
        add(okBtn);
        add(rejBtn);
        addAction();
    }

    public void addAction(){
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == okBtn){
                    JOptionPane.showMessageDialog(f,"OK", "OK",1);
                    controller.getNetworkController().sendStr(controller.getJsonController().getResFriendStr(event.getSrc(),"ok"));
                    controller.getEventsController().getEvents().remove(event);
                }
            }
        });
        rejBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == rejBtn){
                    JOptionPane.showMessageDialog(f,"X", "X",0);
                    controller.getNetworkController().sendStr(controller.getJsonController().getResFriendStr(event.getSrc(),"rej"));
                    controller.getEventsController().getEvents().remove(event);
                }
            }
        });
    }

}
