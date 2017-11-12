package View.MainView;

import Control.MainController;
import Model.StaticModel.Pallate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by skrud on 2017-11-08.
 */
public class ProfileFrame extends JFrame {
    private static final int FRAMEWIDTH = 400;
    private static final int FRAMEHEIGHT = 480;
    private BufferedImage image;

    private JPanel contentPane;
    private MainController controller;
    public ProfileFrame(MainController controller){
        this.controller = controller;
        setSize(FRAMEWIDTH,FRAMEHEIGHT);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout());
        contentPane.setBackground(Pallate.b);
        setContentPane(contentPane);
        Graphics g = contentPane.getGraphics();
//        g.drawImage(image,0,0,contentPane);
        initLabel();
        setVisible(true);
    }
    private void initLabel(){
        JLabel id = new JLabel(controller.getMyAccount().getId());
        JLabel email = new JLabel(controller.getMyAccount().getEmail());
        JLabel date = new JLabel(controller.getMyAccount().getName());
        JLabel name = new JLabel(controller.getMyAccount().getReg_date());

        id.setSize(FRAMEWIDTH,FRAMEHEIGHT/4);
        email.setSize(FRAMEWIDTH,FRAMEHEIGHT/4);
        date.setSize(FRAMEWIDTH,FRAMEHEIGHT/4);
        name.setSize(FRAMEWIDTH,FRAMEHEIGHT/4);

        contentPane.add(id);
        contentPane.add(name);
        contentPane.add(email);
        contentPane.add(date);
    }
}
