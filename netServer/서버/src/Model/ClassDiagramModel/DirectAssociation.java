package Model.ClassDiagramModel;

import java.awt.*;

/**
 * Created by skrud on 2017-09-28.
 */
public class DirectAssociation extends Association {
    public DirectAssociation(Point p1, Point p2) {
        sX = String.valueOf((int) p1.getX());
        sY = String.valueOf((int) p1.getY());
        eX = String.valueOf((int) p2.getX());
        eY = String.valueOf((int) p2.getY());
    }


    public DirectAssociation() {

    }
}
