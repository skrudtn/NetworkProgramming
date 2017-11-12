package View.MainView;

import Control.GUIController;
import Control.MainController;
import Model.ClassDiagramModel.Association;
import Model.ClassDiagramModel.CDModel;
import Model.ClassDiagramModel.ClazzModel;
import Model.StaticModel.Pallate;
import View.ClassDiagram.Clazz;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by skrud on 2017-10-03.
 */
public class DrawContentPanel extends JPanel {
    private final int SIDEBARWIDTH = 200;
    private final int MENUBARWIDTH = 200;
    private final int TOOLBOXWIDTH = 200;
    private final int TOOLBOXHEIGHT = 400;
    private final int PUSHBTNWIDTH = 140;
    private final int PUSHBTNHEIGHT = 100;
    private final int BACKBTNWIDTH = 80;
    private final int BACKBTNHEIGHT = 60;
    private final int DRAWPANELSIZE = 3000;
    private MainFrame f;
    private JScrollPane jScrollPane;
    private JPanel menuPanel;
    private JPanel sideBarPanel;
    private JPanel toolBoxPanel;

    private DrawPanel drawPanel;
    private JComboBox<String> classComboBox;
    private MainController controller;
    private GUIController gc;
    private JButton backBtn;
    private JButton pushBtn;

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
        actionListener();
    }


    private void initDrawPanel() {
        drawPanel = new DrawPanel(controller);
        drawPanel.setLayout(null);
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(DRAWPANELSIZE, DRAWPANELSIZE));
        jScrollPane = new JScrollPane(drawPanel);
        jScrollPane.setBounds(SIDEBARWIDTH, 0, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight());
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jScrollPane);
    }

    private void initMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(Pallate.c);
        menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 0, SIDEBARWIDTH, f.getHeight());
        menuPanel.setLayout(null);
        add(menuPanel);
    }

    private void initToolBoxPanel() {
        toolBoxPanel = new JPanel();
        toolBoxPanel.setBackground(Pallate.d);
        toolBoxPanel.setBorder((new BevelBorder(BevelBorder.RAISED)));
        toolBoxPanel.setBounds(0, f.getHeight() - TOOLBOXHEIGHT, TOOLBOXWIDTH, TOOLBOXHEIGHT);
        sideBarPanel.add(toolBoxPanel);

        sideBarPanel.add(toolBoxPanel);

        String classString[] = {"Class", "Interface", "Association", "Directed Association", "Generalization",
                "Realization", "Dependency", "Aggregation", "Composition"};

        classComboBox = new JComboBox<String>(classString);

        classComboBox.setAlignmentX(1);


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

    private void initBtn() {
        backBtn = new JButton(getImage("client\\Image\\backBtn.png", BACKBTNWIDTH, BACKBTNHEIGHT));
        pushBtn = new JButton(getImage("client\\Image\\pushBtn.png", PUSHBTNWIDTH, PUSHBTNHEIGHT));

        backBtn.setBounds(10, 10, BACKBTNWIDTH, BACKBTNHEIGHT);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        pushBtn.setBounds(10, 10, PUSHBTNWIDTH, PUSHBTNHEIGHT);
        pushBtn.setBorderPainted(false);
        pushBtn.setFocusPainted(false);
        pushBtn.setContentAreaFilled(false);

        sideBarPanel.add(backBtn);
        menuPanel.add(pushBtn);
    }

    private ImageIcon getImage(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image changeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(changeImage);
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }


    private void actionListener() {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backBtn) {
                    gc.displayView();
                }
            }
        });
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public synchronized void keyPressed(KeyEvent e) {
//                super.keyPressed(e);
//                if ((e.getKeyCode()==KeyEvent.VK_P) && ((e.getModifiers()&KeyEvent.CTRL_DOWN_MASK) !=0)){
//                    pushAction();
//                }
//            }
//        });
        pushBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == pushBtn) {
                    pushAction();
                }
            }
        });
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
        toolBoxPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                gc.setComboMode(0);
            }
        });
    }

    private void pushAction() {
        ArrayList<Clazz> czList = drawPanel.getCZList();
        ArrayList<Association> acList = drawPanel.getAcList();
        ArrayList<ClazzModel> cmList = new ArrayList<>();
        for (Clazz c : czList) {
            cmList.add(new ClazzModel(c.getClazzName(), c.getAtt(), c.getMet(),
                    c.getX(), c.getY(), c.getWidth(), c.getHeight(), c.getAcList(), c.getPointInClazzes()));
        }
        CDModel cd = new CDModel(controller.getMyAccount().getId(), controller.getCdModel().getCdName(), cmList, acList);
        String jsonString = controller.getJsonController().cdm2str(cd);
        controller.getNetworkController().sendStr(jsonString);
    }


    void resizePanel() {
        jScrollPane.setBounds(SIDEBARWIDTH, 0, f.getWidth() - (SIDEBARWIDTH + TOOLBOXWIDTH), f.getHeight()-34);
        toolBoxPanel.setBounds(0, f.getHeight() - TOOLBOXHEIGHT, TOOLBOXWIDTH, TOOLBOXHEIGHT);
        sideBarPanel.setBounds(0, 0, SIDEBARWIDTH, f.getHeight());
        menuPanel.setBounds(f.getWidth() - MENUBARWIDTH, 0, SIDEBARWIDTH, f.getHeight());
    }


}