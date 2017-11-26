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
    private ArrayList<String> members;
    private String reg_date;
    private boolean isCreator;
    private boolean isMember;
    private String createBy;

    public RepoModel(){
        isCreator = false;
        isMember = false;
        repoNo = 0;
        repoName = "";
        reg_date="";
        versions = new ArrayList<>();
        createBy = "";
        members = new ArrayList<>();
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
        this.versions = versions;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

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

    public boolean isCreator() {
        return isCreator;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
