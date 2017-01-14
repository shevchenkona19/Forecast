package itea.forecast;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shevc on 13.01.2017.
 */

public class JSONWeatherParser {
    private static JSONWeatherParser parser = null;
    private static final String TAG = JSONWeatherParser.class.getSimpleName();

    private JSONWeatherParser() {
    }

    public static JSONWeatherParser getInstance() {
        if (parser == null) {
            parser = new JSONWeatherParser();
        }
        return parser;
    }

    public String getCityName(JSONObject object) {
        String cityName = null;
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject location = channel.getJSONObject("location");
            cityName = location.getString("city");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return cityName;
    }

    public String getCountryName(JSONObject object) {
        String countryName = null;
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject location = channel.getJSONObject("location");
            countryName = location.getString("country");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return countryName;
    }

    public String getCurrentTemp(JSONObject object) {
        String currWeather = null;
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject item = channel.getJSONObject("item");
            JSONObject condition = item.getJSONObject("condition");
            currWeather = condition.getString("temp");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return currWeather;
    }
}
