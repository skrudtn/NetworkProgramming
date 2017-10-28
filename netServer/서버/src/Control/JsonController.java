package Control;

import Model.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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
        System.out.println(str);
        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        CDModel rt=new CDModel();
        String cdName="";
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
                } else if (key.equals("classList")) {
                    JSONArray jsonArray = (JSONArray) value;
                    int size = jsonArray.size();
                    for(int i=0; i<size;i++) {
                        ArrayList<String> atts = new ArrayList<>();
                        ArrayList<String> mets = new ArrayList<>();
                        String name = new String();
                        String x = "";
                        String y = "";
                        String w = "";
                        String h = "";
                        JSONObject inObj = (JSONObject) jsonArray.get(i);
                        Set inSet = inObj.keySet();
                        for(Object inKey: inSet){
                            Object inValue = inObj.get(inKey);
                            if(inKey.equals("clazzName")){
                                name = (String) inValue;
                            } else if (inKey.equals("x")) {
                                x = (String) inValue;
                            } else if (inKey.equals("y")) {
                                y = (String) inValue;
                            } else if (inKey.equals("w")) {
                                w = (String) inValue;
                            } else if (inKey.equals("h")) {
                                h = (String) inValue;
                            } else if (inKey.equals("att")) {
                                atts=(ArrayList<String>) inValue;
                            } else if (inKey.equals("met")) {
                                mets=(ArrayList<String>) inValue;
                            }
                        }

                        rt.addClazzModel(new ClazzModel(name,atts,mets,x,y,w,h));
                    }

                }
            }
        }
        rt.setCdName(cdName);

        return rt;
    }

    public SearchModel str2sm(String jsonStr) {

        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        String cate = "";
        String data = "";
        try {
            obj = jsonParser.parse(jsonStr);
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
        return new SearchModel(data, cate);
    }

    public String sdms2Str(ArrayList<SearchDataModel> sdms) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        obj.put("type", "searchData");
        for (SearchDataModel sdm : sdms) {
            JSONObject inObj = new JSONObject();
            inObj.put("id", sdm.getId());
            inObj.put("title", sdm.getTitle());
            inObj.put("date", sdm.getDate());

            arr.add(inObj);
        }
        obj.put("arr", arr);

        return obj.toJSONString();
    }
}
