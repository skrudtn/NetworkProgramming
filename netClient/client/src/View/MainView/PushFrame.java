package View.MainView;

import Model.StaticModel.Pallate;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-10.
 */
public class PushFrame extends JFrame {
    private static final int FRAMEWIDTH = 300;
    private static final int FRAMEHEIGHT = 100;
    private JLabel jLabel;
    public PushFrame(String msg){
        setSize(FRAMEWIDTH,FRAMEHEIGHT);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout());
        contentPane.setBackground(Pallate.e);
        setContentPane(contentPane);
        jLabel = new JLabel(msg);
        jLabel.setBackground(Pallate.e);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Serif", Font.BOLD, 15));
        contentPane.add(jLabel);
        setVisible(true);
    }
}
