package Model.ClassDiagramModel;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-25.
 */
public class CDModel {
    private String id;
    private String cdName;
    private ArrayList<ClazzModel> clazzModels;
    private ArrayList<Association> lcList;
    private String json;
    public CDModel(){
        id = "";
        cdName = "";
        clazzModels = new ArrayList<>();
        lcList = new ArrayList<>();
    }

    public CDModel(String id, String cdName, ArrayList<ClazzModel> clazzModels, ArrayList<Association> acList){
        this.cdName = cdName;
        this.clazzModels = clazzModels;
        this.id = id;
        this.lcList = lcList;
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

    public String getId() {
        return id;
    }

    public ArrayList<Association> getLcList() {
        return lcList;
    }

    public void setLcList(ArrayList<Association> lcList) {
        this.lcList = lcList;
    }
    public void addLcList(Association lc){
        this.lcList.add(lc);
    }
    public void rmLcList(Association lc){
        this.lcList.remove(lc);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
