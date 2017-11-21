package View.MainView;

import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by skrud on 2017-11-20.
 */
public class AuthorityPanel extends JPanel {
    AuthorityPanel(){

        setBounds(0, 0, Size.DRAWPANELSIZE, Size.DRAWPANELSIZE);
        setBackground(new Color(0, 0, 0, 0));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(e.getPoint());
            }
        });
    }
}
