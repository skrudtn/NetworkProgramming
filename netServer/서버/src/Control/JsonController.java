package Control;

import Model.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by skrud on 2017-09-27.
 */

public class JsonController {

    private MainController controller;

    public JsonController(MainController controller) {
        this.controller = controller;
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


    public String getJsonType(JSONObject obj) {
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

    public JSONObject string2JSONObject(String string) {
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

    public String lm2str(LoginModel loginModel) {
        JSONObject obj = new JSONObject();
        obj.put("type", "loginInfo");
        obj.put("id", loginModel.getId());
        obj.put("pw", loginModel.getPw());
        obj.put("name", loginModel.getName());
        obj.put("email", loginModel.getEmail());
        return obj.toJSONString();
    }


    public CDModel str2cdm(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        CDModel rt = new CDModel();
        ArrayList<Association> acList = new ArrayList<>();
        String cdName = "";
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
                if (key.equals("cdName")) {
                    cdName = (String) value;
                    rt.setCdName(cdName);
                } else if (key.equals("id")) {
                    id = (String) value;
                    rt.setId(id);
                } else if (key.equals("classList")) {
                    JSONArray jsonArray = (JSONArray) value;
                    int size = jsonArray.size();
                    for (int i = 0; i < size; i++) {
                        ArrayList<String> atts = new ArrayList<>();
                        ArrayList<String> mets = new ArrayList<>();
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
                            }
                        }
                        String arr[];
                        arr = bounds.split(",");
                        rt.addClazzModel(new ClazzModel(name, atts, mets, bounds));
                    }
                } else if (key.equals("points")) {
                    ArrayList<String> points = (ArrayList<String>) value;
                    for (String point : points) {
                        Association ac = new Association();
                        ac.setPoint(point);
                        System.out.println(point);
                        acList.add(ac);
                    }
                    rt.setAcList(acList);
                }
            }

        }
        return rt;
    }


    public String cdm2str(CDModel cd) {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        ArrayList<ClazzModel> cms = cd.getClazzModels();
        ArrayList<Association> acList = cd.getAcList();
        ArrayList<String> a;
        ArrayList<String> m;

        obj.put("cdName", cd.getCdName());
        obj.put("id", cd.getId());
        for (ClazzModel c : cms) {
            JSONObject inObj = new JSONObject();
            String bounds = Integer.toString(c.getX()) + "," + Integer.toString(c.getY())
                    + "," + Integer.toString(c.getW()) + "," + Integer.toString(c.getH());
            inObj.put("clazzName", c.getClassName());
            inObj.put("bounds", bounds);

            a = c.getAttributeList();
            JSONArray aList = new JSONArray();
            for (String str : a) {
                aList.add(str);
            }
            inObj.put("att", aList);

            m = c.getMethodList();
            JSONArray mList = new JSONArray();
            for (String str : m) {
                mList.add(str);
            }
            inObj.put("met", mList);
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

    public SearchPacket str2sp(String str) {
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

    public String sms2str(ArrayList<SearchModel> sms) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        obj.put("type", "searchData");
        for (SearchModel sm : sms) {
            JSONObject inObj = new JSONObject();
            inObj.put("id", sm.getId());
            inObj.put("title", sm.getTitle());
            inObj.put("date", sm.getDate());
            inObj.put("cdNo", String.valueOf(sm.getCdNo()));

            arr.add(inObj);
        }
        obj.put("arr", arr);

        return obj.toJSONString();
    }

    public synchronized CllonePacket str2cp(String str) {
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String id = "";
        String title = "";
        String date = "";
        String cdNo = "";
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
                } else if (key.equals("title")) {
                    title = (String) value;
                } else if (key.equals("date")) {
                    date = (String) value;
                } else if (key.equals("cdNo")) {
                    cdNo = (String) value;
                }
            }
        }
        return new CllonePacket(id, title, date, cdNo);
    }


}
