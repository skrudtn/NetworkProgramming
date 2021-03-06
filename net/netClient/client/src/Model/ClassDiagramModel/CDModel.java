package Model.ClassDiagramModel;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-25.
 */
public class CDModel {
    private int repoNo;
    private String repo;
    private String id;
    private String cdName;
    private ArrayList<ClazzModel> clazzModels;
    private ArrayList<Association> acList;

    public CDModel(){
        id = "";
        cdName = "";
        clazzModels = new ArrayList<>();
        acList = new ArrayList<>();
    }

    public CDModel(String id, String cdName, ArrayList<ClazzModel> clazzModels, ArrayList<Association> acList){
        this.cdName = cdName;
        this.clazzModels = clazzModels;
        this.id = id;
        this.acList = acList;
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

    public ArrayList<Association> getAcList() {
        return acList;
    }

    public void setAcList(ArrayList<Association> acList) {
        this.acList = acList;
    }
    public void addAcList(Association ac){
        this.acList.add(ac);
    }
    public void rmAcList(Association ac){
        this.acList.remove(ac);
    }

    public int getRepoNo() {
        return repoNo;
    }

    public void setRepoNo(int repoNo) {
        this.repoNo = repoNo;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public void setId(String id) {
        this.id = id;

    }
}
