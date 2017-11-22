package Model.RepoModel;

/**
 * Created by skrud on 2017-10-26.
 */
public class ClloneModel {
    private String id;
    private int clNo;

    public ClloneModel(String id, int clNo) {
        this.clNo = clNo;
    }

    public ClloneModel() {
        id = "";
        clNo = 0;
    }
}
