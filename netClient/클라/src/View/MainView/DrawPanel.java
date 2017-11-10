package View.MainView;

import Control.GUIController;
import Control.MainController;
import Model.CDModel;
import Model.ClazzModel;
import Model.Association;
import Model.Pallate;
import View.ClassDiagram.Clazz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-03.
 */
public class DrawPanel extends JPanel {
    private final int DRAWPANELSIZE = 3000;
    private final int CLAZZWIDTH= 180;
    private final int CLAZZHEIGHT= 90;

    private MainController controller;
    private GUIController gc;
    private ArrayList<Clazz> czList;
    private ArrayList<Association> acList;
    private String authority;
    private JPanel coordPanel;

    DrawPanel(MainController controller) {
        this.controller = controller;
        gc = controller.getGUIController();
        czList = new ArrayList<>(0);
        acList = new ArrayList<>(0);
        authority = "";
        initUI();
    }

    private void initUI() {
        setBackground(Pallate.a);

        coordPanel = new JPanel();
        coordPanel.setBounds(0, 0, DRAWPANELSIZE, DRAWPANELSIZE);
        coordPanel.setBackground(new Color(0, 0, 0, 0));
        actionLisenter();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        drawClassPanel();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Association a : acList) {
            g2.draw(new Line2D.Float(a.getsX(), a.getsY(), a.geteX(), a.geteY()));
        }
    }

    private void actionLisenter() {
        final Point[] sP = {null};
        final Point[] mP = {null};
        final Point[] eP = {null};
        final boolean[] isDragged = {false};
        final boolean[] isClicked = {false};
        final Clazz[] pressClazz = {null};
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Clazz cz : czList) {
                    if (isCollide(cz, e.getX(), e.getY())) {
                        isClicked[0] = true;
                        pressClazz[0] = cz;
                        pressClazz[0].requestFocus();
                    }
                }
                isClicked[0] = false;
                System.out.println(isClicked[0]);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                sP[0] = e.getPoint();

                switch (gc.getComboMode()) {

                    case 1:
                        createClassPanel(sP[0]);
                        break;
//                    case 2: createIFPanel(x,y); break;
//                    case 4: createDAssociation(x,y);
//                    case 5: aggrregation;
//                    case 6: composition
                    default:
                        for (Clazz cz : czList) {
                            if (isCollide(cz, (int) sP[0].getX(), (int) sP[0].getY())) {
                                isDragged[0] = true;
                                pressClazz[0] = cz;
                                pressClazz[0].requestFocus();
                                mP[0] = e.getPoint();
                            }
                        }
                        break;
                } gc.setComboMode(0);
            }

            @Override
            public void mouseReleased(MouseEvent e){
                super.mouseReleased(e);
                isDragged[0] = false;
                if(pressClazz[0] != null) {
                    pressClazz[0].requestFocus(false);
                    pressClazz[0] = null;
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                eP[0] = e.getPoint();
                if (isDragged[0]) {
                    moveClassPanel(pressClazz[0], mP[0], eP[0]);
                }
                mP[0] = e.getPoint();
            }
        });
        coordPanel.addMouseListener(new MouseAdapter() {

            Point sP = null;
            Point eP = null;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getSource() == coordPanel) {
                    sP = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.getSource() == coordPanel) {
                    eP = e.getPoint();
                }
                createAssociation(sP, eP);
                rmCoordPanel();
            }
        });


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("key event");
                System.out.println(e.getKeyCode());
                if(e.getKeyCode() == KeyEvent.VK_DELETE ){
//                    removeClassPanel(pressClazz[0]);
                }
