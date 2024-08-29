import java.util.Calendar;
import java.util.Date;

public class DateCalendarRoughTest {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
//        cal.setTime(new Date(2022,4,17,13,26,45));
        cal.setTime(date);
//        cal.set(Calendar.DAY_OF_WEEK,0);
        System.out.println(cal.toString());
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//        Date date = new Date(2022,4,17,13,26,45);
        System.out.println(date.getDay());

        //in Calendar, Sunday is 1, whereas in date.getDay(), Sunday is 0. So, in cca-platform-be-parent, I have to subtract 1 from the cal.get() to maintain consistency.
    }
}

