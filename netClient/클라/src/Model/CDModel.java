package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-25.
 */
public class CDModel
{
    private String cdName;
    private ArrayList<ClazzModel> clazzModels;

    public CDModel(){
        cdName = "";
        clazzModels = new ArrayList<>();
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

    public CDModel(String cdName, ArrayList<ClazzModel> clazzModels){
        this.cdName = cdName;
        this.clazzModels = clazzModels;

    }
}
