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
    private View view;
    private ISendData sendData;
    private JSONObject obj;
    private int currPos;
    private ProgressBar pbLoading;
    private EditText etSearch;

    private static final String TAG = lvAddCityAdapter.class.getSimpleName();

    interface ISendData{
        void getData(POJOCity city);
    }

    public lvAddCityAdapter(Context context, int resource, ISendData sendData, ProgressBar pbLoading, EditText etSearch){
        super(context, resource);
        this.etSearch = etSearch;
        this.pbLoading = pbLoading;
        this.sendData = sendData;
        this.context = context;
        list = new ArrayList<>();
        obj = null;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void updateList(List<String> list2){
        list.clear();
        list.addAll(list2);
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.add_city_each_item, parent, false);
        }
        final TextView tvCityName = (TextView) view.findViewById(R.id.tvAddCityName);
        Button btnAdd = (Button) view.findViewById(R.id.btnAddCity);
        tvCityName.setText(list.get(position));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setVisibility(View.GONE);
                currPos = position;
                GetJSONCityObj runn = new GetJSONCityObj();
                for (int a = 0; a<CityNames.cityNames.length;a++){
                    if (CityNames.cityNames[a].equals(list.get(position))){
                        runn.execute(CityNames.cityNames[a+1]);
                        Log.d("MY", "WOEID: " + CityNames.cityNames[a+1]);
                        break;
                    }
                }

            }
        });

        return view;
    }

    private class GetJSONCityObj extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject object = null;
            try {
                object = new JSONObject(HttpHandler.getInstance().SendRequestToUrl(Integer.valueOf(strings[0])));
                Log.d("MY", "JSON OBJ FROM SERVER: " + object.toString());
            } catch (JSONException e){
                Log.e(TAG, e.getMessage());
            }
            return object;
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            super.onPostExecute(object);
            etSearch = null;
            obj = object;
            POJOCity city = new POJOCity(list.get(currPos), obj);
            pbLoading.setVisibility(View.GONE);
            pbLoading = null;
            sendData.getData(city);
        }
    }
}