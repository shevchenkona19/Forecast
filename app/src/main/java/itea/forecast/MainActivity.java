package itea.forecast;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements lvCitiesAdapter.iShowLoading {
    private ListView lvCities;
    private FloatingActionButton fabAddCity;
    private lvCitiesAdapter citiesAdapter;
    private final int REQUESTCODE_ADDCITY = 1212;
    private ImageView ivEmptyPic;
    private ImageButton ibRefresh;
    private ProgressBar pbRefreshLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCities = (ListView) findViewById(R.id.lvCities);
        fabAddCity = (FloatingActionButton) findViewById(R.id.fabAddCity);
        ivEmptyPic = (ImageView) findViewById(R.id.ivEmptyPick);
        ibRefresh = (ImageButton) findViewById(R.id.ibRefresh);
        pbRefreshLoading = (ProgressBar) findViewById(R.id.pbRefreshLoading);

        pbRefreshLoading.setVisibility(View.GONE);

        citiesAdapter = new lvCitiesAdapter(this, R.layout.list_city_each_item, this);
        lvCities.setAdapter(citiesAdapter);

        lvCities.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, CityActivity.class);
            Bundle b = new Bundle();
            POJOCity pojoCity = new POJOCity(citiesAdapter.getList().get(i).getCityName(),
                    citiesAdapter.getList().get(i).getCityObj().toString(),
                    citiesAdapter.getList().get(i).getCityImage(), citiesAdapter.getList().get(i).getWoeid());
            Log.d("MY", "NAME: " + citiesAdapter.getList().get(i).getCityName());
            b.putParcelable("CITY", pojoCity);
            intent.putExtra("BUNDLE", b);
            startActivity(intent);
        });


        fabAddCity.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
            startActivityForResult(intent, REQUESTCODE_ADDCITY);
        });

        if (citiesAdapter.getCount() == 0) {
            ivEmptyPic.setVisibility(View.VISIBLE);
            lvCities.setVisibility(View.GONE);
            ivEmptyPic.setImageDrawable(getResources().getDrawable(R.drawable.lv_empty_pic));

        } else {
            lvCities.setVisibility(View.VISIBLE);
            ivEmptyPic.setVisibility(View.GONE);
            ivEmptyPic.setImageDrawable(null);
        }

        ivEmptyPic.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
            startActivityForResult(intent, REQUESTCODE_ADDCITY);
        });

        ibRefresh.setOnClickListener(view -> citiesAdapter.refresh());

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

    @Override
    protected void onResume() {
        super.onResume();
        if (citiesAdapter.getCount() == 0) {
            ivEmptyPic.setVisibility(View.VISIBLE);
            lvCities.setVisibility(View.GONE);
            ivEmptyPic.setImageDrawable(getResources().getDrawable(R.drawable.lv_empty_pic));

        } else {
            ivEmptyPic.setVisibility(View.GONE);
            lvCities.setVisibility(View.VISIBLE);
            ivEmptyPic.setImageDrawable(null);
        }
    }

    @Override
    public void showProgressBar() {
        pbRefreshLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pbRefreshLoading.setVisibility(View.GONE);
    }
}
