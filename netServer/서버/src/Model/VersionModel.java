package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-16.
 */
public class VersionModel {
    private int verNo;
    private String name;
    private String diagram;
    private String modifiedBY;
    private String createBY;
    private String reg_date;
    private int ver;

    public VersionModel(){
        verNo = 0;
        ver = 0;
        name = "";
        diagram = "";
        createBY="";
        modifiedBY="";
        reg_date = "";

    }

    public int getVerNo() {
        return verNo;
    }

    public void setVerNo(int verNo) {
        this.verNo = verNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getModifiedBY() {
        return modifiedBY;
    }

    public void setModifiedBY(String modifiedBY) {
        this.modifiedBY = modifiedBY;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public void setCreateBy(String createBy) {
        this.createBY= createBy;
    }

    public String getCreateBY() {
        return createBY;
    }
}
