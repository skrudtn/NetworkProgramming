package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by skrud on 2017-11-22.
 */
public class SharedData {
    private static volatile SharedData INSTANCE = null;

    private ArrayList<Event> events;
    private SharedData(){
        events = new ArrayList<>();
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
        System.out.println("addEvents");
        System.out.println(event.getType());
        System.out.println(event.getSrc());
        System.out.println(event.getDes());
        events.add(event);
    }
    public void removeEvent(Event event){
        events.remove(event);
    }

    public ArrayList<Event> getEvents(String id) {
        System.out.println("getEvents");
        System.out.println(id);
        ArrayList<Event> rtEvents = new ArrayList<>();
        for(Event event: events){
            if(event.getDes().equals(id)){
                rtEvents.add(event);
            }
        }
        return rtEvents;
    }
}
