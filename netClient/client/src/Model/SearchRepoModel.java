package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-28.
 */
public class SearchRepoModel {
    private String title;
    private String id;
    private String date;
    private String repoNo;
    private ArrayList<String> friends;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SearchRepoModel(String title, String id, String date, String repoNo) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.repoNo = repoNo;
    }

    public SearchRepoModel(){
        this.title ="";
        this.id = "";
        this.date ="";
        this.repoNo ="";
        this.friends = new ArrayList<>();
    }

    public String getRepoNo() {
        return repoNo;
    }

    public void setRepoNo(String repoNo) {
        this.repoNo = repoNo;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
}
