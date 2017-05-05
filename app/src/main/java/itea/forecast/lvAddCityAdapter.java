package itea.forecast;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shevc on 14.01.2017.
 */

public class lvAddCityAdapter extends ArrayAdapter<String> {
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private iAdapterUtils adapterUtils;
    private JSONObject obj;
    private int currPos;
    private Toast errorMsg;

    private static final String TAG = lvAddCityAdapter.class.getSimpleName();

    interface iAdapterUtils {
        void sendData(POJOCity city);

        void blockInput();

        void showLoading();

        void hideLoading();
    }

    public lvAddCityAdapter(Context context, int resource, iAdapterUtils adapterUtils) {
        super(context, resource);
        this.adapterUtils = adapterUtils;
        this.context = context;
        list = new ArrayList<>();
        obj = null;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        errorMsg = Toast.makeText(context, "Ooops! Server error, trying again", Toast.LENGTH_SHORT);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void updateList(List<String> list2) {
        list.clear();
        list.addAll(list2);
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.add_city_each_item, parent, false);
        }
        final TextView tvCityName = (TextView) view.findViewById(R.id.tvAddCityName);
        Button btnAdd = (Button) view.findViewById(R.id.btnAddCity);
        tvCityName.setText(list.get(position));
        btnAdd.setOnClickListener(view1 -> {
            adapterUtils.blockInput();
            currPos = position;
            GetJSONCityObj runn = new GetJSONCityObj();
            for (int a = 0; a < CityNames.cityNames.length; a++) {
                if (CityNames.cityNames[a].equals(list.get(position))) {
                    runn.execute(CityNames.cityNames[a + 1], CityNames.cityNames[a + 2]);
                    Log.d("MY", "WOEID: " + CityNames.cityNames[a + 1]);
                    break;
                }
            }

        });

        return view;
    }

    private class GetJSONCityObj extends AsyncTask<String, Void, POJOCity> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapterUtils.showLoading();
        }

        @Override
        protected POJOCity doInBackground(String... strings) {
            JSONObject object = null;
            POJOCity pojoCity;
            try {
                boolean success;
                do {
                    object = new JSONObject(HttpHandler.getInstance().SendRequestToUrl(Integer.valueOf(strings[0])));
                    Log.d("MY", "JSON OBJ FROM SERVER: " + object.toString());
                    success = JSONWeatherParser.getInstance().isSuccess(object);
                    if (success == false) {
                        errorMsg.show();
                    }
                } while (success == false);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
            pojoCity = new POJOCity(list.get(currPos), object.toString(), strings[1], strings[0]);
            if (pojoCity.getCityObj() == null) {
                Log.e(TAG, "City object is null");
            }
            return pojoCity;
        }

        @Override
        protected void onPostExecute(POJOCity pojoCity) {
            if (pojoCity.getCityObj() == null) {
                Log.e(TAG, "City object is null");
            }
            super.onPostExecute(pojoCity);
            adapterUtils.hideLoading();
            adapterUtils.sendData(pojoCity);
        }
    }
}
