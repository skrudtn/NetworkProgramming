package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-23.
 */
public class ClazzModel {

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(ArrayList<String> attributeList) {
        this.attributeList = attributeList;
    }

    public ArrayList<String> getMethodList() {
        return methodList;
    }

    public void setMethodList(ArrayList<String> methodList) {
        this.methodList = methodList;
    }

    private String className;
    private ArrayList<String> attributeList;
    private ArrayList<String> methodList;
    private String x;
    private String  y;
    private String  w;
    private String  h;
    public ClazzModel(){
        className = "";
        attributeList = new ArrayList<>(0);
        methodList = new ArrayList<>(0);
        x="";
        y="";
        w="";
        h="";
    }


    public ClazzModel(String className, ArrayList<String> atts, ArrayList<String> mets, String x, String y, String w, String h){
        this.className = className;
        this.attributeList = atts;
        this.methodList = mets;
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }

    public String getX() {
        return x;
    }

//    public void setX(String x) {
//        this.x = x;
//    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public void addAttList(String a){
        attributeList.add(a);
    }
    public void rmAttList(String a){
        attributeList.remove(a);
    }
    public void addMethodList(String m){
        methodList.add(m);
    }
    public void rmMethodList(String m){
        methodList.remove(m);
    }
}
