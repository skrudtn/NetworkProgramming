package View.MainView;

import Control.MainController;
import Model.SearchDataModel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-27.
 */
public class NoticePanel extends JPanel{
    private static final int RWIDTH = 600;
    private static final int RHEIGHT = 120;
    private static final int LABELHEIGHT = 60;
    private MainController mc;
    private JLabel nameLabel;
    private ArrayList<ResultPanel> resultPanels;
    private int y;
    public NoticePanel(MainController mc){
        this.mc = mc;
        nameLabel = new JLabel("Search Result");
        nameLabel.setBounds(0,0,getWidth(),LABELHEIGHT);
        resultPanels = new ArrayList<>();
        y=nameLabel.getHeight();
        setLayout(null);
    }
    public void addResultPanel(){
        ArrayList<SearchDataModel> sdms = mc.getSdms();
        for(SearchDataModel sdm: sdms){
            ResultPanel rp = new ResultPanel(sdm.getTitle(),sdm.getId(),sdm.getDate());
            rp.setBounds(0,y,RWIDTH,RHEIGHT);
            add(rp);
            resultPanels.add(rp);
            y+=RHEIGHT;
        }
        repaint();
    }

}
