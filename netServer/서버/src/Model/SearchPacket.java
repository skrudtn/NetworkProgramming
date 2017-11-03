package Model;

/**
 * Created by skrud on 2017-10-27.
 */
public class SearchPacket {
    private String data;
    private String cate;

    public SearchPacket() {
        data = "";
        cate = "";
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategory() {
        return cate;
    }

    public void setCategory(String category) {
        this.cate = category;
    }

    public SearchPacket(String data, String category) {
        this.data = data;
        this.cate = category;
    }
}
