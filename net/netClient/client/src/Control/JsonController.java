package Control;

import Model.*;
import Model.ClassDiagramModel.CDModel;
import Model.ClassDiagramModel.ClazzModel;
import Model.ClassDiagramModel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by skrud on 2017-09-27.
 */
public class JsonController {
    String getJsonType(JSONObject obj) {
        Set keySet = obj.keySet();
        String type = "";
        for (Object key : keySet) {
            Object value = obj.get(key);
            if (key.equals("type")) {
                type = (String) value;
            }
        }
        return type;
    }

    JSONObject str2JSONObject(String string) {
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

    String login2JSONString(String id, String pw) {
        JSONObject obj = new JSONObject();
        obj.put("type", "login");
        obj.put("id", id);
        obj.put("pw", pw);

        String loginInfo = obj.toJSONString();
        return loginInfo;
    }

    String pwc2JSONString(String id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "pwcId");
        obj.put("id", id);

        String loginInfo = obj.toJSONString();
        return loginInfo;
    }

    String su2JSONString(String id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "suId");
        obj.put("id", id);

        String loginInfo = obj.toJSONString();
        return loginInfo;
    }

    String signupString2JSONString(LoginModel loginModel) {
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

    LoginModel str2lm(String jsonString) {
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

    public String cdm2str(CDModel cd,int repoNo) {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        ArrayList<ClazzModel> cms = cd.getClazzModels();
        ArrayList<Association> lcList = cd.getAcList();
        ArrayList<String> a;
        ArrayList<String> m;

        obj.put("repo", cd.getCdName());
        obj.put("repoNo", String.valueOf(repoNo));
        obj.put("id", cd.getId());
        for (ClazzModel c : cms) {
            JSONObject inObj = new JSONObject();
            String bounds = Integer.toString(c.getX()) + "," + Integer.toString(c.getY())
                    + "," + Integer.toString(c.getW()) + "," + Integer.toString(c.getH());
            inObj.put("clazzName", c.getClassName());
            inObj.put("bounds", bounds);
            ArrayList<String> acList = new ArrayList<>();
            ArrayList<String> dacList = new ArrayList<>();
            ArrayList<String> rzList = new ArrayList<>();
            ArrayList<String> gzList = new ArrayList<>();
            ArrayList<String> agList = new ArrayList<>();
            ArrayList<String> cpList = new ArrayList<>();
            ArrayList<String> dpList = new ArrayList<>();
            for (Association lc : c.getAcList()) {
                if (lc instanceof DirectAssociation) {
                    dacList.add(lc.getPoint());
                } else if (lc instanceof Realization) {
                    Realization rz = (Realization) lc;
                    rzList.add(rz.getPoint());
                } else if (lc instanceof Generalization) {
                    Generalization gz = (Generalization) lc;
                    gzList.add(gz.getPoint());
                } else if (lc instanceof Aggregation) {
                    Aggregation ag = (Aggregation) lc;
                    agList.add(ag.getPoint());
                } else if (lc instanceof Composition) {
                    Composition cp = (Composition) lc;
                    cpList.add(cp.getPoint());
                } else if (lc instanceof Dependency) {
                    Dependency dp = (Dependency) lc;
                    dpList.add(dp.getPoint());
                } else {
                    acList.add(lc.getPoint());
                }
            }
            inObj.put("clazzAcList", acList);
            inObj.put("clazzAgList", agList);
            inObj.put("clazzDacList", dacList);
            inObj.put("clazzGzList", gzList);
            inObj.put("clazzRzList", rzList);
            inObj.put("clazzDpList", dpList);
            inObj.put("clazzCpList", cpList);

            ArrayList<String> arr = new ArrayList<>();
            for (Integer integer : c.getPointInClazz()) {
                arr.add(String.valueOf(integer));
            }
            inObj.put("pointInClazz", arr);
            inObj.put("att", c.getAttributeList());
            inObj.put("met", c.getMethodList());
            jsonArray.add(inObj);
        }
        obj.put("classList", jsonArray);
        ArrayList<String> acPoints = new ArrayList<>();
        ArrayList<String> agPoints = new ArrayList<>();
        ArrayList<String> dacPoints = new ArrayList<>();
        ArrayList<String> gzPoints = new ArrayList<>();
        ArrayList<String> rzPoints = new ArrayList<>();
        ArrayList<String> dpPoints = new ArrayList<>();
        ArrayList<String> cpPoints = new ArrayList<>();
        for (Association ac : lcList) {
            if (ac instanceof Aggregation) {
                agPoints.add(ac.getPoint());
            } else if (ac instanceof Composition) {
                cpPoints.add(ac.getPoint());
            } else if (ac instanceof DirectAssociation) {
                dacPoints.add(ac.getPoint());
            } else if (ac instanceof Generalization) {
                gzPoints.add(ac.getPoint());
            } else if (ac instanceof Realization) {
                rzPoints.add(ac.getPoint());
            } else if (ac instanceof Dependency) {
                dpPoints.add(ac.getPoint());
            } else {
                acPoints.add(ac.getPoint());
            }
        }
        obj.put("acPoints", acPoints);
        obj.put("dacPoints", dacPoints);
        obj.put("agPoints", agPoints);
        obj.put("gzPoints", gzPoints);
        obj.put("rzPoints", rzPoints);
        obj.put("dpPoints", dpPoints);
        obj.put("cpPoints", cpPoints);
        obj.put("type", "push");
        obj.put("pushType", "cd");

        return obj.toJSONString();
    }

    public CDModel str2cdm(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        CDModel rt = new CDModel();
        ArrayList<Association> acList = new ArrayList<>();
        String cdName = "";
        String repoNo= "";
        String id = "";
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
                if (key.equals("repo")) {
                    cdName = (String) value;
                } else if (key.equals("repoNo")) {
                    repoNo = (String) value;
                } else if (key.equals("id")) {
                    id = (String) value;
                } else if (key.equals("classList")) {
                    JSONArray jsonArray = (JSONArray) value;
                    int size = jsonArray.size();
                    for (int i = 0; i < size; i++) {
                        ArrayList<String> atts = new ArrayList<>();
                        ArrayList<String> mets = new ArrayList<>();
                        ArrayList<Association> inAcList = new ArrayList<>();

                        ArrayList<Integer> pointInClazz = new ArrayList<>();

                        String name = "";
                        String bounds = "";
                        JSONObject inObj = (JSONObject) jsonArray.get(i);
                        Set inSet = inObj.keySet();
                        for (Object inKey : inSet) {
                            Object inValue = inObj.get(inKey);
                            if (inKey.equals("clazzName")) {
                                name = (String) inValue;
                            } else if (inKey.equals("bounds")) {
                                bounds = (String) inValue;
                            } else if (inKey.equals("att")) {
                                atts = (ArrayList<String>) inValue;
                            } else if (inKey.equals("met")) {
                                mets = (ArrayList<String>) inValue;
                            } else if (inKey.equals("clazzAcList")) {
                                ArrayList<String> strAcList = (ArrayList<String>) inValue;
                                for (String point : strAcList) {
                                    Association ac = new Association();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("clazzCpList")) {
                                ArrayList<String> strAcList = (ArrayList<String>) inValue;
                                for (String point : strAcList) {
                                    Association ac = new Composition();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("clazzAgList")) {
                                ArrayList<String> strAgList = (ArrayList<String>) inValue;
                                for (String point : strAgList) {
                                    Association ac = new Aggregation();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("clazzAgList")) {
                                ArrayList<String> strAgList = (ArrayList<String>) inValue;
                                for (String point : strAgList) {
                                    Association ac = new Aggregation();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("clazzDacList")) {
                                ArrayList<String> strDacList = (ArrayList<String>) inValue;
                                for (String point : strDacList) {
                                    Association ac = new DirectAssociation();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("clazzGzList")) {
                                ArrayList<String> strGzList = (ArrayList<String>) inValue;
                                for (String point : strGzList) {
                                    Association ac = new Generalization();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("clazzRzList")) {
                                ArrayList<String> strRzList = (ArrayList<String>) inValue;
                                for (String point : strRzList) {
                                    Association ac = new Realization();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("clazzDpList")) {
                                ArrayList<String> strRzList = (ArrayList<String>) inValue;
                                for (String point : strRzList) {
                                    Association ac = new Dependency();
                                    ac.setPoint(point);
                                    inAcList.add(ac);
                                }
                            } else if (inKey.equals("pointInClazz")) {
                                ArrayList<String> strPIC = (ArrayList<String>) inValue;
                                for (String point : strPIC) {
                                    pointInClazz.add(Integer.parseInt(point));
                                }
                            }
                        }
                        String arr[];
                        arr = bounds.split(",");
                        rt.addClazzModel(new ClazzModel(name, atts, mets,
                                Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), inAcList, pointInClazz));
                    }

                } else if (key.equals("acPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Association();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                } else if (key.equals("agPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Aggregation();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                } else if (key.equals("cpPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Composition();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                } else if (key.equals("dpPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Dependency();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                } else if (key.equals("dacPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new DirectAssociation();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                } else if (key.equals("gzPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Generalization();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                } else if (key.equals("rzPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Realization();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                }
            }
        }
        rt.setCdName(cdName);
        rt.setRepoNo(Integer.parseInt(repoNo));
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
    public String getCloneStr(String name) {
        JSONObject obj = new JSONObject();
        obj.put("type", "clone");
        obj.put("name", name);

        return obj.toJSONString();
    }

    public String getSearchStr(String data, String cate) {
        JSONObject obj = new JSONObject();
        obj.put("type", "search");
        obj.put("cate", cate);
        obj.put("data", data);

        return obj.toJSONString();
    }
    public String getSearchId(String id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "searchId");
        obj.put("id", id);

        return obj.toJSONString();
    }

    public String getSearchRepoStr(String name, String id, ArrayList<String> authorities) {
        JSONObject obj = new JSONObject();
        obj.put("type", "repoPacket");
        obj.put("name", name);
        obj.put("id", id);
        obj.put("authorities", authorities);

        return obj.toJSONString();
    }

    public String getRepoDataStr(String s,String id, String repoNo) {
        JSONObject obj = new JSONObject();
        obj.put("type", "repoModel");
        obj.put("name", s);
        obj.put("id", id);
        obj.put("repoNo", repoNo);

        return obj.toJSONString();
    }

    public ArrayList<SearchRepoModel> str2smds(String str) {
        ArrayList<SearchRepoModel> sdms = new ArrayList<>();
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
                if (key.equals("arr")) {
                    JSONArray arr = (JSONArray) value;
                    int size = arr.size();
                    for (int i = 0; i < size; i++) {
                        JSONObject inObj = (JSONObject) arr.get(i);
                        Set inSet = inObj.keySet();
                        String title = "";
                        String id = "";
                        String date = "";
                        String repoNo ="";
                        for (Object inKey : inSet) {
                            Object inValue = inObj.get(inKey);
                            if (inKey.equals("title")) {
                                title = (String) inValue;
                            } else if (inKey.equals("id")) {
                                id = (String) inValue;
                            } else if (inKey.equals("date")) {
                                date = (String) inValue;
                            } else if (inKey.equals("repoNo")){
                                repoNo = (String) inValue;
                            }
                        }
                        SearchRepoModel sdm = new SearchRepoModel(title, id, date, repoNo);
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

    public ArrayList<String> str2friends(String str) {
        ArrayList<String> friends = new ArrayList<>();
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
                if (key.equals("friends")) {
                    friends = (ArrayList<String>) value;
                }
            }
        }
        return friends;
    }

    public RepoModel str2repo(String str) {

        RepoModel repoModel = new RepoModel();
        ArrayList<VersionModel> versions = new ArrayList<>();
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
                if (key.equals("id")) {
                    repoModel.setCreateBy((String) value);
                } else if (key.equals("name")) {
                    repoModel.setRepoName((String) value);
                } else if (key.equals("repoNo")) {
                    String temp = (String) value;
                    repoModel.setRepoNo(Integer.parseInt(temp));
                } else if (key.equals("autho")) {
                    String temp = (String) value;
                    if(temp.equals("true")){
                        repoModel.setAutho(true);
                    } else{
                        repoModel.setAutho(false);
                    }
                } else if (key.equals("versions")) {
                    JSONArray arr = (JSONArray) value;
                    int size = arr.size();
                    for (int i = 0; i < size; i++) {
                        JSONObject inObj = (JSONObject) arr.get(i);
                        Set inSet = inObj.keySet();
                        String verName = "";
                        String date = "";
                        String modi = "";
                        String diagram = "";
                        String verNo = "";
                        String ver = "";

                        for (Object inKey : inSet) {
                            Object inValue = inObj.get(inKey);
                            if (inKey.equals("verName")) {
                                verName = (String) inValue;
                            } else if (inKey.equals("date")) {
                                date = (String) inValue;
                            } else if (inKey.equals("modi")) {
                                modi = (String) inValue;
                            } else if (inKey.equals("diagram")) {
                                diagram = (String) inValue;
                            } else if (inKey.equals("verNo")) {
                                verNo = (String) inValue;
                            }  else if (inKey.equals("ver")) {
                                ver = (String) inValue;
                            }
                        }
                        VersionModel vm = new VersionModel(verName, diagram, date, modi, Integer.parseInt(verNo),Integer.parseInt(ver));
                        versions.add(vm);
                    }
                    repoModel.setVersions(versions);
                }
            }
        }
        return repoModel;
    }

    public String str2reqFriend(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String id = "";
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
                if (key.equals("id")) {
                    id = (String) value;
                }
            }
        }
        return id;
    }

    public ArrayList<Event> str2Events(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        ArrayList<String> temp=null;
        ArrayList<Event> events = new ArrayList<>();
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
                if (key.equals("reqFriends")) {
                    JSONObject inObj = (JSONObject) obj;
//                    Set keySet = jsonObject.keySet();
                    temp = (ArrayList<String>) value;
//                    events.addAll(temp);
                } else if(key.equals("reqMembers")){
                    temp = (ArrayList<String>) value;
//                    events.addAll(temp);
                }
            }
        }
        return events;
    }

    public Event str2friendEvents(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String src=null;
        String des = null;
        String date = null;
        String data = null;
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
                if (key.equals("src")) {
                    src = (String) value;
                } else if(key.equals("des")){
                    des = (String) value;
                } else if(key.equals("date")){
                    date = (String) value;
                }else if(key.equals("data")){
                    data = (String) value;
                }
            }
        }
        return new Event(src,des,"friend",data,date);
    }
}