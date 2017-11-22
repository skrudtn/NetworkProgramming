package View.MainView;

import Control.GUIController;
import Control.JsonController;
import Control.MainController;
import Control.NetworkController;
import Model.ClassDiagramModel.CDModel;
import Model.ClassDiagramModel.ClazzModel;
import Model.ClassDiagramModel.Association;
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

    public void resize() {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int width = (int) (screen.getWidth() - insets.left - insets.right);
        int height = (int) (screen.getHeight() - insets.top - insets.bottom);

        setBounds(0, 0, width, height);
    }

    private void initFrame() {
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        resize();
        getContentPane().setLayout(cards);
        setResizable(false);
        drawContentPanel = new DrawContentPanel(this);
        displayPanel = new DisplayContentPanel(this);
        getContentPane().add("display", displayPanel);
        getContentPane().add("draw", drawContentPanel);

        addAction();
        setVisible(true);
    }

    private void addAction() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                drawContentPanel.resizePanel();
            }
        });
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                resizeFrame(e);
            }
        });
    }

    private void resizeFrame(WindowEvent e) {
        if ((e.getNewState() & this.MAXIMIZED_BOTH) == this.MAXIMIZED_BOTH) {
            drawContentPanel.resizePanel();
        }
    }

    public CardLayout getCardLayout() {
        return cards;
    }

    public MainController getController() {
        return controller;
    }

    private void setKeyStroke(JMenu fileMenu, JMenu remoteMenu) {
        fileMenu.getItem(0).setAccelerator(
                KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
        fileMenu.getItem(1).setAccelerator(
                KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
        fileMenu.getItem(2).setAccelerator(
                KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
        fileMenu.getItem(3).setAccelerator(
                KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK));

        remoteMenu.getItem(0).setAccelerator(
                KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));

        remoteMenu.getItem(1).setAccelerator(
                KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));
        remoteMenu.getItem(2).setAccelerator(
                KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));
        remoteMenu.getItem(3).setAccelerator(
                KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK ^ InputEvent.ALT_MASK));

    }
}