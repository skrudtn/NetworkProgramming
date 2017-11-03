package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-25.
 */
public class CDModel
{
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String cdName;
    private ArrayList<ClazzModel> clazzModels;
    private ArrayList<Association> acList;

    public CDModel(){
        id="";
        cdName = "";
        clazzModels = new ArrayList<>();
        acList = new ArrayList<>();
    }
    public CDModel(String id,String cdName){
        this.id = id;
        this.cdName =cdName;
        clazzModels = new ArrayList<>();
        acList = new ArrayList<>();
    }
    public String getCdName() {
        return cdName;
    }

    public void setCdName(String cdName) {
        this.cdName = cdName;
    }

    public ArrayList<ClazzModel> getClazzModels() {
        return clazzModels;
    }
    public void addClazzModel(ClazzModel clazzModel){
        clazzModels.add(clazzModel);
    }
    public void rmClazzModel(ClazzModel clazzModel){
        clazzModels.remove(clazzModel);
    }

    public void setClazzModels(ArrayList<ClazzModel> clazzModels) {
        this.clazzModels = clazzModels;
    }

    public CDModel(String id,String cdName, ArrayList<ClazzModel> clazzModels){
        this.id = id;
        this.cdName = cdName;
        this.clazzModels = clazzModels;

    }

    public void setAcList(ArrayList<Association> acList) {
        this.acList = acList;
    }

    public ArrayList<Association> getAcList() {
        return acList;
    }

    public void addAcList(Association ac) {
        acList.add(ac);
    }
}
