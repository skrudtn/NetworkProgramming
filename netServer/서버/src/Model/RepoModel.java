package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-16.
 */
public class RepoModel {
    private int repoNo;
    private String name;
    private String verName;
    private String id;
    private ArrayList<VersionModel> versions;
    private ArrayList<String> authorizations;
    private String reg_date;

    public RepoModel(){
        repoNo = 0;
        name = "";
        id = "";
        versions = new ArrayList<>();
        authorizations = new ArrayList<>();
    }


    public RepoModel(String name, String id, int repoNo) {
        this.name = name;
        this.id = id;
        this.repoNo = repoNo;
    }

    public int getRepoNo() {
        return repoNo;
    }

    public void setRepoNo(int repoNo) {
        this.repoNo = repoNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<VersionModel> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<VersionModel> versions) {
        this.versions = versions;
    }

    public ArrayList<String> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(ArrayList<String> authorizations) {
        this.authorizations = authorizations;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerName() {
        return verName;
    }
    public void addAuthorization(String a){
        this.authorizations.add(a);
    }
    public void removeAuthorization(String a){
        this.authorizations.remove(a);
    }

}
