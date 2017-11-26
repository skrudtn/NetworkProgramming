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

/**
 * Created by skrud on 2017-11-22.
 */
public class StatusFrame extends JFrame {
    private MainController controller;
    private JScrollPane jScrollPane;
    private JPanel jPanel;
    private ArrayList<StatusPanel> statusPanels;
    private int y;
    public StatusFrame(MainController controller){
        y=0;
        this.controller = controller;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(Size.LOGIN_FRAME_W, Size.LOGIN_FRAME_W);
        setResizable(false);
        setLocationRelativeTo(null);
        this.setTitle("Status Window");
        jPanel = new JPanel();
        jPanel.setBackground(Color.WHITE);
        statusPanels= new ArrayList<>();
        jScrollPane = new JScrollPane(jPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(jScrollPane);
        this.setVisible(true);
    }

    public void setStateFrame() {
        ArrayList<Event> events = controller.getEventsController().getEvents();
        EventComparator comp = new EventComparator();
        events.sort(comp);
        for (Event e :events) {
            StatusPanel panel = new StatusPanel(controller,e,this);
            panel.setBounds(0, y, Size.LOGIN_FRAME_W, Size.STATUSH);
            statusPanels.add(panel);
            y += Size.STATUSH;
            jPanel.add(panel);
        }
        jPanel.setPreferredSize(new Dimension(getWidth(),y));
        repaint();
    }
    public void initLabels(){
        y=0;
        this.removeAll();
        if(statusPanels!= null) {
            statusPanels.clear();
        }
    }
}
