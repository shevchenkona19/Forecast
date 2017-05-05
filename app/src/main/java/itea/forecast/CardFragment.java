package itea.forecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by shevc on 30.01.2017.
 */

public class CardFragment extends Fragment {

    private static final String TAG = CardFragment.class.getSimpleName();

    private CardView weatherCard;
    private POJOCity city2;
    private int count;

    public static CardFragment newInstance(POJOCity city, int count) {
        if (city == null) {
            Log.e(TAG, "NEW INSTANCE: City is null");
        }
        CardFragment fragment = new CardFragment();

        fragment.city2 = city;
        fragment.count = count;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_card, container, false);
        weatherCard = (CardView) view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvHigh = (TextView) view.findViewById(R.id.tvCardHigh);
        TextView tvLow = (TextView) view.findViewById(R.id.tvCardLow);
        TextView tvDay = (TextView) view.findViewById(R.id.tvCardNameOfDay);
        if (city2 == null) {
            Log.e(TAG, "City is null");
        }
        if (city2.getCityObj() == null) {
            Log.e(TAG, "Json is null");
        }
        if (count != 0) {
            tvHigh.setText(String.valueOf(JSONWeatherParser.getInstance().getHigh(count - 1, city2.getCityObj())));
            tvLow.setText(String.valueOf(JSONWeatherParser.getInstance().getLow(count - 1, city2.getCityObj())));
            tvDay.setText(JSONWeatherParser.getInstance().getNameOfDay(count - 1, city2.getCityObj()));
        } else {
            tvDay.setText("No weather for today");
            tvHigh.setText("");
            tvLow.setText("");
        }
    }

    public CardView getWeatherCard() {
        return weatherCard;
    }
}
