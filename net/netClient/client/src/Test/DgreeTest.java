package Test;
import java.awt.*;

/**
 * Created by skrud on 2017-11-11.
 */
public class DgreeTest {
    public static void main(String[] args) {
        Point p1 = new Point(0,0);
        Point p2 = new Point(100,100);
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double rotAng = Math.toDegrees(Math.atan2(dx,-dy));
        double cosX = Math.cos(Math.toRadians(60));
        new DgreeTest().getBPoint();
//        new DgreeTest().getDgree(10,90);
    }
    private void getDgree(double distance,double angle){
        double x = distance*Math.cos(angle);
        double y = distance*Math.sin(angle);

        System.out.println(x+", "+y);
    }

    public void getBPoint(){

        int x1 =0;
        int y1 =0;
        int x2 =10;
        int y2 =0;
        int len = 3;
        double theta = Math.toRadians(30);
        double ab = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
        System.out.println(ab);
        double n = len*Math.cos(theta);
        double m = ab-len*Math.cos(theta);
        double cX = (n*x1+m*x2)/(m+n);
        double cY = (n*y1+m*y2)/(m+n);
        double slope = (y2-y1)/(x2-x1);
        double alpha = Math.atan(slope);
        int aX = (int)(cX-len*Math.sin(theta)*Math.cos(3.14/2-alpha));
        int aY = (int)(cY+len*Math.sin(theta)*Math.sin(3.14/2-alpha));
        int bX = (int)(cX+len*Math.sin(theta)*Math.cos(3.14/2-alpha));
        int bY = (int)(cY-len*Math.sin(theta)*Math.sin(3.14/2-alpha));
//        return new Point((int)m-len*Math.sin30);
    }
}
