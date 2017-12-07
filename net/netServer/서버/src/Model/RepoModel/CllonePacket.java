package Model.RepoModel;

/**
 * Created by skrud on 2017-10-30.
 */
public class CllonePacket {
    private String name;
    private int repoNo;

    public CllonePacket(String name, int repoNo) {
        this.name = name;
        this.repoNo = repoNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepoNo() {
        return repoNo;
    }

    public void setRepoNo(int repoNo) {
        this.repoNo = repoNo;
    }
}
