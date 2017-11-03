package View.MainView;

import Control.GUIController;
import Control.MainController;
import Model.CDModel;
import Model.ClazzModel;
import Model.Association;
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
    private String cdName;
    private String authority;
    private JPanel coordPanel;

    public DrawPanel(MainController controller) {
        this.controller = controller;
        gc = controller.getGUIController();
        czList = new ArrayList<>(0);
        acList = new ArrayList<>(0);
        cdName = controller.getCdModel().getCdName();
        authority = "";
        initUI();
    }

    private void initUI() {
        setBackground(new Color(255, 255, 225));

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
                        System.out.println(isClicked[0]);
                        System.out.println(pressClazz[0]);
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
                                mP[0] = e.getPoint();
                            }
                        }
                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                super.mouseReleased(e);
                isDragged[0] = false;
                pressClazz[0] = null;
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
                    System.out.println("sP " + sP);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.getSource() == coordPanel) {
                    eP = e.getPoint();
                    System.out.println("eP " + eP);
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

    public void removeClassPanel(Clazz cz) {
        czList.remove(cz);
        remove(cz);
        repaint();
    }

    public void removeAllClassPanel() {
        for (Clazz cz : czList) {
            remove(cz);
        }
        czList.clear();
        repaint();
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
            Clazz newCz = new Clazz((int) p.getX(), (int) p.getY());
            czList.add(newCz);
            add(newCz);
            repaint();
        }
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
            if (isCollide(cz, (int) sP.getX(), (int) sP.getY()) ||
                    (isCollide(cz, (int) eP.getX(), (int) eP.getY()))) {
                System.out.println(sP+" "+eP);
                clazzes[c] = cz;
                c++;
            }
        }
        if (c == 2) {
            Point shortCut[] = getShortCut(clazzes[0], clazzes[1]);
            Association ac = new Association(sP, eP);
            acList.add(ac);
            drawAssociation();
        }
    }

    private Point[] getShortCut(Clazz c1, Clazz c2) {
        Point points[] = null;
        c1.getX();
        c1.getY();
        c1.getWidth();
        c1.getHeight();
        c2.getX();
        c2.getY();
        c2.getWidth();
        c2.getHeight();

        return points;
    }

    private boolean isCollide(Clazz cz, int sX, int sY) {
        int x = cz.getX();
        int y = cz.getY();
        int w = cz.getWidth();
        int h = cz.getHeight();
        if (x <= sX && x + w >= sX && y <= sY && y + h >= sY) {
            return true;
        }
        return false;
    }

    public void drawAssociation() {
        Graphics g = this.getGraphics();

        for (Association ac : acList) {
            g.drawLine(ac.getsX(), ac.getsY(), ac.geteX(), ac.geteY());
        }
        repaint();
    }

    public ArrayList<Clazz> getCZList() {
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

    public void cmd2ClassPanel(CDModel cdm) {
        authority = cdm.getId();
        cdName = cdm.getCdName();
        ArrayList<ClazzModel> cms = cdm.getClazzModels();
        for (ClazzModel cm : cms) {
            Clazz c = new Clazz(cm.getX(), cm.getY());
            c.ClloneClazz(cm.getClassName(), cm.getAttributeList(), cm.getMethodList());
            czList.add(c);
        }
    }

    private void cmd2Assci(CDModel cdModel) {
        acList = cdModel.getAcList();
    }

    public ArrayList<Association> getACList() {
        return acList;
    }

    public void moveClassPanel(Clazz c, Point mP, Point eP) {
        int dx = (int) eP.getX() - (int) mP.getX();
        int dy = (int) eP.getY() - (int) mP.getY();
        c.setX(c.getX() + dx);
        c.setY(c.getY() + dy);
        c.setLocation(c.getX() + dx, c.getY() + dy);
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