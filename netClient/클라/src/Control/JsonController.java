package Control;

import Model.ClazzModel;
import Model.LoginModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by skrud on 2017-09-27.
 */
public class JsonController {
    MainController controller = null;


    public JsonController(MainController controller) {
        this.controller = controller;
    }

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

    public LoginModel loginString2loginModel(String jsonString) {
        System.out.println(jsonString);
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
                System.out.println(key + ":" + value);
            }
        }
        return new LoginModel(id, pw, email, name);
    }

    public String cd2str(ArrayList<ClazzModel> cmList, String cdName){
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String n="";
        ArrayList<String> a;
        ArrayList<String> m;

        obj.put("cdName", cdName);
        for(ClazzModel c: cmList) {
            JSONObject inObj = new JSONObject();
            inObj.put("clazzName",c.getClassName());
            inObj.put("x",Integer.toString(c.getX()));
            inObj.put("y",Integer.toString(c.getY()));
            inObj.put("w",Integer.toString(c.getW()));
            inObj.put("h",Integer.toString(c.getH()));

            a=c.getAttributeList();
            JSONArray aList = new JSONArray();
            for(String str: a) {
                aList.add(str);
            }
            inObj.put("att",aList);

            m=c.getMethodList();
            JSONArray mList = new JSONArray();
            for(String str: m){
                mList.add(str);
            }
            inObj.put("met",mList);
            jsonArray.add(inObj);
        }
        obj.put("classList",jsonArray);
        obj.put("type","classDiagram");

        return obj.toJSONString();
    }

    public String pw2JSONString(String id, String pw) {
        JSONObject obj = new JSONObject();
        obj.put("type", "pw");
        obj.put("id", id);
        obj.put("pw", pw);

        return obj.toJSONString();
    }

    public String cdName2str(String cdName) {
        JSONObject obj = new JSONObject();
        obj.put("type", "cd");
        obj.put("name", cdName);

        return obj.toJSONString();
    }
}
