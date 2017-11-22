package Control;

import Model.Event;
import Model.VersionModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by skrud on 2017-11-19.
 */
public class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

        String s1 = o1.getDate();
        String s2 = o1.getDate();
        Date d1=null;
        Date d2=null;
        try {
            d1 = transFormat.parse(s1);
            d2 = transFormat.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d1 != null;
        assert d2 != null;
        int comp = d1.compareTo(d2);
        if(comp>0){ // order by asc
            return 1;
        } else if(comp<0){
            return -1;
        } else {
            return 0;
        }
    }
}
