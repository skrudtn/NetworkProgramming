package View.MainView;

import Control.MainController;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class DrawFrame extends JFrame {
    public DrawContentPanel getDrawContentPanel() {
        return drawContentPanel;
    }

    public DisplayContentPanel getDisplayPanel() {
        return displayPanel;
    }

    private DrawContentPanel drawContentPanel;
    private DisplayContentPanel displayPanel;
    private MainController controller;
    private CardLayout cards;

    public DrawFrame(MainController controller) {
        this.controller = controller;
        cards = new CardLayout();
        initFrame();
    }

    private void initFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int width = (int) (screen.getWidth() - insets.left - insets.right);
        int height = (int) (screen.getHeight() - insets.top - insets.bottom);
        setBounds(0, 0,width,height);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        getContentPane().setLayout(cards);

        drawContentPanel = new DrawContentPanel(this);
        displayPanel = new DisplayContentPanel(this);
        getContentPane().add("draw", drawContentPanel);
        getContentPane().add("display", displayPanel);
        setVisible(true);
    }

    public CardLayout getCardLayout() {
        return cards;
    }
    public MainController getController() {
        return controller;
    }

}