package View.MainView;

import Control.GUIController;
import Control.MainController;
import Control.NetworkController;
import Model.ClazzModel;
import View.ClassDiagram.Clazz;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-03.
 */
public class DrawContentPanel extends JPanel {

    private final int SIDEBARWIDTH = 200;
    private final int MENUBARWIDTH = 200;
    private final int TOOLBOXWIDTH = 200;
    private final int TOOLBOXHEIGHT = 400;
    private DrawFrame f;
    private JScrollPane jScrollPane;
    private JPanel menuPanel;
    private JPanel sideBarPanel;
    private JPanel toolBoxPanel;

    private DrawPanel drawPanel;
    private JComboBox<String> classComboBox;
    private MainController controller;
    private GUIController gc;
    private NetworkController nc;
    public DrawContentPanel(DrawFrame f) {
        this.f = f;
        this.controller = f.getController();
        gc = controller.getGUIController();
        nc = controller.getNetworkController();

        initUI();
    }


    private void initUI() {
        setLayout(null);
        initDrawPanel();
        initSideBarPanel();
        initMenuPanel();
        initToolBoxPanel();
        initMenuBar();
        componentListener();
    }

    private void componentListener() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                drawPanel.setBounds(SIDEBARWIDTH, 30, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight());
                toolBoxPanel.setBounds(0, f.getHeight() - TOOLBOXHEIGHT, TOOLBOXWIDTH, TOOLBOXHEIGHT);
                sideBarPanel.setBounds(0, 30, SIDEBARWIDTH, f.getHeight());
                menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 30, SIDEBARWIDTH,f.getHeight());

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
    }

    private void initDrawPanel(){
        drawPanel = new DrawPanel(controller);
        jScrollPane = new JScrollPane(drawPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        drawPanel.setLayout(null);
        drawPanel.setBackground(new Color(254, 248, 203));
        drawPanel.setBounds(0,0,3000,3000);
//        drawPanel.setBounds(SIDEBARWIDTH, 30, 3000, f.getHeight());
        jScrollPane.setBounds(SIDEBARWIDTH, 30, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight());
        add(jScrollPane);
//        add(drawPanel);
    }

    private void initMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(255, 255, 240));
        menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 30, SIDEBARWIDTH,f.getHeight());

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
        classComboBox.setBounds(0, 0, TOOLBOXWIDTH, 100);
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
        sideBarPanel.setBounds(0, 30, SIDEBARWIDTH, f.getHeight());
        System.out.println();
        sideBarPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        sideBarPanel.setLayout(null);
        add(sideBarPanel);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("파일");
        JMenu remoteMenu = new JMenu("관리");
        JMenuItem create = new JMenuItem("New");

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("생성생성");
            }
        });
        fileMenu.add(create);
        fileMenu.getItem(0).setAccelerator(
                KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileChooser(0);
                System.out.println("열자열자");
            }

        });
        fileMenu.add(open);
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileChooser(1);
                System.out.println("저장저장");
            }
        });
        fileMenu.add(save);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        fileMenu.add(exit);

        JMenuItem clone = new JMenuItem("clone");
        clone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        remoteMenu.add(clone);
        remoteMenu.getItem(0).setAccelerator(
                KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));
        JMenuItem add = new JMenuItem("add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        remoteMenu.add(add);

        JMenuItem commit = new JMenuItem("commit");
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        remoteMenu.add(commit);
        JMenuItem push = new JMenuItem("push");

        push.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Clazz> czList = drawPanel.getCZList();
                ArrayList<ClazzModel> cmList= new ArrayList<>();
//                drawPanel.getCDName()
                for(Clazz c: czList) {
                    cmList.add(new ClazzModel(c.getClazzName(), c.getAtt(), c.getMet(),c.getX(),c.getY(), c.getWidth(), c.getHeight()));
                }
                nc.push(cmList, drawPanel.getCDName()+"frist cd");
                gc.push();
            }
        });
        remoteMenu.add(push);
        remoteMenu.getItem(3).setAccelerator(
                KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));

        menuBar.add(fileMenu);
        menuBar.add(remoteMenu);
        menuBar.setBackground(new Color(216, 215, 217));
        menuBar.setBounds(0,0,f.getWidth(),30);
        add(menuBar);
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

}
