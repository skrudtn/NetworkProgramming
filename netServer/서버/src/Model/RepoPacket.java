package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-20.
 */
public class RepoPacket {
    private String name;
    private String id;
    private ArrayList<String> authorities;
    public RepoPacket(String name, String id, ArrayList<String> authorities) {
        this.name = name;
        this.id = id;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }
}
