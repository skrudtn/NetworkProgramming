package Model;

import Control.VersionComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by skrud on 2017-11-16.
 */
public class RepoModel {
    private int repoNo;
    private String repoName;
    private ArrayList<VersionModel> versions;
//    private ArrayList<String> authorizations;
    private String reg_date;
    private boolean isAutho;
    private String createBy;

    public RepoModel(){
        isAutho = false;
        repoNo = 0;
        repoName = "";
        reg_date="";
        versions = new ArrayList<>();
        createBy = "";
//        authorizations = new ArrayList<>();
    }


    public int getRepoNo() {
        return repoNo;
    }

    public void setRepoNo(int repoNo) {
        this.repoNo = repoNo;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public ArrayList<VersionModel> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<VersionModel> versions) {

        VersionComparator comp = new VersionComparator();
        Collections.sort(versions, comp);
        this.versions = versions;
    }

//    public ArrayList<String> getAuthorizations() {
//        return authorizations;
//    }

//    public void setAuthorizations(ArrayList<String> authorizations) {
//        this.authorizations = authorizations;
//    }
//
    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
    public void addVersion(VersionModel v){
        versions.add(v);
    }
    public void removeVersion(VersionModel v){
        versions.remove(v);
    }

    public boolean isAutho() {
        return isAutho;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setAutho(boolean autho) {
        isAutho = autho;
    }
}
