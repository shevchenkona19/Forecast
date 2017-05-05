package itea.forecast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shevc on 14.12.2016.
 */

public class lvCitiesAdapter extends ArrayAdapter<POJOCity> {
    private List<POJOCity> list;
    private Context context;
    private LayoutInflater inflater;
    iShowLoading showLoading;

    interface iShowLoading {
        void showProgressBar();

        void hideProgressBar();

    }

    private static String TAG = lvAddCityAdapter.class.getSimpleName();

    public lvCitiesAdapter(Context context, int resource, iShowLoading showLoading) {
        super(context, resource);
        list = new ArrayList<>();
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showLoading = showLoading;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public List<POJOCity> getList() {
        return list;
    }

    @Nullable
    @Override
    public POJOCity getItem(int position) {
        return list.get(position);
    }

    public void updateList(ArrayList<POJOCity> pojoCity) {
        list.clear();
        list.addAll(pojoCity);
        notifyDataSetChanged();
    }

    public void updateList(POJOCity city) {
        list.add(city);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_city_each_item, parent, false);
        }

        TextView tvCityName = (TextView) view.findViewById(R.id.tvCityName);
        TextView tvCityTemp = (TextView) view.findViewById(R.id.tvCityTemp);

        final ImageView ivBackground = (ImageView) view.findViewById(R.id.ivBackground);
        final ProgressBar pbCityLoading = (ProgressBar) view.findViewById(R.id.pbCityLoading);

        tvCityName.setText(list.get(position).getCityName());
        if (list.get(position).getCityObj() == null) {
            Log.e(TAG, "City object is null");
        }
        Picasso.with(context)
                .load(list.get(position).getCityImage())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        ivBackground.setImageBitmap(new BlurMaker().blur(context, bitmap));
                        pbCityLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        ivBackground.setImageDrawable(errorDrawable);
                        pbCityLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        pbCityLoading.setVisibility(View.VISIBLE);
                    }
                });
        tvCityTemp.setText(
                JSONWeatherParser.getInstance()
                        .getCurrentTemp(
                                list.get(position).getCityObj()
                        ) + "°C, " +
                        JSONWeatherParser.getInstance()
                                .getCondition(list.get(position).getCityObj())
        );

        return view;
    }

    public void refresh() {
        Refresh ref = new Refresh();
        ref.execute(getList());
    }

    private class Refresh extends AsyncTask<List<POJOCity>, Void, ArrayList<POJOCity>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading.showProgressBar();
        }

        @Override
        protected ArrayList<POJOCity> doInBackground(List<POJOCity>... lists) {
            Log.d(TAG, "Starting a refreshTask");
            ArrayList<POJOCity> list = new ArrayList<>();
            boolean success;
            for (POJOCity city : lists[0]) {
                try {
                    Log.d(TAG, "Sending refresh request for " + city.getCityName());
                    JSONObject cityObj;
                    do {
                        cityObj = new JSONObject(HttpHandler.getInstance().SendRequestToUrl(Integer.valueOf(city.getWoeid())));
                        success = JSONWeatherParser.getInstance().isSuccess(cityObj);
                    } while (success == false);

                    if (cityObj == null) {
                        Log.e(TAG, "JSONObj is null!");
                    }
                    city.setCityObj(cityObj);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                list.add(city);
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<POJOCity> pojoCities) {
            super.onPostExecute(pojoCities);
            Log.d(TAG, "Calling update method");
            updateList(pojoCities);
            showLoading.hideProgressBar();
        }
    }

}

