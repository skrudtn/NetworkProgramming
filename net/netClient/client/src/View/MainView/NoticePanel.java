package View.MainView;

import Control.MainController;
import Model.SearchRepoModel;
import Model.StaticModel.MyImage;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-27.
 */
public class NoticePanel extends JPanel{
    private MainController mc;
    private ArrayList<SearchRepoPanel> resultPanels;
    private int y;
    public NoticePanel(MainController mc){
        this.mc = mc;
        resultPanels = new ArrayList<>();
        setLayout(null);
        y=0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.notice_bgImage.getImage(), 0, 0, null);
    }


    public void addResultPanel(ArrayList<SearchRepoModel> sdms){
        if(sdms !=null) {
            for (SearchRepoModel sdm : sdms) {
                SearchRepoPanel rp = new SearchRepoPanel(mc, sdm);
                rp.setBounds(30, y+10, Size.RWIDTH-30, Size.RHEIGHT);
                resultPanels.add(rp);
                y += Size.RHEIGHT+10;
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
