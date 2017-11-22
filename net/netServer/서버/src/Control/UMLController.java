package Control;

import Model.*;
import Model.ClassDiagramModel.*;
import Model.RepoModel.*;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-08.
 */
public class UMLController {
    private MainController controller;
    private DBController dc;
    private JsonController jc;
    private String cloneStr;
//    private int repoNo;

    private ArrayList<SearchModel> sms;
//    private RepoModel repoModel;

    UMLController(MainController controller){
        this.controller = controller;
        jc = controller.getJsonController();
        dc = controller.getDBController();
        sms = new ArrayList<>();
    }

    int push(String data){
        int ack= Ack.pushREJ;
        String type = jc.getPushType(jc.str2JSONObject(data));
        switch (type) {
            case "cd":
                System.out.println(data);
                CDModel cdm = jc.str2cdm(data);
                cdm.setJson(data);
                int verNo = dc.insertVersion(cdm.getCdName(),cdm.getId(),cdm.getRepoNo());
                if(dc.pushCD(cdm,verNo)) ack = Ack.pushACK;
                break;
        }
        return ack;
    }

    int cllone(String data){
        int ack =  Ack.cloneREJ;
        CllonePacket cp = jc.str2cp(data);
        cloneStr= dc.cllone(cp);

        if(!cloneStr.equals("")){
            ack = Ack.cloneACK;
        }
        return ack;
    }
    String getCloneModel(){
        return cloneStr;
    }

    int search(String data) {
        int ack = Ack.searchRej;
        SearchPacket sp = jc.str2sp(data);
        ArrayList<SearchModel> sms = dc.search(sp);
        if (!sms.isEmpty()) {
            ack = Ack.searchAck;
            this.sms = sms;
        }
        return ack;
    }

    String getSearchModels(){
        return jc.sms2str(sms);
    }

    int isExistRepo(String data){
        int ack =  Ack.overlapRepoREJ;
        RepoPacket rp = jc.str2repoPacket(data);
        if(!dc.isExistRepo(rp.getName())){
            ack = Ack.overlapRepoACK;
        }
        return ack;
    }

    RepoModel createRepo(String data) {
        RepoPacket repo = jc.str2repoPacket(data);
        int repoNo = dc.insertRepo(repo);
        RepoModel repoModel = new RepoModel();
        repoModel.setId(repo.getId());
        repoModel.setRepoNo(repoNo);
        repoModel.setName(repo.getName());
        System.out.println(repoNo);
        return repoModel;
    }

    int isExistRepoData(String data) {
        int ack =  Ack.repoREJ;
        System.out.println(data);
        RepoModel repo = jc.str2repoData(data);
        if(dc.isExistRepo(repo.getName())){
            ack = Ack.repoACK;
        }
        return ack;
    }
    String getRepoStr(String data){
        RepoModel repo = jc.str2repoData(data);
        return jc.rm2str(dc.selectRepoData(repo),controller.getLoginController().getId());
    }
}
