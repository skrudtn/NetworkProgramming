package View.MainView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-10-07.
 */
public class DisplayContentPanel extends JPanel {
    private DrawFrame f = null;
    public DisplayContentPanel(DrawFrame f){
        this.f = f;
        initUI();
    }
    private void initUI(){
        setBackground(new Color(100,120,240));
        JPanel p = new JPanel();
        p.setBackground(new Color(100,100,0));
        p.setBounds(0,0,100,100);
        setLayout(null);
        add(p);
    }
}
