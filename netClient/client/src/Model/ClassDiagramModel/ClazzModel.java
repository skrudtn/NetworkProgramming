package Model.ClassDiagramModel;

import Model.ClassDiagramModel.Association;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-03.
 */
/*
  class model
 */
public class ClazzModel {
    private String className;
    private ArrayList<String> attributeList;
    private ArrayList<String> methodList;
    private ArrayList<Integer> pointInClazz;
    private ArrayList<Association> acList;
    private int x;
    private int y;
    private int w;
    private int h;
    private String bounds;

    public ClazzModel(){
        className = "";
        attributeList = new ArrayList<>(0);
        methodList = new ArrayList<>(0);
        acList = new ArrayList<>();
        pointInClazz = new ArrayList<>();
        x=0;
        y=0;
        w=0;
        h=0;
        bounds = "";
    }


    public ClazzModel(String name, ArrayList<String> atts, ArrayList<String> mets, int x, int y, int w, int h, ArrayList<Association> acList, ArrayList<Integer> pointInClazz){
        this.className = name;
        this.attributeList = atts;
        this.methodList = mets;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.acList = acList;
        this.pointInClazz = pointInClazz;
    }

    public ClazzModel(String name, ArrayList<String> atts, ArrayList<String> mets, String bounds, ArrayList<Association> acList, ArrayList<Integer> pointInClazz){
        this.className = name;
        this.attributeList = atts;
        this.methodList = mets;
        this.bounds = bounds;
        String arr[];
        arr = bounds.split(",");
        this.x = Integer.parseInt(arr[0]);
        this.y = Integer.parseInt(arr[1]);
        this.w = Integer.parseInt(arr[2]);
        this.h = Integer.parseInt(arr[3]);
        this.acList = acList;
        this.pointInClazz = pointInClazz;
    }
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
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

    public ArrayList<Association> getAcList() {
        return acList;
    }

    public void setAcList(ArrayList<Association> acList) {
        this.acList = acList;
    }
    public void addAcList(Association ac){
        this.acList.add(ac);
    }

    public ArrayList<Integer> getPointInClazz() {
        return pointInClazz;
    }

    public void setPointInClazz(ArrayList<Integer> pointInClazz) {
        this.pointInClazz = pointInClazz;
    }

    public String getBounds() {
        return bounds;
    }

    public void setBounds(String bounds) {
        this.bounds = bounds;
    }
}
