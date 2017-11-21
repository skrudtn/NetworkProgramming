package Model;

/**
 * Created by skrud on 2017-11-16.
 */
public class VersionModel {
    private String name;
    private String diagram;
    private String reg_date;
    private String modifiedBy;
    private int verNo;
    private String ver;

    public VersionModel(String name, String diagram, String reg_date, String modifiedBy,int verNo) {
        this.name = name;
        this.diagram = diagram;
        this.reg_date = reg_date;
        this.modifiedBy = modifiedBy;
        this.verNo = verNo;
        this.ver = "";
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

    public int getVerNo() {
        return verNo;
    }

    public void setVerNo(int verNo) {
        this.verNo = verNo;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
