import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateTest {

    static List<SimpleDateFormat> formats = Arrays.asList(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US), new SimpleDateFormat("M/d/yy h:mm a", Locale.US));


    public static void main(String[] args) throws ParseException {

        String stresr = new SimpleDateFormat().format(new Date());

        //System.setProperty("","");
//        TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
        Locale.setDefault(new Locale("en", "US"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String dateInString = "18-Aug-2023";


        String str = new SimpleDateFormat().format(formatter.parse(dateInString));  //works only in java 8. str is 8/18/23, 12:00 AM in java 17.

        String str1 = new SimpleDateFormat("M/d/yy h:mm a", Locale.US).format(formatter.parse(dateInString));    // works in java 8 and java 17

        System.out.println(getDateFromString(str));
        System.out.println(getDateFromString(str1));


        Date date = new SimpleDateFormat().parse(str);
        Date date1 = new SimpleDateFormat("M/d/yy h:mm a").parse(str);

        Date date2 = new SimpleDateFormat().parse(str1);
        Date date3 = new SimpleDateFormat("M/d/yy h:mm a").parse(str1);

        System.out.println(date);
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);

    }


    public static Date getDateFromString(String dateString) {
        for (SimpleDateFormat format : formats) {
            try {
                return format.parse(dateString);
            } catch (Exception e) {
            }
        }
        return null;
    }

}