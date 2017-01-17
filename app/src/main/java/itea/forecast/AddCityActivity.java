package itea.forecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends AppCompatActivity implements lvAddCityAdapter.ISendData {
    private EditText etSearchTB;
    private ListView lvSearchResults;
    private lvAddCityAdapter addCityAdapter;
    private List<String> names = new ArrayList<>();
    private ProgressBar pbLoading;

    private static final String TAG = AddCityActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        etSearchTB = (EditText) findViewById(R.id.etSearchTB);
        lvSearchResults = (ListView) findViewById(R.id.lvSearchResults);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);

        addCityAdapter = new lvAddCityAdapter(this, R.layout.add_city_each_item, this, pbLoading, etSearchTB);
        lvSearchResults.setAdapter(addCityAdapter);

        etSearchTB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    return;
                }
                for (int a = 0; a<=CityNames.cityNames.length; a += 3){
                    if(a >= CityNames.cityNames.length-2){
                        continue;
                    }
                    try {
                        if (CityNames.cityNames[a].substring(0, charSequence.length()).equals(charSequence.toString())) {
                            names.add(CityNames.cityNames[a]);
                        }
                    } catch (StringIndexOutOfBoundsException e){
                        Log.e(TAG, e.getMessage());
                    }
                }
                addCityAdapter.updateList(names);
                names.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void sendData(POJOCity city) {
        Log.d("MY", "SendData start...");
        Bundle b = new Bundle();
        b.putParcelable("CITY", city);
        Intent intent = new Intent();
        intent.putExtra("BUNDLE", b);
        setResult(RESULT_OK, intent);
        finish();
    }
}
