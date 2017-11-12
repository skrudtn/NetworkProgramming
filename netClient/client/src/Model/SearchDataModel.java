package Model;

/**
 * Created by skrud on 2017-10-28.
 */
public class SearchDataModel {
    private String title;
    private String id;
    private String date;
    private String cdNo;

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

    public SearchDataModel(String title, String id, String date,String cdNo) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.cdNo = cdNo;
    }

    public SearchDataModel(){
        this.title ="";
        this.id = "";
        this.date ="";
        this.cdNo ="";
    }

    public String getCdNo() {
        return cdNo;
    }

    public void setCdNo(String clNo) {
        this.cdNo = clNo;
    }
}
