package itea.forecast;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

        lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                Bundle b = new Bundle();
                POJOCity pojoCity = new POJOCity(citiesAdapter.getList().get(i).getCityName(),
                        citiesAdapter.getList().get(i).getCityObj().toString(),
                        citiesAdapter.getList().get(i).getCityImage());
                Log.d("MY", "NAME: " + citiesAdapter.getList().get(i).getCityName());
                b.putParcelable("CITY", pojoCity);
                intent.putExtra("BUNDLE", b);
                startActivity(intent);
            }
        });


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
