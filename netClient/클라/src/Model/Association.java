package Model;

import java.awt.*;

/**
 * Created by skrud on 2017-09-28.
 */
public class Association {
    private String sX;
    private String sY;
    private String eX;
    private String eY;
    private String Point;


    public String getPoint() {
        Point= sX+","+sY+","+eX+","+eY;
        return Point;
    }

    public void setPoint(String point) {
        String arr[];
        arr = point.split(",");
        sX = arr[0];
        sY = arr[1];
        eX = arr[2];
        eY = arr[3];
        Point = point;
    }
    public int getsX() {
        return Integer.parseInt(sX);
    }

    public void setsX(String sX) {
        this.sX = sX;
    }

    public int getsY() {
        return Integer.parseInt(sY);
    }

    public void setsY(String sY) {
        this.sY = sY;
    }

    public int geteX() {
        return Integer.parseInt(eX);
    }

    public void seteX(String eX) {
        this.eX = eX;
    }

    public int geteY() {
        return Integer.parseInt(eY);
    }

    public void seteY(String eY) {
        this.eY = eY;
    }


    public Association(Point startP, Point endP){
        sX = String.valueOf((int) startP.getX());
        sY = String.valueOf((int) startP.getY());
        eX = String.valueOf((int) endP.getX());
        eY = String.valueOf((int) endP.getY());
    }

    public Association(int startX, int endX, int startY, int endY) {
        this.sX = String.valueOf(startX);
        this.eX = String.valueOf(endX);
        this.sY = String.valueOf(startY);
        this.eY = String.valueOf(endY);
    }

    public Association(String sX, String sY, String eX, String eY) {
        this.sX = sX;
        this.sY = sY;
        this.eX = eX;
        this.eY = eY;
    }

    public Association() {
    }
}
