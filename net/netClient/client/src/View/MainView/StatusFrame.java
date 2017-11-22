package View.MainView;

import Control.EventComparator;
import Control.MainController;
import Model.*;
import Model.Event;
import Model.StaticModel.MyFont;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;
import org.json.simple.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by skrud on 2017-11-22.
 */
public class StatusFrame extends JFrame {
    private MainController controller;
    private JScrollPane jScrollPane;
    private JPanel jPanel;
    private ArrayList<JLabel> jLabels;
    private int y;
    public StatusFrame(MainController controller){
        y=0;
        this.controller = controller;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(Size.FRAMEWIDTH, Size.FRAMEHEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        this.setTitle("Status Window");
        jPanel = new JPanel();
        jPanel.setBackground(Color.WHITE);
        jLabels = new ArrayList<>();
        jScrollPane = new JScrollPane(jPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(jScrollPane);
        this.setVisible(true);
    }

    public void setStateFrame() {
        ArrayList<Event> events = controller.getEventsController().getEvents();
        EventComparator comp = new EventComparator();
        events.sort(comp);
        for (Event e :events) {
            String str = "";
            if(e.getType().equals("friend")) str = "Friend request came from "+e.getSrc();
            else if(e.getType().equals("member")) str = "Project "+e.getData()+" proposal came from "+e.getSrc();
            JLabel jLabel = new JLabel(str);
            jLabel.setForeground(Pallate.a);
            jLabel.setForeground(Pallate.e);
            jLabel.setFont(MyFont.serif25);
            jLabel.setBounds(0, y, Size.FRAMEWIDTH, Size.STATUSH);
            jLabels.add(jLabel);
            y += Size.STATUSH;
            jPanel.add(jLabel);
        }
        setPreferredSize(new Dimension(getWidth(),y));
        repaint();
    }
    public void initLabels(){
        y=0;
        this.removeAll();
        if(jLabels != null) {
            jLabels.clear();
        }
    }
}
