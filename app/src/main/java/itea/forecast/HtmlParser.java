package itea.forecast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by shevc on 14.12.2016.
 */

public class HtmlParser {

    public ArrayList<String> getTempForFiveDays(String html) {
        ArrayList<String> tempList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(html).get();
            Elements elements = doc.select("div.temp");
            for (Element el : elements) {
                tempList.add(el.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempList;
    }

    public String getCurrentDay(String html) {
        String day = "";
        try {
            Document doc = Jsoup.connect(html).get();
            Elements elements = doc.select("div.currentSelectDay");
            for (Element el : elements) {
                day = el.text();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public ArrayList<String> getNameOfDays(String html) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(html).get();
            Elements elements = doc.select("span.dayoffWeek");
            for (Element element : elements) {
                list.add(element.text());
            }
            elements.clear();
            elements = doc.select("span.dayoffMonth");
            for (Element element : elements){
                list.add(element.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getWeatherForHours(String html, StringBuffer stringBuffer){
        try {
            Document doc = Jsoup.connect(html).get();
            Elements elements = doc.select("tr.colorRow");
            for (Element element : elements){
                stringBuffer.append(element.text());
                stringBuffer.append(" ");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
