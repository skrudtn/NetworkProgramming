package Model;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by skrud on 2017-11-25.
 */
public class MemManagePacket {
    ArrayList<String> members;
    int repoNo;

    public MemManagePacket(ArrayList<String> members, int repoNo) {
        this.members = members;
        this.repoNo = repoNo;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public int getRepoNo() {
        return repoNo;
    }

    public void setRepoNo(int repoNo) {
        this.repoNo = repoNo;
    }
}
