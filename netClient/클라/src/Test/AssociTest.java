package Test;

import Model.Association;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-31.
 */
public class AssociTest extends JFrame {
    Point sP;
    Point eP;
    Panel2 p2 = new Panel2();
    ArrayList<Association> list = new ArrayList<>();
    public AssociTest(){
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100,100,200,200);

        this.setContentPane(p2);
        this.setVisible(true);


    }

    class Panel2 extends JPanel{
        Panel2(){
            setBounds(100,100,100,100);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    pressedAction(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    releasedAction(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for(Association a: list) {
                g.drawLine(a.getsX(), a.getsY(), a.geteX(), a.geteY());
            }
        }
    }

    private void pressedAction(MouseEvent e) {
        sP = e.getPoint();
    }
    private void releasedAction(MouseEvent e) {
        eP = e.getPoint();
        list.add(new Association(sP,eP));
        repaint();
    }

    public static void main(String[] args) {
        new AssociTest();
    }
}
