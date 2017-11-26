package Model;

import java.util.Vector;

/**
 * Created by skrud on 2017-11-05.
 */
public final class LoginInfo {
    private static volatile LoginInfo INSTANCE = null;

    private Vector<String> connectedIds;
    private LoginInfo(){
        connectedIds = new Vector<>();
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
        connectedIds.add(id);
        displayIds();
    }
    public void rmConnectId(String id){
        connectedIds.remove(id);
        displayIds();
    }

    public Vector<String> getconnectedIds() {
        return connectedIds;
    }
    public boolean isConnectedId(String id){
        displayIds();
        for(String str: connectedIds){
            if(id.equals(str)){
                return true;
            }
        }
        return false;
    }
    public void displayIds(){
        System.out.println("---------");
        System.out.println("접속 유저");
        for(String s: connectedIds){
            System.out.println(s);
        }
        System.out.println("---------");
    }
}
