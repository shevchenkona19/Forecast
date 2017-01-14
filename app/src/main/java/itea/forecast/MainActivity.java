package itea.forecast;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Calendar;
import java.util.logging.StreamHandler;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private ListView lvCities;
    private FloatingActionButton fabAddCity;
    private lvCitiesAdapter citiesAdapter;
    private final int REQUESTCODE_ADDCITY = 1212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCities = (ListView) findViewById(R.id.lvCities);
        fabAddCity = (FloatingActionButton) findViewById(R.id.fabAddCity);

        citiesAdapter = new lvCitiesAdapter(this, R.layout.list_city_each_item);
        lvCities.setAdapter(citiesAdapter);



        fabAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivityForResult(intent, REQUESTCODE_ADDCITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_ADDCITY && resultCode == RESULT_OK) {
            Bundle b = data.getExtras().getBundle("BUNDLE");
            POJOCity pojoCity = b.getParcelable("CITY");
            citiesAdapter.updateList(pojoCity);
        }
    }

}