//                isClicked[0] = false;
            }
        });

    }

    private void createClassPanel(Point p) {
        boolean createFlag = true;
        if (!czList.isEmpty()) {
            for (Clazz cz : czList) {
                if (isCollide(cz, (int) p.getX(), (int) p.getY()) ||
                        isCollide(cz, (int) p.getX()+CLAZZWIDTH, (int) p.getY())||
                        isCollide(cz, (int) p.getX(), (int) p.getY()+CLAZZHEIGHT)||
                        isCollide(cz, (int) p.getX()+CLAZZWIDTH, (int) p.getY()+CLAZZHEIGHT)) {
                    createFlag = false;
                }
            }
        }
        if (createFlag) {
            Clazz newCz = new Clazz(this,(int) p.getX(), (int) p.getY());
            czList.add(newCz);
            add(newCz);
            repaint();
        }
    }

    public void removeClassPanel(Clazz cz) {
        for(Association ac: cz.getAcList()){
            acList.remove(ac);
        }
        cz.getAcList().clear();

        czList.remove(cz);
        remove(cz);
        repaint();
    }
    private void removeAllClassPanel() {
        for (Clazz cz : czList) {
            remove(cz);
        }
        czList.clear();
        repaint();
    }

    private void drawClassPanel() {
        for (Clazz c : czList) {
            add(c);
            c.repaint();
        }
    }


    private void createAssociation(Point sP, Point eP) {
        int c = 0;
        Clazz[] clazzes = new Clazz[2];
        for (Clazz cz : czList) {
            if (isCollide(cz, (int) sP.getX(), (int) sP.getY())){
                clazzes[0] = cz;
                c++;
            }
            else if(isCollide(cz, (int) eP.getX(), (int) eP.getY())) {
                clazzes[1] = cz;
                c++;
            }
        }
        if (c == 2) {
            Point shortCut[] = getShortCut(clazzes[0], clazzes[1]);
            Association ac = new Association(shortCut[0], shortCut[1]);
            clazzes[0].addAcList(ac);
            clazzes[0].addPointInClazzes(0);
            clazzes[1].addAcList(ac);
            clazzes[1].addPointInClazzes(1);
            acList.add(ac);
            repaint();
        } else{
            clazzes = null;
        }

    }
    private Point[] getShortCut(Clazz c1, Clazz c2) {
        Point points[] = new Point[2];
        Point s = new Point(c1.getX()+(c1.getWidth()/2),c1.getY()+(c1.getHeight()/2));
        Point e = new Point(c2.getX()+(c2.getWidth()/2),c2.getY()+(c2.getHeight()/2));
        points[0] = s;
        points[1] = e;
        return points;
    }

    private boolean isCollide(Clazz cz, int sX, int sY) {
        int x = cz.getX();
        int y = cz.getY();
        int w = cz.getWidth();
        int h = cz.getHeight();
        return x <= sX && x + w >= sX && y <= sY && y + h >= sY;
    }


    ArrayList<Clazz> getCZList() {
        return czList;
    }

    public void addCoordPanel() {
        add(coordPanel);
        this.setComponentZOrder(coordPanel, 0);
        coordPanel.setOpaque(false);
        repaint();
    }

    public void rmCoordPanel() {
        remove(coordPanel);
        repaint();
    }

    public void init() {
        removeAllClassPanel();
        acList.clear();
        repaint();
    }

    public void cllone() {
        init();
        cmd2ClassPanel(controller.getCdModel());
        cmd2Assci(controller.getCdModel());
        repaint();
    }

    private void cmd2ClassPanel(CDModel cdm) {
        authority = cdm.getId();
        ArrayList<ClazzModel> cms = cdm.getClazzModels();
        for (ClazzModel cm : cms) {
            Clazz c = new Clazz(this,cm.getX(), cm.getY());
            c.ClloneClazz(cm.getClassName(), cm.getAttributeList(), cm.getMethodList(),cm.getAcList(),cm.getPointInClazz());
            czList.add(c);
        }
    }

    private void cmd2Assci(CDModel cdModel) {
        for(Association ac: cdModel.getAcList()){
            createAssociation(new Point(ac.getsX(),ac.getsY()),new Point(ac.geteX(),ac.geteY()));
        }
    }

    ArrayList<Association> getACList() {
        return acList;
    }

    private void moveClassPanel(Clazz c, Point mP, Point eP) {
        int dx = (int) eP.getX() - (int) mP.getX();
        int dy = (int) eP.getY() - (int) mP.getY();
        c.setX(dx+c.getX());
        c.setY(dy+c.getY());
        c.setLocation(dx+c.getX(),dy+c.getY());

        int i=0;
        for(Association ac: c.getAcList()){
            if(c.getPointInClazzes().get(i) ==0){
                ac.setsX(String.valueOf(dx+ac.getsX()));
                ac.setsY(String.valueOf(dy+ac.getsY()));
            } else{
                ac.seteX(String.valueOf(dx+ac.geteX()));
                ac.seteY(String.valueOf(dy+ac.geteY()));
            }
            i++;
        }
        repaint();
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