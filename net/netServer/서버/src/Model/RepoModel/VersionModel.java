package Model;

/**
 * Created by skrud on 2017-11-16.
 */
public class VersionModel {
    private int verNo;
    private String name;
    private String diagram;
    private String modifiedBy;
    private String createBy;
    private String reg_date;
    private int ver;

    public VersionModel(){
        verNo = 0;
        ver = 0;
        name = "";
        diagram = "";
        createBy="";
        modifiedBy="";
        reg_date = "";

    }

    public VersionModel(String verName, String diagram, String date, String modi, int verNo, int ver) {
        this.name= verName;
        this.diagram = diagram;
        this.reg_date = date;
        this.modifiedBy = modi;
        this.verNo = verNo;
        this.ver = ver;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public void setCreateBy(String createBy) {
        this.createBy= createBy;
    }

    public String getCreateBy() {
        return createBy;
    }
}
