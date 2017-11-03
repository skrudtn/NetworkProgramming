package View.MainView;

import Control.MainController;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by skrud on 2017-11-01.
 */
public class ProjectNameFrame extends JFrame{
    private static final int FRAMEWIDTH = 300;
    private static final int FRAMEHEIGHT= 100;
    private MainController controller;
    private JTextField textField;
    public ProjectNameFrame(MainController controller){
        this.controller = controller;
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        setTitle("New File");
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel conPanel = new JPanel();
        conPanel.setLayout(null);
        textField = new JTextField(10);
        textField.setBounds(60,10,100,30);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER)action();
            }
        });
        conPanel.add(textField);
        JButton btn = new JButton("Create");
        btn.setBounds(textField.getX()+textField.getWidth()+20,10,70,30);
        conPanel.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn && !textField.getText().equals("")) action();
            }
        });

        setContentPane(conPanel);
        setVisible(true);
    }
    private void action(){
        controller.getCdModel().setCdName(textField.getText());
        controller.getGUIController().drawView();
        this.dispose();
    }
}
