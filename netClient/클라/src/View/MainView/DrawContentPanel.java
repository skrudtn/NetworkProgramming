package View.MainView;

import Control.GUIController;
import Control.MainController;
import Control.NetworkController;

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
    private final int MENUBARHEIGHT= 30;
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
        drawPanel.setBackground(new Color(254, 248, 203));
        drawPanel.setPreferredSize(new Dimension(DRAWPANELSIZE,DRAWPANELSIZE));
        jScrollPane = new JScrollPane(drawPanel);
        jScrollPane.setBounds(SIDEBARWIDTH, 0, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight()-45);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jScrollPane);
    }

    private void initMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(255, 255, 240));
        menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 0, SIDEBARWIDTH,f.getHeight());
        menuPanel.setLayout(null);
        add(menuPanel);
    }

    private void initToolBoxPanel() {
        toolBoxPanel = new JPanel();
        toolBoxPanel.setBackground(new Color(255, 239, 200));
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
        sideBarPanel.setBackground(new Color(255, 255, 240));
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

    public void action(){
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                jScrollPane.setBounds(SIDEBARWIDTH, 0, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight()-70);
                toolBoxPanel.setBounds(0, f.getHeight() - TOOLBOXHEIGHT, TOOLBOXWIDTH, TOOLBOXHEIGHT);
                sideBarPanel.setBounds(0, 0, SIDEBARWIDTH, f.getHeight());
                menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 0, SIDEBARWIDTH,f.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("ContentsPanel Key Listener");
            }
        });

        jScrollPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("scroll Key Listener");
            }
        });
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public void setDrawPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
        repaint();
    }
}
