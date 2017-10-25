package View.ClassDiagram;

import Control.MainController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-09-28.
 */
public class Association {
    int startX;
    int endX;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    int startY;
    int endY;
    public Association(MainController controller, Point startP, Point endP){
        startX = (int) startP.getX();
        startY = (int) startP.getY();
        endX = (int) endP.getX();
        endY = (int) endP.getY();
    }
}
