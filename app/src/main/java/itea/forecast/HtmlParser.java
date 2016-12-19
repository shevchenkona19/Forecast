package itea.forecast;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by shevc on 14.12.2016.
 */
/*
 */
public class HtmlParser {

    /*
    Я еще не понял, что именно этот метод тащит - с ним я еще разберусь
    12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -1°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: +1°C   1
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: +1°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C      2
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: +1°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C      3
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -3°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -3°C     4
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: +1°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: +1°C     5
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: +1°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -5°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -5°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -1°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -4°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -4°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -1°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -4°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: -4°C
12-19 09:16:54.861 15486-15526/itea.forecast D/My Tag: 0°C
12-19 09:16:54.862 15486-15526/itea.forecast D/My Tag: 0°C
     */

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
        for (String s : tempList){
            Log.d("My Tag", s);
        }
        return tempList;
    }

    /*
    12-19 09:16:55.664 15486-15526/itea.forecast D/My Tag: вторник, 20 декабря
     */

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
        Log.d("My Tag", day);
        return day;
    }
    /*
    2-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: Сегодня
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: Завтра
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: среда
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: четверг
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: пятница
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: 19 декабря
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: 20 декабря
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: 21 декабря
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: 22 декабря
12-19 09:16:55.005 15486-15526/itea.forecast D/My Tag: 23 декабря
     */

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
        for (String s : list) {
            Log.d("My Tag",s );
        }
        return list;
    }

    //00:00 -3°C -4°C 0 мм 757 мм рт. ст. 94% 2 м/с 02:00 -3°C -5°C 0 мм 756 мм рт. ст. 93% 3 м/с 04:00 -3°C -5°C 0 мм 756 мм рт. ст. 94% 3 м/с 06:00 -2°C -4°C 0 мм 756 мм рт. ст. 94% 3 м/с 08:00 -2°C -4°C 0 мм 755 мм рт. ст. 95% 4 м/с 10:00 -1°C -3°C 0,1 мм 755 мм рт. ст. 94% 4 м/с 12:00 0°C -2°C 0 мм 755 мм рт. ст. 94% 4 м/с 14:00 +1°C -1°C 0 мм 755 мм рт. ст. 96% 4 м/с 16:00 +1°C -1°C 0,2 мм 755 мм рт. ст. 97% 4 м/с 18:00 0°C -2°C 0,3 мм 754 мм рт. ст. 97% 4 м/с 20:00 0°C -2°C 0,3 мм 754 мм рт. ст. 97% 4 м/с 22:00 0°C -2°C 0,2 мм 754 мм рт. ст. 97% 4 м/с 00:00 0°C -1°C 0,2 мм 754 мм рт. ст. 97% 3 м/с 02:00 0°C -1°C 0,2 мм 755 мм рт. ст. 97% 3 м/с 04:00 0°C -1°C 0,1 мм 755 мм рт. ст. 98% 3 м/с 06:00 0°C -1°C 0 мм 756 мм рт. ст. 98% 2 м/с 08:00 0°C -1°C 0 мм 757 мм рт. ст. 98% 2 м/с 10:00 0°C -1°C 0 мм 758 мм рт. ст. 97% 2 м/с 12:00 0°C -1°C 0 мм 759 мм рт. ст. 96% 2 м/с 14:00 0°C +1°C 0 мм 760 мм рт. ст. 95% 1 м/с 16:00 -2°C -1°C 0 мм 761 мм рт. ст. 96% 1 м/с 18:00 -4°C -3°C 0 мм 761 мм рт. ст. 97% 1 м/с 20:00 -4°C -3°C 0 мм 762 мм рт. ст. 97% 1 м/с 22:00 -5°C -4°C 0 мм 763 мм рт. ст. 96% 1 м/с 00:00 -5°C -4°C 0 мм 763 мм рт. ст. 96% 1 м/с 02:00 -5°C -5°C 0 мм 764 мм рт. ст. 95% 0 м/с 04:00 -5°C -4°C 0 мм 763 мм рт. ст. 95% 1 м/с 06:00 -5°C -4°C 0 мм 763 мм рт. ст. 95% 1 м/с 08:00 -5°C -4°C 0 мм 764 мм рт. ст. 96% 1 м/с 10:00 -3°C -2°C 0 мм 764 мм рт. ст. 92% 1 м/с 12:00 -1°C 0°C 0 мм 763 мм рт. ст. 91% 1 м/с 14:00 -1°C 0°C 0 мм 763 мм рт. ст. 93% 1 м/с 16:00 -2°C -1°C 0 мм 763 мм рт. ст. 95% 1 м/с 18:00 -3°C -4°C 0 мм 762 мм рт. ст. 96% 2 м/с 20:00 -4°C -5°C 0 мм 762 мм рт. ст. 95% 2 м/с 22:00 -4°C -5°C 0 мм 762 мм рт. ст. 94% 2 м/с

    public ArrayList<String> getWeatherForHours(String html){
        Log.d("My Tag", "start");
        ArrayList<String> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(html).get();
            Elements elements = doc.select("tr.colorRow");
            for (Element element : elements){
               list.add(element.text());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        for (String s : list) {
            Log.d("My Tag",s );
        }
        return list;
    }
}
