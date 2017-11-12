package View.MainView;

import Control.GUIController;
import Control.MainController;
import Model.ClassDiagramModel.*;
import Model.StaticModel.Pallate;
import View.ClassDiagram.Clazz;
import View.ClassDiagram.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-03.
 */
public class DrawPanel extends JPanel {
    private final int DRAWPANELSIZE = 3000;
    private final int CLAZZWIDTH = 180;
    private final int CLAZZHEIGHT = 90;

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
        acList = new ArrayList<>();
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
        drawLines(g2);
    }

    private void drawLines(Graphics2D g2) {

        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int len= 30;
        for (Association ac : acList) {
            if (ac instanceof DirectAssociation) {
                DirectAssociation dac = (DirectAssociation) ac;
                ac.makeABpoint(len);
                g2.draw(new Line2D.Float(dac.getsX(), dac.getsY(), dac.geteX(), dac.geteY()));
                g2.draw(new Line2D.Float(dac.geteX(), dac.geteY(), dac.getA().x, dac.getA().y));
                g2.draw(new Line2D.Float(dac.geteX(), dac.geteY(), dac.getB().x, dac.getB().y));
            } else if(ac instanceof Generalization){
                Generalization gz = (Generalization) ac;
                gz.makeABpoint(len);
                g2.draw(new Line2D.Float(gz.getsX(), gz.getsY(), gz.getcX(), gz.getcY()));
                g2.draw(new Line2D.Float(gz.geteX(), gz.geteY(), gz.getA().x, gz.getA().y));
                g2.draw(new Line2D.Float(gz.geteX(), gz.geteY(), gz.getB().x, gz.getB().y));
                g2.draw(new Line2D.Float(gz.getA().x, gz.getA().y,gz.getB().x, gz.getB().y));
            } else if(ac instanceof Realization){
                Realization rz = (Realization) ac;
                rz.makeABpoint(len);
                g2.draw(new Line2D.Float(rz.geteX(), rz.geteY(), rz.getA().x, rz.getA().y));
                g2.draw(new Line2D.Float(rz.geteX(), rz.geteY(), rz.getB().x, rz.getB().y));
                g2.draw(new Line2D.Float(rz.getA().x, rz.getA().y,rz.getB().x, rz.getB().y));
                Stroke oriStock = g2.getStroke();
                Stroke dashed = new BasicStroke(3,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[]{9},0);
                g2.setStroke(dashed);
                g2.drawLine(rz.getsX(), rz.getsY(), rz.getcX(), rz.getcY());
                g2.setStroke(oriStock);
            } else if(ac instanceof Dependency){
                Dependency rz = (Dependency) ac;
                rz.makeABpoint(len);
                g2.draw(new Line2D.Float(rz.geteX(), rz.geteY(), rz.getA().x, rz.getA().y));
                g2.draw(new Line2D.Float(rz.geteX(), rz.geteY(), rz.getB().x, rz.getB().y));
                Stroke oldStock = g2.getStroke();
                Stroke dashed = new BasicStroke(3,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[]{9},0);
                g2.setStroke(dashed);
                g2.drawLine(rz.getsX(), rz.getsY(), rz.geteX(), rz.geteY());
                g2.setStroke(oldStock);
            }else if(ac instanceof Aggregation){
                Aggregation gz = (Aggregation) ac;
                gz.makeABpoint(len);
                g2.draw(new Line2D.Float(gz.getsX(), gz.getsY(), gz.getdX(), gz.getdY()));
                g2.draw(new Line2D.Float(gz.getdX(), gz.getdY(), gz.getA().x, gz.getA().y));
                g2.draw(new Line2D.Float(gz.getdX(), gz.getdY(),gz.getB().x, gz.getB().y));
                g2.draw(new Line2D.Float(gz.geteX(), gz.geteY(),gz.getA().x, gz.getA().y));
                g2.draw(new Line2D.Float(gz.geteX(), gz.geteY(),gz.getB().x, gz.getB().y));

            }else if(ac instanceof Composition){
                Composition gz = (Composition) ac;
                gz.makeABpoint(len);
                g2.draw(new Line2D.Float(gz.getsX(), gz.getsY(), gz.getdX(), gz.getdY()));
                Stroke oldStock = g2.getStroke();
                g2.setStroke(new BasicStroke(6));
                g2.draw(new Line2D.Float(gz.getdX(), gz.getdY(), gz.getA().x, gz.getA().y));
                g2.draw(new Line2D.Float(gz.getdX(), gz.getdY(),gz.getB().x, gz.getB().y));
                g2.draw(new Line2D.Float(gz.geteX(), gz.geteY(),gz.getA().x, gz.getA().y));
                g2.draw(new Line2D.Float(gz.geteX(), gz.geteY(),gz.getB().x, gz.getB().y));
                g2.draw(new Line2D.Float(gz.getdX(), gz.getdY(),gz.geteX(), gz.geteY()));
                g2.draw(new Line2D.Float(gz.getA().x, gz.getA().y,gz.getB().x, gz.getB().y));
                g2.setStroke(oldStock);
            } else{
                g2.draw(new Line2D.Float(ac.getsX(), ac.getsY(), ac.geteX(), ac.geteY()));
            }
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

                        System.out.println(cz.getX() + ", " + cz.getY() + ", " + cz.getWidth() + ", " + cz.getHeight());
                    }
                }
                isClicked[0] = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                sP[0] = e.getPoint();

                switch (gc.getComboMode()) {

                    case 1:
                        createClassPanel(sP[0],0);
                        break;
                    case 2: createClassPanel(sP[0],1); break;
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
                }
                gc.setComboMode(0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                isDragged[0] = false;
                if (pressClazz[0] != null) {
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
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    removeClassPanel(pressClazz[0]);
                }
                isClicked[0] = false;
            }
        });

    }

    private void createClassPanel(Point p,int flag) {
        boolean createFlag = true;
        if (!czList.isEmpty()) {
            for (Clazz cz : czList) {
                if (isCollide(cz, (int) p.getX(), (int) p.getY()) ||
                        isCollide(cz, (int) p.getX() + CLAZZWIDTH, (int) p.getY()) ||
                        isCollide(cz, (int) p.getX(), (int) p.getY() + CLAZZHEIGHT) ||
                        isCollide(cz, (int) p.getX() + CLAZZWIDTH, (int) p.getY() + CLAZZHEIGHT)) {
                    createFlag = false;
                }
            }
        }
        if (createFlag) {
            Clazz newCz=null;
            if(flag==0) {
                newCz = new Clazz(this, (int) p.getX(), (int) p.getY());
            } else if(flag ==1) {
                newCz = new Interface(this,(int)p.getX(),(int)p.getY());
            }
            czList.add(newCz);
            add(newCz);

            repaint();
        }
    }

    public void removeClassPanel(Clazz cz) {
        for(Association ac : cz.getAcList()) {
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
            if (isCollide(cz, (int) sP.getX(), (int) sP.getY())) {
                clazzes[0] = cz;
                c++;
            } else if (isCollide(cz, (int) eP.getX(), (int) eP.getY())) {
                clazzes[1] = cz;
                c++;
            }
        }
        if (c == 2) {
            Point shortCut[] = getShortCut(clazzes[0], clazzes[1]);
            Association ac = null;
            if (gc.getComboMode() == 3) {
                ac = new Association(shortCut[0], shortCut[1]);
            } else if (gc.getComboMode() == 4) {
                ac = new DirectAssociation(shortCut[0], shortCut[1]);
            } else if (gc.getComboMode() == 5) {
                ac = new Generalization(shortCut[0], shortCut[1]);
            } else if (gc.getComboMode() == 6) {
                ac = new Realization(shortCut[0], shortCut[1]);
            } else if (gc.getComboMode() == 7) {
                ac = new Dependency(shortCut[0], shortCut[1]);
            }else if (gc.getComboMode() == 8) {
                ac = new Aggregation(shortCut[0], shortCut[1]);
            }else if (gc.getComboMode() == 9) {
                ac = new Composition(shortCut[0], shortCut[1]);
            }
            clazzes[0].addAcList(ac);
            clazzes[0].addPointInClazzes(0);
            clazzes[1].addAcList(ac);
            clazzes[1].addPointInClazzes(1);
            acList.add(ac);
            repaint();
        } else {
            clazzes = null;
        }

    }

    private Point[] getShortCut(Clazz c1, Clazz c2) {
        return calcShortCut(c1.getX(), c1.getY(), c1.getWidth(), c1.getHeight(), c2.getX(), c2.getY(), c2.getWidth(), c2.getHeight());
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
        cmd2LineClass(controller.getCdModel());
        repaint();
    }

    private void cmd2ClassPanel(CDModel cdm) {
        authority = cdm.getId();
        ArrayList<ClazzModel> cms = cdm.getClazzModels();
        for (ClazzModel cm : cms) {
            Clazz c = new Clazz(this, cm.getX(), cm.getY());
            c.clloneClazz(cm.getClassName(), cm.getAttributeList(), cm.getMethodList(),cm.getAcList(),cm.getPointInClazz());
            czList.add(c);
        }
    }

    private void cmd2LineClass(CDModel cdm) {
        for(Association ac: cdm.getAcList()){
            createAssociation(new Point(ac.getsX(),ac.getsY()),new Point(ac.geteX(),ac.geteY()));
        }
    }


    private void moveClassPanel(Clazz c, Point mP, Point eP) {
        int dx = (int) eP.getX() - (int) mP.getX();
        int dy = (int) eP.getY() - (int) mP.getY();
        c.setX(dx + c.getX());
        c.setY(dy + c.getY());
        c.setLocation(dx + c.getX(), dy + c.getY());

        int i = 0;
        for(Association ac : c.getAcList()){
            if (c.getPointInClazzes().get(i) == 0) {
                ac.setsX(String.valueOf(dx + ac.getsX()));
                ac.setsY(String.valueOf(dy + ac.getsY()));
            } else {
                ac.seteX(String.valueOf(dx + ac.geteX()));
                ac.seteY(String.valueOf(dy + ac.geteY()));
            }
            i++;
        }
        repaint();
    }

    private Point[] calcShortCut(int cX1, int cY1, int cW1, int cH1, int cX2, int cY2, int cW2, int cH2) {
        Point[] points = new Point[2];
        System.out.println("cX1, cY1 : " + cX1 + ", " + cY1);
        System.out.println("cX2, cY2 : " + cX2 + ", " + cY2);

        double x1 = cX1 + cW1 / 2;
        double y1 = cY1 + cH1 / 2;
        double x2 = cX2 + cW2 / 2;
        double y2 = cY2 + cH2 / 2;

        System.out.println("x1, y1 : " + x1 + ", " + y1);
        System.out.println("x2, y2 : " + x2 + ", " + y2);
        double dx = x2 - x1;
        double dy = y2 - y1;
        double shortCutX1 = 0;
        double shortCutY1 = 0;
        double shortCutX2 = 0;
        double shortCutY2 = 0;
        if (dx == 0) {
            shortCutX1 = shortCutX2 = x1;
            shortCutY1 = y1;
            shortCutY2 = y2;
        } else if (dy == 0) {
            shortCutY1 = shortCutY2 = y1;
            shortCutX1 = x1;
            shortCutX2 = x2;
        } else {
            int flag = 0;
            if (x1 <= x2 && y1 <= y2) flag = 1;
            if (x1 <= x2 && y1 >= y2) flag = 2;
            if (x1 >= x2 && y1 <= y2) flag = 3;
            if (x1 >= x2 && y1 >= y2) flag = 4;
            System.out.println(flag);
            switch (flag) {
                case 1:
                    if (dy / dx > 1.0) { // 두 사각형의 위와 아래직선
                        shortCutY1 = cY1 + cH1;
                        shortCutX1 = getLinearX(shortCutY1, dx, dy, x1, y1);
                        shortCutY2 = cY2;
                        shortCutX2 = getLinearX(shortCutY2, dx, dy, x2, y2);
                    } else if (dy / dx < 1.0) {
                        shortCutX1 = cX1 + cW1;
                        shortCutY1 = getLinearY(shortCutX1, dx, dy, x1, y1);
                        shortCutX2 = cX2;
                        shortCutY2 = getLinearY(shortCutX2, dx, dy, x2, y2);
                    } else if (dy / dx == 1.0) {
                        shortCutX1 = cX1 + cW1;
                        shortCutY1 = cY1 + cH1;
                        shortCutX2 = cX2;
                        shortCutY2 = cY2;
                    }
                    break;

                case 2:
                    if (dy / dx < -1.0) { // y가 정해짐
                        shortCutY1 = cY1;
                        shortCutX1 = getLinearX(shortCutY1, dx, dy, x1, y1);
                        shortCutY2 = cY2 + cH2;
                        shortCutX2 = getLinearX(shortCutY2, dx, dy, x2, y2);
                    } else if (dy / dx > -1.0) { //x가 정해짐
                        shortCutX1 = cX1 + cW1;
                        shortCutY1 = getLinearY(shortCutX1, dx, dy, x1, y1);
                        shortCutX2 = cX2;
                        shortCutY2 = getLinearY(shortCutX2, dx, dy, x2, y2);
                    } else if (dy / dx == -1.0) {
                        shortCutX1 = cX1 + cW1;
                        shortCutY1 = cY1;
                        shortCutX2 = cX2;
                        shortCutY2 = cY2 + cH2;
                    }
                    break;
                case 3:
                    if (dy / dx < -1.0) { // y가 고정
                        shortCutY1 = cY1 + cH1;
                        shortCutX1 = getLinearX(shortCutY1, dx, dy, x1, y1);
                        shortCutY2 = cY2;
                        shortCutX2 = getLinearX(shortCutY2, dx, dy, x2, y2);
                    } else if (dy / dx > -1.0) { // x가 고정
                        shortCutX1 = cX1;
                        shortCutY1 = getLinearY(shortCutX1, dx, dy, x1, y1);
                        shortCutX2 = cX2 + cW2;
                        shortCutY2 = getLinearY(shortCutX2, dx, dy, x2, y2);
                    } else if (dy / dx == -1.0) {
                        shortCutX1 = cX1;
                        shortCutY1 = cY1 + cH1;
                        shortCutX2 = cX2 + cW2;
                        shortCutY2 = cY2;
                    }
                    break;
                case 4:
                    if (dy / dx > 1.0) { //y가 고정
                        shortCutY1 = cY1;
                        shortCutX1 = getLinearX(shortCutY1, dx, dy, x1, y1);
                        shortCutY2 = cY2 + cH2;
                        shortCutX2 = getLinearX(shortCutY2, dx, dy, x2, y2);
                    } else if (dy / dx < 1.0) {// x가 고정
                        shortCutX1 = cX1;
                        shortCutY1 = getLinearY(shortCutX1, dx, dy, x1, y1);
                        shortCutX2 = cX2 + cW2;
                        shortCutY2 = getLinearY(shortCutX2, dx, dy, x2, y2);
                    } else if (dy / dx == 1.0) {
                        shortCutX1 = cX1;
                        shortCutY1 = cY1;
                        shortCutX2 = cX2 + cW2;
                        shortCutY2 = cY2 + cH2;
                    }
                    break;
            }
            points[0] = new Point((int) shortCutX1, (int) shortCutY1);
            points[1] = new Point((int) shortCutX2, (int) shortCutY2);
        }

        return points;
    }

    private static int getLinearX(double y, double dx, double dy, double x1, double y1) {
        double x = 0;
        x = (dx / dy) * (y - y1) + x1;
        return (int) x;
    }
    private static int getLinearY(double x, double dx, double dy, double x1, double y1) {
        double y = 0;
        y = (dy / dx) * (x - x1) + y1;
        return (int) y;
    }

    public ArrayList<Association> getAcList() {
        return acList;
    }

    public void setAcList(ArrayList<Association> acList) {
        this.acList = acList;
    }
}