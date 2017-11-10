package Control;

import Model.CDModel;
import Model.ClazzModel;
import Model.LoginModel;
import Model.SearchDataModel;
import Model.Association;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by skrud on 2017-09-27.
 */
public class JsonController {

    public String login2JSONString(String id, String pw) {
        JSONObject obj = new JSONObject();
        obj.put("type", "login");
        obj.put("id", id);
        obj.put("pw", pw);

        String loginInfo = obj.toJSONString();
        return loginInfo;
    }

    public String pwc2JSONString(String id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "pwcId");
        obj.put("id", id);

        String loginInfo = obj.toJSONString();
        return loginInfo;
    }

    public String su2JSONString(String id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "suId");
        obj.put("id", id);

        String loginInfo = obj.toJSONString();
        return loginInfo;
    }

    public String signupString2JSONString(LoginModel loginModel) {
        JSONObject obj = new JSONObject();
        String regString = "";
        obj.put("type", "signup");
        obj.put("id", loginModel.getId());
        obj.put("pw", loginModel.getPw());
        obj.put("name", loginModel.getName());
        obj.put("email", loginModel.getEmail());
        regString = obj.toJSONString();
        return regString;
    }


    public JSONObject str2Json(String string) {
        Object obj = null;
        JSONObject jsonObject = null;
        JSONParser jsonParser = new JSONParser();
        try {
            obj = jsonParser.parse(string);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        if (obj instanceof JSONObject) {
            jsonObject = (JSONObject) obj;
        }

        return jsonObject;
    }

    public LoginModel str2lm(String jsonString) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String id = "";
        String pw = "";
        String email = "";
        String name = "";
        try {

            obj = jsonParser.parse(jsonString);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            Set keySet = jsonObject.keySet();
            for (Object key : keySet) {
                Object value = jsonObject.get(key);
                if (key.equals("id")) {
                    id = (String) value;
                } else if (key.equals("pw")) {
                    pw = (String) value;
                } else if (key.equals("email")) {
                    email = (String) value;
                } else if (key.equals("name")) {
                    name = (String) value;
                }
            }
        }
        return new LoginModel(id, pw, email, name);
    }

    public String cdm2str(CDModel cd) {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        ArrayList<ClazzModel> cms =cd.getClazzModels();
        ArrayList<Association> acList = cd.getAcList();
        ArrayList<String> a;
        ArrayList<String> m;

        obj.put("cdName", cd.getCdName());
        obj.put("id", cd.getId());
        for (ClazzModel c : cms) {
            JSONObject inObj = new JSONObject();
            String bounds = Integer.toString(c.getX())+","+Integer.toString(c.getY())
                    +","+Integer.toString(c.getW())+","+Integer.toString(c.getH());
            inObj.put("clazzName", c.getClassName());
            inObj.put("bounds", bounds);
            ArrayList<String> arr = new ArrayList<>();
            for(Association association: c.getAcList()){
                arr.add(association.getPoint());
            }
            inObj.put("clazzAcList",arr);

            arr = new ArrayList<>();
            for(Integer integer : c.getPointInClazz()){
                arr.add(String.valueOf(integer));
            }
            inObj.put("pointInClazz",arr);
            inObj.put("att", c.getAttributeList());
            inObj.put("met", c.getMethodList());
            jsonArray.add(inObj);
        }
        obj.put("classList", jsonArray);
        ArrayList<String> points = new ArrayList<>();
        for (Association ac : acList) {
            points.add(ac.getPoint());
        }
        obj.put("points", points);
        obj.put("type", "classDiagram");

        return obj.toJSONString();
    }

    public CDModel str2cdm(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        CDModel rt=new CDModel();
        ArrayList<Association> acList = new ArrayList<>();
        String cdName="";
        String id="";
        try {
            obj = jsonParser.parse(str);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            Set keySet = jsonObject.keySet();
            for (Object key : keySet) {
                Object value = jsonObject.get(key);
                if (key.equals("cdName")) {
                    cdName = (String) value;
                } else if(key.equals("id")){
                    id = (String)value;
                } else if (key.equals("classList")) {
                    JSONArray jsonArray = (JSONArray) value;
                    int size = jsonArray.size();
                    for(int i=0; i<size;i++) {
                        ArrayList<String> atts = new ArrayList<>();
                        ArrayList<String> mets = new ArrayList<>();
                        ArrayList<Association> inAcList = new ArrayList<>();
                        ArrayList<Integer> pointInClazz = new ArrayList<>();

                        String name = "";
                        String bounds = "";
                        JSONObject inObj = (JSONObject) jsonArray.get(i);
                        Set inSet = inObj.keySet();
                        for(Object inKey: inSet){
                            Object inValue = inObj.get(inKey);
                            if(inKey.equals("clazzName")){
                                name = (String) inValue;
                            } else if (inKey.equals("bounds")) {
                                bounds = (String) inValue;
                            } else if (inKey.equals("att")) {
                                atts=(ArrayList<String>) inValue;
                            } else if (inKey.equals("met")) {
                                mets=(ArrayList<String>) inValue;
                            } else if( inKey.equals("clazzAcList")){
                                ArrayList<String> strAcList = (ArrayList<String>) inValue;
                                for(String point : strAcList) {
                                    Association ac = new Association();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if(inKey.equals("pointInClazz")){
                                ArrayList<String> strPIC = (ArrayList<String>) inValue;
                                for(String point : strPIC) {
                                    pointInClazz.add(Integer.parseInt(point));
                                }
                            }
                        }
                        String arr[];
                        arr = bounds.split(",");
                        rt.addClazzModel(new ClazzModel(name,atts,mets,
                                Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]),inAcList,pointInClazz));
                    }

                } else if(key.equals("points")){
                    ArrayList<String> points = (ArrayList<String>) value;
                    for(String point : points) {
                        Association ac = new Association();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                }
            }
        }
        rt.setCdName(cdName);
        rt.setId(id);
        return rt;
    }

    public String pw2JSONString(String id, String pw) {
        JSONObject obj = new JSONObject();
        obj.put("type", "pw");
        obj.put("id", id);
        obj.put("pw", pw);

        return obj.toJSONString();
    }

    public String getCloneStr(String id, String title, String date, String cdNo) {
        JSONObject obj = new JSONObject();
        obj.put("type", "clone");
        obj.put("id", id);
        obj.put("title", title);
        obj.put("date", date);
        obj.put("cdNo", cdNo);

        return obj.toJSONString();
    }

    public String getSearchStr(String data, String cate) {
        JSONObject obj = new JSONObject();
        obj.put("type", "search");
        obj.put("cate", cate);
        obj.put("data", data);

        return obj.toJSONString();
    }

    public ArrayList<SearchDataModel> str2smds(String str) {
        ArrayList<SearchDataModel> sdms = new ArrayList<>();
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        try {
            obj = jsonParser.parse(str);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            Set keySet = jsonObject.keySet();
            for (Object key : keySet) {
                Object value = jsonObject.get(key);
                if(key.equals("arr")){
                    JSONArray arr = (JSONArray) value;
                    int size = arr.size();
                    for(int i=0; i<size; i++){
                        JSONObject inObj = (JSONObject) arr.get(i);
                        Set inSet = inObj.keySet();
                        String title = "";
                        String id = "";
                        String date="";
                        String cdNo = "";

                        for(Object inKey: inSet){
                            Object inValue = inObj.get(inKey);
                            if(inKey.equals("title")){
                                title = (String) inValue;
                            } else if (inKey.equals("id")) {
                                id = (String) inValue;
                            } else if (inKey.equals("date")) {
                                date = (String) inValue;
                            } else if(inKey.equals("cdNo")){
                                cdNo = (String) inValue;
                            }
                        }
                        SearchDataModel sdm = new SearchDataModel(title,id,date,cdNo);
                        sdms.add(sdm);
                    }
                }
            }
        }
        return sdms;

    }

    public String getLogoutStr(String id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "logout");
        obj.put("id", id);

        return obj.toJSONString();
    }
}
