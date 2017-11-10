package View.MainView;

import Control.MainController;
import Model.SearchDataModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-27.
 */
public class NoticePanel extends JPanel{
    private static final int RWIDTH = 600;
    private static final int RHEIGHT = 120;
    private MainController mc;
    private ArrayList<ResultPanel> resultPanels;
    private int y;
    public NoticePanel(MainController mc){
        this.mc = mc;
        resultPanels = new ArrayList<>();
        setLayout(null);
        y=0;
    }


    public void addResultPanel(ArrayList<SearchDataModel> sdms){
        if(sdms !=null) {
            for (SearchDataModel sdm : sdms) {
                ResultPanel rp = new ResultPanel(mc, sdm);
                rp.setBounds(0, y, RWIDTH, RHEIGHT);
                resultPanels.add(rp);
                y += RHEIGHT;
                add(rp);
            }
        }
        setPreferredSize(new Dimension(getWidth(),y));
        repaint();
    }
    public void initResultPanel(){
        y=0;
        this.removeAll();
        if(resultPanels != null) {
            resultPanels.clear();
        }
    }

}
