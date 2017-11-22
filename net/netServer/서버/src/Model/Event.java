package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by skrud on 2017-11-22.
 */
public class Event {
    private String src;
    private String des;
    private String type;
    private String data;
    private String date;

    public Event(String src, String des, String type) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        String date = sdf.format(d);
        this.src = src;
        this.des = des;
        this.type = type;
        this.date = date;
    }

    public Event(String src, String des, String type, String data) {
        this.src = src;
        this.des = des;
        this.type = type;
        this.data = data;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
