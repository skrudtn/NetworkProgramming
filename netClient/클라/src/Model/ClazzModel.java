package Model;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-03.
 */
/*
  클래스 다이어그램 데이터
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
    private int x;
    private int y;
    private int w;
    private int h;

    public ClazzModel(){
        className = "";
        attributeList = new ArrayList<>(0);
        methodList = new ArrayList<>(0);
        x=0;
        y=0;
        w=0;
        h=0;
    }


    public ClazzModel(String className, ArrayList<String> att, ArrayList<String> met, int x, int y, int w, int h){
        this.className = className;
        this.attributeList = att;
        this.methodList = met;
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
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

}
