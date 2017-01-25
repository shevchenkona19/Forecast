package itea.forecast;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shevc on 19.01.2017.
 */

public class CardViewPagerAdapter extends PagerAdapter{

    private final List<CardView> list;
    private Context context;
    private POJOCity pojoCity;

    public CardViewPagerAdapter(Context context, POJOCity pojoCity) {
        this.pojoCity = pojoCity;
        this.context = context;
        list = new ArrayList<>();
        for (int i = 0; i < 10; i ++){
            list.add(new CardView(context));
        }
    }

    public CardView getCardAt(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.weather_card, container, false);
        container.addView(view);
        JSONObject object = pojoCity.getCityObj();
        TextView tvDay = (TextView) view.findViewById(R.id.tvCardNameOfDay);
        TextView tvDate = (TextView) view.findViewById(R.id.tvCardDate);
        TextView tvHigh = (TextView) view.findViewById(R.id.tvCardHigh);
        TextView tvLow = (TextView) view.findViewById(R.id.tvCardLow);
        tvDay.setText(JSONWeatherParser.getInstance().getNameOfDay(position, object));
        tvDate.setText(JSONWeatherParser.getInstance().getDate(position, object));
        tvHigh.setText(String.valueOf(JSONWeatherParser.getInstance().getHigh(position, object)) + "°C");
        tvLow.setText(String.valueOf(JSONWeatherParser.getInstance().getLow(position, object)) + "°C");
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
