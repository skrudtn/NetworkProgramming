package Model;

/**
 * Created by skrud on 2017-11-23.
 */
public class ResFriendPacket {
    private String des;
    private boolean isOk;

    public ResFriendPacket(String des, boolean isOk) {
        this.des = des;
        this.isOk = isOk;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
