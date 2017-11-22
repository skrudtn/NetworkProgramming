package View.MainView;

import Control.MainController;
import Model.SearchRepoModel;

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
    private ArrayList<SearchRepoPanel> resultPanels;
    private int y;
    public NoticePanel(MainController mc){
        this.mc = mc;
        resultPanels = new ArrayList<>();
        setLayout(null);
        y=0;
    }


    public void addResultPanel(ArrayList<SearchRepoModel> sdms){
        if(sdms !=null) {
            for (SearchRepoModel sdm : sdms) {
                SearchRepoPanel rp = new SearchRepoPanel(mc, sdm);
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
