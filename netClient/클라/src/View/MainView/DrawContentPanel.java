package View.MainView;

import Control.GUIController;
import Control.MainController;
import Control.NetworkController;
import Model.Pallate;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by skrud on 2017-10-03.
 */
public class DrawContentPanel extends JPanel{
    private final int SIDEBARWIDTH = 200;
    private final int MENUBARWIDTH = 200;
    private final int TOOLBOXWIDTH = 200;
    private final int TOOLBOXHEIGHT = 400;
    private final int DRAWPANELSIZE= 3000;
    private MainFrame f;
    private JScrollPane jScrollPane;
    private JPanel menuPanel;
    private JPanel sideBarPanel;
    private JPanel toolBoxPanel;

    private DrawPanel drawPanel;
    private JComboBox<String> classComboBox;
    private MainController controller;
    private GUIController gc;
    private JPanel coordPanel;
    private JButton btn;

    public DrawContentPanel(MainFrame f) {
        this.f = f;
        this.controller = f.getController();
        gc = controller.getGUIController();

        initUI();
    }


    private void initUI() {
        setLayout(null);
        initDrawPanel();
        initSideBarPanel();
        initMenuPanel();
        initToolBoxPanel();
        initBtn();
        action();
    }


    private void initDrawPanel(){
        drawPanel = new DrawPanel(controller);
        drawPanel.setLayout(null);
        drawPanel.setBackground(Pallate.a);
        drawPanel.setPreferredSize(new Dimension(DRAWPANELSIZE,DRAWPANELSIZE));
        jScrollPane = new JScrollPane(drawPanel);
        jScrollPane.setBounds(SIDEBARWIDTH, 0, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight());
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jScrollPane);
    }

    private void initMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(Pallate.c);
        menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 0, SIDEBARWIDTH,f.getHeight());
        menuPanel.setLayout(null);
        add(menuPanel);
    }

    private void initToolBoxPanel() {
        toolBoxPanel = new JPanel();
        toolBoxPanel.setBackground(Pallate.d);
        toolBoxPanel.setBorder((new BevelBorder(BevelBorder.RAISED)));
        toolBoxPanel.setBounds(0, f.getHeight() - TOOLBOXHEIGHT, TOOLBOXWIDTH, TOOLBOXHEIGHT);
        sideBarPanel.add(toolBoxPanel);
        toolBoxPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                gc.setComboMode(0);
            }
        });

        sideBarPanel.add(toolBoxPanel);

        String classString[] = {"Class", "Interface", "Association", "Directed Association", "Aggregation", "Composition"};
        classComboBox = new JComboBox<String>(classString);
        classComboBox.setAlignmentX(1);
        classComboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                int i = classComboBox.getSelectedIndex();
                gc.setComboMode(i + 1);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
        classComboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.isPopupTrigger()) {
                    System.out.println("!!!");
                }
            }
        });

        toolBoxPanel.add(classComboBox);
    }

    private void initSideBarPanel() {
        sideBarPanel = new JPanel();
        sideBarPanel.setBackground(Pallate.c);
        sideBarPanel.setBounds(0, 0, SIDEBARWIDTH, f.getHeight());
        sideBarPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        sideBarPanel.setLayout(null);
        add(sideBarPanel);
    }
    private void initBtn(){
        btn = new JButton();
        menuPanel.add(btn);
        btn.setSize(100,100);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn){
                    gc.displayView();
                }
            }
        });
    }

    private void action(){
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public void setDrawPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
        repaint();
    }
    public void resizePanel(){
        jScrollPane.setBounds(SIDEBARWIDTH, 0, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight()-70);
        toolBoxPanel.setBounds(0, f.getHeight() - TOOLBOXHEIGHT, TOOLBOXWIDTH, TOOLBOXHEIGHT);
        sideBarPanel.setBounds(0, 0, SIDEBARWIDTH, f.getHeight());
        menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 0, SIDEBARWIDTH,f.getHeight());
    }
}
