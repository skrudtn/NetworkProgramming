package Control;

import Model.Event;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-22.
 */
public class EventsController {
    private MainController controller;
    private ArrayList<Event> events;
    EventsController(MainController controller){
        events =new ArrayList<>();
        this.controller = controller;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
    public void addEvent(Event e){
        events.add(e);
        display();
    }
    public void removeEvent(Event e){
        events.remove(e);
    }
    public void display(){
        for(Event e: events){
            System.out.println(e.getSrc()+" -> "+e.getDes());
        }
    }
}
