package itea.forecast;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView lvCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCities = (ListView) findViewById(R.id.lvCities);

        HtmlParse htmlParse = new HtmlParse();
        htmlParse.execute("");
    }

    private class HtmlParse extends AsyncTask<String, Void, Void>{
        private HtmlParser htmlParser;

        @Override
        protected Void doInBackground(String... strings) {
            htmlParser = new HtmlParser();
            htmlParser.getTempForFiveDays("https://www.meteoprog.ua/ru/weather/Kyiv/");
            htmlParser.getNameOfDays("https://www.meteoprog.ua/ru/weather/Kyiv/");
            htmlParser.getWeatherForHours("https://www.meteoprog.ua/ru/meteograms/Kyiv/", new StringBuffer());
            htmlParser.getCurrentDay("https://www.meteoprog.ua/ru/meteograms/Kyiv/");
            return null;
        }
    }
}
