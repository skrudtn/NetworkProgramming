package Control;

import Model.Animation;
import Model.Event;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-22.
 */
public class AnimationThread implements Runnable {
    private Animation a;
    private JButton btn;
    public AnimationThread(Animation a, JButton btn){
        this.a = a;
        this.btn = btn;
        System.out.println("Animation Thread");
    }
    @Override
    public void run() {
        for(;;) {
//            System.out.println(a.isNormal());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!a.isNormal()) {
                System.out.println("try 밖");
                try {
                    System.out.println("try 안");

                    btn.setIcon(getImage("client\\Image\\actionStatus1.png", Size.HEADERHEIGHET, Size.HEADERHEIGHET - 10));
                    Thread.sleep(500);
                    btn.setIcon(getImage("client\\Image\\actionStatus2.png", Size.HEADERHEIGHET, Size.HEADERHEIGHET - 10));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private ImageIcon getImage(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image changeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(changeImage);
    }
}
