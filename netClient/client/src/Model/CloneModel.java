package Model;

/**
 * Created by skrud on 2017-10-26.
 */
public class CloneModel {
    private String id;
    private String dir;

    public CloneModel(String id, String dir) {
        this.id = id;
        this.dir = dir;
    }

    public CloneModel() {
        id = "";
        dir = "";
    }
}
