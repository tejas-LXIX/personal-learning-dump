//import java.util.List;
//import com.google.gson.Gson;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//
//public class JSONParsing {
//
//    public static void main(String[] args) throws ClassNotFoundException {
//        List<String> linksList = new ArrayList<>();
//        try {
//            //Class<?> cls = Class.forName("JSONparsing.class");
//            //ClassLoader classLoader = cls.getClassLoader();
//            //JsonReader reader = new JsonReader(new FileReader(classLoader.getResource("links.json").getFile()));
//            JsonReader reader = new JsonReader(new FileReader("C:\\data\\dcv\\sportline_java\\branches\\master\\DCV_crawler_engine\\src\\main\\resources\\com\\dcvsolution\\site\\bongdaplusdotvn\\scrap\\links.json"));
//            reader.beginObject();
//            while (reader.hasNext()) {
//                String value = reader.nextString();
//                linksList.add(value);
//            }
//            reader.endObject();
//            reader.close();
//        } catch (FileNotFoundException fnfe) {
//            fnfe.printStackTrace();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//        System.out.println(linksList.toString());
//    }
//
//}