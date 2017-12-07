package View.MainView;

import Control.GUIController;
import Control.MainController;
import Model.ClassDiagramModel.Association;
import Model.ClassDiagramModel.CDModel;
import Model.ClassDiagramModel.ClazzModel;
import Model.RepoModel;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;
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
    private MainFrame f;
    private JScrollPane jScrollPane;
    private JPanel menuPanel;
    private JPanel sideBarPanel;
    private JPanel toolBoxPanel;
    private DrawPanel drawPanel;
    private ArrayList<JComboBox> comboBoxes;
    private JComboBox<String> modeComboBox;
    private JComboBox<String> classComboBox;
    private JComboBox<String> sequenceComboBox;
    private MainController controller;
    private GUIController gc;
    private JButton backBtn;
    private JButton pushBtn;
    private JButton modeBtn;

    public DrawContentPanel(MainFrame f) {
        this.f = f;
        this.controller = f.getController();
        gc = controller.getGUIController();
        comboBoxes = new ArrayList<>();
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
        drawPanel = new DrawPanel(controller, this);
        drawPanel.setLayout(null);
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(Size.DRAWPANELSIZE, Size.DRAWPANELSIZE));
        jScrollPane = new JScrollPane(drawPanel);
        jScrollPane.setBounds(Size.MENUBARWIDTH, 0, f.getWidth() - (Size.MENUBARWIDTH + Size.TOOLBOXWIDTH), f.getHeight());
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jScrollPane);
    }

    private void initMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(Pallate.c);
        menuPanel.setBounds(f.getWidth() - Size.MENUBARWIDTH, 0, Size.MENUBARWIDTH, f.getHeight());
        menuPanel.setLayout(null);
        add(menuPanel);
    }

    private void initToolBoxPanel() {
        toolBoxPanel = new JPanel();
        toolBoxPanel.setBackground(Pallate.d);
        toolBoxPanel.setBorder((new BevelBorder(BevelBorder.RAISED)));
        toolBoxPanel.setBounds(0, f.getHeight() - Size.TOOLBOXHEIGHT, Size.TOOLBOXWIDTH, Size.TOOLBOXHEIGHT);
        sideBarPanel.add(toolBoxPanel);

        String modeString[] = {"ClassDiagram", "SequenceDiagram"};
        modeComboBox = new JComboBox<>(modeString);
        modeComboBox.setAlignmentX(1);
        modeComboBox.setFont(MyFont.serif20);
        String classString[] = {"Class", "Interface", "Association", "Directed Association", "Generalization",
                "Realization", "Dependency", "Aggregation", "Composition"};

        classComboBox = new JComboBox<>(classString);
        classComboBox.setAlignmentX(1);
        classComboBox.setFont(MyFont.serif18);

        String seqString[] = {"Lifeline", "Message", "Self-Message", "Async-Message", "Reply-Message"};

        sequenceComboBox = new JComboBox<>(seqString);
        sequenceComboBox.setAlignmentX(1);
        sequenceComboBox.setFont(MyFont.serif18);
        comboBoxes.add(classComboBox);
        comboBoxes.add(sequenceComboBox);

        toolBoxPanel.add(modeComboBox);
        toolBoxPanel.add(sequenceComboBox);
        toolBoxPanel.add(classComboBox);
        setDiagramMode();
    }

    private void initSideBarPanel() {
        sideBarPanel = new JPanel();
        sideBarPanel.setBackground(Pallate.c);
        sideBarPanel.setBounds(0, 0, Size.MENUBARWIDTH, f.getHeight());
        sideBarPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        sideBarPanel.setLayout(null);
        add(sideBarPanel);
    }

    private void initBtn() {
        backBtn = new JButton(MyImage.btn_back);
        pushBtn = new JButton(MyImage.btn_push);

        backBtn.setBounds(10, 10, Size.BACKBTNWIDTH, Size.BACKBTNHEIGHT);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        pushBtn.setBounds(10, 10, Size.PUSHBTNWIDTH, Size.PUSHBTNHEIGHT);
        pushBtn.setBorderPainted(false);
        pushBtn.setFocusPainted(false);
        pushBtn.setContentAreaFilled(false);

        sideBarPanel.add(backBtn);
        menuPanel.add(pushBtn);
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
        modeComboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                int i = modeComboBox.getSelectedIndex();
                setDiagramMode();
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
        CDModel cd = new CDModel(controller.getLoginController().getMyAccount().getId(), controller.getUmlController().getRepoModel().getRepoName(), cmList, acList);
        String str = controller.getJsonController().cdm2str(cd, controller.getUmlController().getRepoModel().getRepoNo());
        controller.getNetworkController().sendStr(str);
    }


    void resizePanel() {
        jScrollPane.setBounds(Size.MENUBARWIDTH, 0, f.getWidth() - (Size.MENUBARWIDTH + Size.TOOLBOXWIDTH), f.getHeight() - 34);
        toolBoxPanel.setBounds(0, f.getHeight() - Size.TOOLBOXHEIGHT, Size.TOOLBOXWIDTH, Size.TOOLBOXHEIGHT);
        sideBarPanel.setBounds(0, 0, Size.MENUBARWIDTH, f.getHeight());
        menuPanel.setBounds(f.getWidth() - Size.MENUBARWIDTH, 0, Size.MENUBARWIDTH, f.getHeight());
    }

    public JComboBox<String> getClassComboBox() {
        return classComboBox;
    }

    public JButton getPushBtn() {
        return pushBtn;
    }

    public void setDiagramMode() {
        if (modeComboBox.getSelectedIndex() ==0) {
            classComboBox.setVisible(true);
            sequenceComboBox.setVisible(false);
        } else if (modeComboBox.getSelectedIndex()== 1) {
            classComboBox.setVisible(false);
            sequenceComboBox.setVisible(true);
        }
    }
}
