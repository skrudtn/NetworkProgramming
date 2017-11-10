package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-05.
 */
public final class LoginInfo {
    private static volatile LoginInfo INSTANCE = null;

    private ArrayList<String> connectedId;
    private LoginInfo(){
        connectedId = new ArrayList<>();
    }

    public static LoginInfo getInstance(){
        if(INSTANCE ==null){
            synchronized (LoginInfo.class){
                if(INSTANCE ==null){
                    INSTANCE = new LoginInfo();
                }
            }
        }
        return INSTANCE;
    }

    public void addConnectId(String id){
        connectedId.add(id);
    }
    public void rmConnectId(String id){
        connectedId.remove(id);
    }

    public ArrayList<String> getConnectedId() {
        return connectedId;
    }
}
