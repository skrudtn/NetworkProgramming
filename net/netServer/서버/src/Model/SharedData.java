package Model;

import java.util.*;

/**
 * Created by skrud on 2017-11-22.
 */
public class SharedData {
    private static volatile SharedData INSTANCE = null;

    private Vector<Event> events;
    private Vector<Integer> repoNos;
    private SharedData(){
        events = new Vector<>();
        repoNos = new Vector<>();
    }

    public static SharedData getInstance(){
        if(INSTANCE ==null){
            synchronized (LoginInfo.class){
                if(INSTANCE ==null){
                    INSTANCE = new SharedData();
                }
            }
        }
        return INSTANCE;
    }

    public void addEvent(Event event){
        events.add(event);
    }
    public void removeEvent(Event event){
        events.remove(event);
    }

    public ArrayList<Event> getEvents(String id) {
        ArrayList<Event> rtEvents = new ArrayList<>();
        for(Event event: events){
            if(event.getDes().equals(id)){
                rtEvents.add(event);
            }
        }
        events.removeAll(rtEvents);
        return rtEvents;
    }

    public boolean isCloneRepo(Integer repoNo) {
        boolean rt = false;
        for(Integer i:repoNos){
            if(i.equals(repoNo)){
                rt = true;
            }
        }
        return rt;
    }
    public void addCloneRepo(Integer repoNo){
        repoNos.add(repoNo);
        displayRepoNos();
    }
    public void removeCloneRepo(Integer repoNo){
        repoNos.remove(repoNo);
        displayRepoNos();
    }

    void displayRepoNos(){
        System.out.println("Repos ---");
        for(Integer i:repoNos){
            System.out.println(i);
        }
        System.out.println("---");
    }
}
