package View.MainView;

import Control.GUIController;
import Control.JsonController;
import Control.MainController;
import Control.NetworkController;
import Model.CDModel;
import Model.ClazzModel;
import Model.Association;
import View.ClassDiagram.Clazz;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;


public class MainFrame extends JFrame {
    public DrawContentPanel getDrawContentPanel() {
        return drawContentPanel;
    }

    public DisplayContentPanel getDisplayPanel() {
        return displayPanel;
    }

    private DrawContentPanel drawContentPanel;
    private DisplayContentPanel displayPanel;
    private MainController controller;
    private GUIController gc;
    private JsonController jc;
    private NetworkController nc;
    private CardLayout cards;

    public MainFrame(MainController controller) {
        this.controller = controller;
        gc = controller.getGUIController();
        jc = controller.getJsonController();
        nc = controller.getNetworkController();
        cards = new CardLayout();
        initFrame();
    }

    private void initFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int width = (int) (screen.getWidth() - insets.left - insets.right);
        int height = (int) (screen.getHeight() - insets.top - insets.bottom);

        setBounds(0, 0,width,height);

        getContentPane().setLayout(cards);

        drawContentPanel = new DrawContentPanel(this);
        displayPanel = new DisplayContentPanel(this);
        getContentPane().add("display", displayPanel);
        getContentPane().add("draw", drawContentPanel);

        initMenuBar();
        setVisible(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("Frame Key Listener");
            }
        });
    }

    public CardLayout getCardLayout() {
        return cards;
    }
    public MainController getController() {
        return controller;
    }

    public void initMenuBar() {
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

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileDia(0);
                System.out.println("열자열자");
            }

        });
        fileMenu.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileDia(1);
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

        JMenuItem add = new JMenuItem("add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                gc.clone("ksna", "first cd");
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
        JMenuItem search = new JMenuItem("search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                gc.search();
            }
        });
        remoteMenu.add(search);

        JMenuItem push = new JMenuItem("push");
        push.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Clazz> czList = drawContentPanel.getDrawPanel().getCZList();
                ArrayList<Association> acList = drawContentPanel.getDrawPanel().getACList();
                ArrayList<ClazzModel> cmList= new ArrayList<>();
                for(Clazz c: czList) {
                    cmList.add(new ClazzModel(c.getClazzName(),c.getAtt(), c.getMet(),c.getX(),c.getY(), c.getWidth(), c.getHeight()));
                }
                CDModel cd = new CDModel(controller.getMyAccount().getId(), controller.getCdModel().getCdName(), cmList,acList);
                String jsonString = jc.cdm2str(cd);
                nc.sendStr(jsonString);
            }
        });
        remoteMenu.add(push);

        setKeyStroke(fileMenu, remoteMenu);
        menuBar.add(fileMenu);
        menuBar.add(remoteMenu);
        menuBar.setBackground(new Color(216, 215, 217));
        menuBar.setBounds(0,0,getWidth(),30);
        this.setJMenuBar(menuBar);
    }

    private void setKeyStroke(JMenu fileMenu, JMenu remoteMenu){
        fileMenu.getItem(0).setAccelerator(
                KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
        fileMenu.getItem(1).setAccelerator(
                KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
        fileMenu.getItem(2).setAccelerator(
                KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK ));
        fileMenu.getItem(3).setAccelerator(
                KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK ));

        remoteMenu.getItem(0).setAccelerator(
                KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));

        remoteMenu.getItem(1).setAccelerator(
                KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));
//
//        remoteMenu.getItem(2).setAccelerator(
//                KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));
        remoteMenu.getItem(3).setAccelerator(
                KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));
        remoteMenu.getItem(4).setAccelerator(
                KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));

    }
}