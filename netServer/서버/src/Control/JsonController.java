package Control;

import Model.*;
import Model.ClassDiagramModel.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by skrud on 2017-09-27.
 */

class JsonController {
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

    String lm2str(LoginModel loginModel) {
        JSONObject obj = new JSONObject();
        obj.put("type", "loginInfo");
        obj.put("id", loginModel.getId());
        obj.put("pw", loginModel.getPw());
        obj.put("name", loginModel.getName());
        obj.put("email", loginModel.getEmail());
        return obj.toJSONString();
    }

    public String cdm2str(CDModel cd) {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        ArrayList<ClazzModel> cms = cd.getClazzModels();
        ArrayList<Association> lcList = cd.getLcList();
        ArrayList<String> a;
        ArrayList<String> m;

        obj.put("repo", cd.getCdName());
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
            ArrayList<String> dpList = new ArrayList<>();
            for (Association lc : c.getLcList()) {
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
                }else if (lc instanceof Dependency) {
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
        for (Association ac : lcList) {
            if(ac instanceof Aggregation){
                agPoints.add(ac.getPoint());
            } else if(ac instanceof DirectAssociation) {
                dacPoints.add(ac.getPoint());
            }else if(ac instanceof Generalization) {
                gzPoints.add(ac.getPoint());
            }else if(ac instanceof Realization) {
                rzPoints.add(ac.getPoint());
            }else if(ac instanceof Dependency) {
                dpPoints.add(ac.getPoint());
            }else {
                acPoints.add(ac.getPoint());
            }
        }
        obj.put("acPoints", acPoints);
        obj.put("dacPoints", dacPoints);
        obj.put("agPoints", agPoints);
        obj.put("gzPoints", gzPoints);
        obj.put("rzPoints", rzPoints);
        obj.put("dpPoints", dpPoints);
        obj.put("type", "clone");
        obj.put("cloneType", "cd");

        return obj.toJSONString();
    }

    public CDModel str2cdm(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        CDModel rt = new CDModel();
        ArrayList<Association> acList = new ArrayList<>();
        String id = "";
        String repo="";
        String repoNo="";
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
                    repo = (String) value;
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
                    rt.setLcList(acList);
                }else if (key.equals("agPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Aggregation();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setLcList(acList);
                }else if (key.equals("dpPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Dependency();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setLcList(acList);
                }else if (key.equals("dacPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new DirectAssociation();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setLcList(acList);
                }else if (key.equals("gzPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Generalization();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setLcList(acList);
                }else if (key.equals("rzPoints")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Realization();
                        ac.setPoint(point);
                        acList.add(ac);
                    }
                    rt.setLcList(acList);
                }
            }
        }
        rt.setRepoNo(Integer.parseInt(repoNo));
        rt.setCdName(repo);
        rt.setId(id);
        return rt;
    }
    SearchPacket str2sp(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String cate = "";
        String data = "";
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
                if (key.equals("cate")) {
                    cate = (String) value;
                } else if (key.equals("data")) {
                    data = (String) value;
                }
            }
        }
        return new SearchPacket(data, cate);
    }

    String sms2str(ArrayList<SearchModel> sms) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        obj.put("type", "searchData");
        for (SearchModel sm : sms) {
            JSONObject inObj = new JSONObject();
            inObj.put("id", sm.getId());
            inObj.put("title", sm.getTitle());
            inObj.put("date", sm.getDate());
            inObj.put("repoNo", String.valueOf(sm.getCdNo()));

            arr.add(inObj);
        }
        obj.put("arr", arr);

        return obj.toJSONString();
    }

    CllonePacket str2cp(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String name= "";

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
                if (key.equals("name")) {
                    name = (String) value;
                }
            }
        }
        return new CllonePacket(name);
    }

    String str2si(String data) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String id = "";
        try {
            obj = jsonParser.parse(data);
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

    String si2str(String id) {
        JSONObject obj = new JSONObject();
        obj.put("type", "searchId");
        obj.put("id", id);
        return obj.toJSONString();
    }

    String firends2str(ArrayList<String> friends) {
        JSONObject obj = new JSONObject();

        obj.put("type", "friends");
        obj.put("friends", friends);

        return obj.toJSONString();

    }

    String getPushType(JSONObject obj) {
        Set keySet = obj.keySet();
        String type = "";
        for (Object key : keySet) {
            Object value = obj.get(key);
            if (key.equals("pushType")) {
                type = (String) value;
            }
        }
        return type;
    }

    RepoPacket str2repoPacket(String data) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String name  = "";
        String id  = "";
        ArrayList<String> authorities =null;
        try {
            obj = jsonParser.parse(data);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            Set keySet = jsonObject.keySet();
            for (Object key : keySet) {
                Object value = jsonObject.get(key);
                if (key.equals("name")) {
                    name = (String) value;
                } else if(key.equals("id")) {
                    id = (String) value;
                } else if(key.equals("authorities")) {
                    authorities = (ArrayList<String>) value;
                }
            }
        }
        if(authorities == null) authorities = new ArrayList<>();
        return new RepoPacket(name,id,authorities);
    }

    public String rm2str(RepoModel rm,String id) {
        JSONObject obj = new JSONObject();
        boolean isAutho = false;
        obj.put("type", "repoData");
        obj.put("id", rm.getId());
        obj.put("name", rm.getName());
        obj.put("repoNo", String.valueOf(rm.getRepoNo()));
        for(String str: rm.getAuthorizations()){
            if(str.equals(rm.getId())){
                isAutho = true;
            }
        }
        if(rm.getId().equals(id)){
            isAutho = true;
        }
        if(isAutho) {
            obj.put("autho", "true");
        } else{
            obj.put("autho", "false");
        }
        VersionComparator comp = new VersionComparator();
        Collections.sort(rm.getVersions(), comp);
        JSONArray jsonArray = new JSONArray();
        for(VersionModel vm: rm.getVersions()){
            JSONObject inObj = new JSONObject();
            inObj.put("verName", vm.getName());
            inObj.put("date",vm.getReg_date());
            inObj.put("modi",vm.getModifiedBY());
            inObj.put("diagram",vm.getDiagram());
            inObj.put("verNo",String.valueOf(vm.getVerNo()));
            jsonArray.add(inObj);
        }

        obj.put("versions", jsonArray);
        return obj.toJSONString();
    }

    public RepoModel str2repoData(String data) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String name  = "";
        String id  = "";
        String repoNo= "";
        ArrayList<String> authorities =null;
        try {
            obj = jsonParser.parse(data);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            Set keySet = jsonObject.keySet();
            for (Object key : keySet) {
                Object value = jsonObject.get(key);
                if (key.equals("name")) {
                    name = (String) value;
                } else if(key.equals("id")) {
                    id = (String) value;
                } else if(key.equals("repoNo")){
                    repoNo = (String) value;
                }
            }
        }
        System.out.println("JSON Contorller");
        System.out.println(name);
        System.out.println(id);
        System.out.println(repoNo);
        return new RepoModel(name,id,Integer.parseInt(repoNo));
    }

}
