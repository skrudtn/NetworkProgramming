package Control;

import Model.*;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-08.
 */
public class UMLContorller {
    private MainController controller;
    private DBController dc;
    private JsonController jc;
    private String cloneStr;

    private ArrayList<SearchModel> mySms;
    public UMLContorller(MainController controller){
        this.controller = controller;
        jc = controller.getJsonController();
        dc = controller.getDBController();
    }

    public int pushCD(String data){
        int ack= Ack.pushREJ;
        CDModel cdm = jc.str2cdm(data);
        cdm.setJson(data);
        if(dc.push(cdm)){
            ack = Ack.pushACK;
        }
        return ack;
    }

    public int cllone(String data){
        int ack =  Ack.cloneREJ;
        CllonePacket cp = jc.str2cp(data);
        cloneStr= dc.cllone(cp);

        if(!cloneStr.equals("")){
            ack = Ack.cloneACK;
        }
        return ack;
    }

    public String getCloneModel(){
        return cloneStr;
    }

    public int search(String data) {
        int ack = Ack.searchRej;
        SearchPacket sp = jc.str2sp(data);
        ArrayList<SearchModel> sms = dc.search(sp);
        if (!sms.isEmpty()) {
            ack = Ack.searchAck;
            mySms = sms;
        }
        return ack;
    }
    public String getSearchModels(){
        return jc.sms2str(mySms);
    }


}
