package Model;

/**
 * Created by skrud on 2017-10-26.
 */
public class PullModel {
    private String id;
    private String dir;

    public PullModel(String id, String dir) {
        this.id = id;
        this.dir = dir;
    }

    public PullModel(){
        id = "";
        dir = "";
    }
}
