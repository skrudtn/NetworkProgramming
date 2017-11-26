package Control;

import Model.Animation;
import Model.StaticModel.MyImage;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-22.
 */
public class AnimationThread implements Runnable {
    private Animation a;
    private JButton btn;
    private ImageIcon a1;
    private ImageIcon a2;
    private ImageIcon n;
    public AnimationThread(Animation a, JButton btn){
        this.a = a;
        this.btn = btn;
        a1 = MyImage.btn_myalert1;
        a2 = MyImage.btn_myalert2;
        n = MyImage.btn_myalert;
    }
    @Override
    public void run() {
        for(;;) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!a.isNormal()) {
                try {
                    btn.setIcon(a1);
                    Thread.sleep(500);
                    btn.setIcon(a2);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else{
                btn.setIcon(n);
            }
        }
    }
}
