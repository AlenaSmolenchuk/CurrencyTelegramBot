import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element; 
  
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
 

public class Parser {

     private static String url = "http://www.finmarket.ru/currency/rates/";
     public static ArrayList<Course> Courses = new ArrayList<Course>();

     public static void parseUrl() throws IOException {
         Document page = getPage();
         Element tableKrs = page.select("table[class=karramba]").first();
         Elements values = tableKrs.select("tr[class]");
         int index = 0;
         Courses.clear();
         setValues(values , index);
     } 

     private static Document getPage() throws IOException {
         Document page = Jsoup.parse(new URL(url), 4000);
         return page;
     }

     public static void setValues(Elements values, int index) {
         for (int i = 0; i < 33; i++) {
            Element valueLine = values.get(index + i);
            Course cource = new Course();
            cource.setCode(valueLine.select("td").get(0).text());
            cource.setcurrencyName(valueLine.select("td").get(1).text());
            cource.setAmount(valueLine.select("td").get(2).text());
            cource.setCourse(valueLine.select("td").get(3).text());
            cource.setChange(valueLine.select("td").get(4).text());
            Courses.add(cource);
        }
     }
 
     public static String getCourse(String currencyCode){
         return Courses.stream().filter(course -> currencyCode.equals(course.getCode()))
                 .findFirst()
                 .get()
                 .getCourse();
     }

     public static ArrayList<String> getCurrencyCodes() {
         ArrayList<String> currencyCodes = new ArrayList<String>();
         for (int i=0; i < Courses.size(); i++) {
            currencyCodes.add(Courses.get(i).getCode());
         }
         return currencyCodes;
     }

     public static void main(String[] args) throws IOException {
         Document page = getPage();
         Element tableKrs = page.select("table[class=karramba]").first();
         Elements values = tableKrs.select("tr[class]");
     }
}
