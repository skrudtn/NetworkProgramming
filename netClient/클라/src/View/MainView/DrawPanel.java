package View.MainView;

import Control.GUIController;
import Control.MainController;
import View.ClassDiagram.Association;
import View.ClassDiagram.Clazz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-03.
 */
public class DrawPanel extends JPanel {
    private MainController controller;
    private GUIController gc;
    private ArrayList<Clazz> czList;
    private ArrayList<Association> acList;
    private String cdName;

    public DrawPanel(MainController controller) {
        this.controller = controller;
        gc = controller.getGUIController();
        czList = new ArrayList<>(0);
        acList = new ArrayList<>(0);
        cdName = "";
        initUI();
    }

    private void initUI() {
        setBackground(new Color(255, 255, 225));
        setBounds(100,0,500,500);
//        setBorder(new EmptyBorder(5, 5, 5, 5));
        actionLisnter();
    }

    private void actionLisnter() {
        this.addMouseListener(new MouseListener() {
            Point sP =null;
            Point eP =null;

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int comboMode = gc.getComboMode();
                switch (comboMode) {

                    case 1:
                        createClassPanel(x, y);
                        break;
//                    case 2: createIFPanel(x,y); break;
//                    case 3:
//                        createAssociation();
//                    case 4: createDAssociation(x,y);
//                    case 5: aggrregation;
//                    case 6: composition

                }
                if(comboMode != 3) gc.setComboMode(0);
                for(Clazz cz: czList) {
                    if (x>= cz.getX() && x <= cz.getX()+cz.getWidth() && y>= cz.getY() && y <= cz.getY()+cz.getHeight()){
                        System.out.println("!!!!!!!!!!!!!!!!!!!!");
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                if(gc.getComboMode() == 3) {
//                    eP = e.getPoint();
////                    createAssociation(sP, eP);
////                    drawAssociation();
//                    System.out.println("그리지");
//                    gc.setComboMode(0);
//                }
//                System.out.println(e.getPoint());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }


    public void removeClassPanel() {
        for (Clazz cz : czList) {
            remove(cz);
        }

        repaint();

    }

    private void createClassPanel(int mousex, int mousey) {
        Clazz cz = new Clazz(controller, mousex, mousey);
        czList.add(cz);
        add(cz);
        repaint();
    }

    public void drawClassPanel() {
        for (Clazz cz : czList) {
            cz.repaint();
        }
    }

    private void createAssociation(Point sP, Point eP){
        Association ac = new Association(controller, sP, eP);
        acList.add(ac);
    }
    public void drawAssociation(){
        Graphics g= this.getGraphics();

        for(Association ac: acList){
            g.drawLine(ac.getStartX(), ac.getStartY(), ac.getEndX(), ac.getEndY());
            System.out.println(ac.getStartX()+" "+ac.getStartY()+" "+ac.getEndX()+" "+ac.getEndY());
        }
        repaint();
    }


    public ArrayList<Clazz> getCZList() {
        return czList;
    }
    public String getCDName(){
        return cdName;
    }
}


/*
        drawPanel.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    System.out.format("줌줌");
                    //zoom in
                    if (e.getWheelRotation() < 0) {

                    }
                }
            }
        });
        drawPanel.setLayout(null);
    }
*/