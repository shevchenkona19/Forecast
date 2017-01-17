package itea.forecast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by shevc on 14.12.2016.
 */

public class lvCitiesAdapter extends ArrayAdapter<POJOCity> {
    private List<POJOCity> list;
    private Context context;
    private LayoutInflater inflater;
    private int currPos;
    private View view;
    private Bitmap bit;
    private ImageView ivBackground;
    private ProgressBar pbCityLoading;

    private static String TAG = lvAddCityAdapter.class.getSimpleName();

    public lvCitiesAdapter(Context context, int resource) {
        super(context, resource);
        list = new ArrayList<>();
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        currPos = -1;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public List<POJOCity> getList(){
        return list;
    }

    @Nullable
    @Override
    public POJOCity getItem(int position) {
        return list.get(position);
    }

    public void updateList(POJOCity pojoCity){
        this.list.add(pojoCity);
        notifyDataSetChanged();
        currPos +=1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_city_each_item, parent, false);
        }

        ivBackground  = (ImageView) view.findViewById(R.id.ivBackground);
        TextView tvCityName = (TextView) view.findViewById(R.id.tvCityName);
        TextView tvCityTemp = (TextView) view.findViewById(R.id.tvCityTemp);
        pbCityLoading = (ProgressBar) view.findViewById(R.id.pbCityLoading);
        if(position >= currPos) {
            tvCityName.setText(list.get(position).getCityName());
            if (list.get(position).getCityObj() == null){
                Log.e(TAG, "City object is null");
            }
            tvCityTemp.setText(
                    JSONWeatherParser.getInstance()
                    .getCurrentTemp(
                            list.get(position).getCityObj()
                    ) + ", " +
                            JSONWeatherParser.getInstance()
                    .getCondition(list.get(position).getCityObj())
            );
            new BitmapFromURL().execute(list.get(position).getCityImage());


        }
        return view;
    }

    private class BitmapFromURL extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            pbCityLoading.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            pbCityLoading.setVisibility(View.GONE);
            bit = bitmap;
            ivBackground.setImageBitmap(new BlurMaker().blur(context, bitmap));
        }
    }

}
