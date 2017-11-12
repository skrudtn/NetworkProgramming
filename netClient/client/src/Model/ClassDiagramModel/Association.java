package Model.ClassDiagramModel;

import java.awt.*;

/**
 * Created by skrud on 2017-11-11.
 */
public class Association {
    protected String sX;
    protected String sY;
    protected String eX;
    protected String eY;
    private String point;
    private Point a;
    private Point b;
    private double cX;
    private double cY;
    private double dX;
    private double dY;
    private double alpha;
    private double slope;
    public int getcX() {
        return (int)cX;
    }
    public int getcY() {
        return (int)cY;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getSlope() {
        return slope;
    }

    public int getdX() {
        return (int)dX;
    }

    public int getdY() {
        return (int)dY;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public void setB(Point b) {
        this.b = b;
    }
    public void makeABpoint(int len){
        int x1 =Integer.parseInt(sX);
        int y1 =Integer.parseInt(sY);
        int x2 =Integer.parseInt(eX);
        int y2 =Integer.parseInt(eY);

        double theta = Math.toRadians(30);
        double ab = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
        double n = len*Math.cos(theta);
        double m = ab-n;
        double dn = 2*n;
        double dm = ab-(dn);
        cX = (n*x1+m*x2)/(m+n);
        cY = (n*y1+m*y2)/(m+n);
        dX = (dn*x1+dm*x2)/(dm+dn);
        dY = (dn*y1+dm*y2)/(dm+dn);
        if(x1!=x2){
            slope = (y2-y1)/(x2-x1);
        }
        alpha = Math.atan(slope);
        int aX = (int)(cX-len*Math.sin(theta)*Math.cos(3.14/2-alpha));
        int aY = (int)(cY+len*Math.sin(theta)*Math.sin(3.14/2-alpha));
        a = new Point(aX,aY);
        int bX = (int)(cX+len*Math.sin(theta)*Math.cos(3.14/2-alpha));
        int bY = (int)(cY-len*Math.sin(theta)*Math.sin(3.14/2-alpha));
        b = new Point(bX,bY);
    }
    public Point getB(){
        return b;
    }
    public Point getA(){
        return a;
    }
    public String getPoint() {
        point= sX+","+sY+","+eX+","+eY;
        return point;
    }

    public void setPoint(String point) {
        String arr[];
        arr = point.split(",");
        sX = arr[0];
        sY = arr[1];
        eX = arr[2];
        eY = arr[3];
        this.point = point;
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
