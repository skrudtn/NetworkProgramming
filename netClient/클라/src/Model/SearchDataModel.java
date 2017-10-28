package Model;

/**
 * Created by skrud on 2017-10-28.
 */
public class SearchDataModel {
    private String title;
    private String id;
    private String date;

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

    public SearchDataModel(String title, String id, String date) {
        this.title = title;
        this.id = id;
        this.date = date;

    }

    public SearchDataModel(){
        this.title ="";
        this.id = "";
        this.date ="";
    }
}
