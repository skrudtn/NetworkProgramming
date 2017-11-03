package Model;

/**
 * Created by skrud on 2017-10-28.
 */
public class SearchModel {
    private String title;
    private String id;
    private String date;
    private int cdNo;

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

    public SearchModel(String title, String id, String date, int cdNo) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.cdNo = cdNo;
    }

    public SearchModel(){
        this.title ="";
        this.id = "";
        this.date ="";
        this.cdNo = 0;
    }


    public int getCdNo() {
        return cdNo;
    }
    public void setCdNo(int cdNo) {
        this.cdNo = cdNo;
    }

}
