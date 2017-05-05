package itea.forecast;

import android.util.Log;

import org.json.JSONArray;
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

    public String getCondition(JSONObject object) {
        String cond = null;
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject item = channel.getJSONObject("item");
            JSONObject condition = item.getJSONObject("condition");
            cond = condition.getString("text");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return cond;
    }

    public Boolean isSuccess(JSONObject object) {
        boolean res = false;
        int count = 0;
        try {
            JSONObject query = object.getJSONObject("query");
            count = query.getInt("count");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        if (count != 0) {
            res = true;
        }
        return res;
    }

    public String getNameOfDay(int number, JSONObject object) {
        String s = "";
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject item = channel.getJSONObject("item");
            JSONArray forecast = item.getJSONArray("forecast");
            s = forecast.getJSONObject(number).getString("day");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return s;
    }

    public String getDate(int number, JSONObject object) {
        String s = "";
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject item = channel.getJSONObject("item");
            JSONArray forecast = item.getJSONArray("forecast");
            s = forecast.getJSONObject(number).getString("date");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return s;
    }

    public int getHigh(int number, JSONObject object) {
        int high = 0;
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject item = channel.getJSONObject("item");
            JSONArray forecast = item.getJSONArray("forecast");
            high = forecast.getJSONObject(number).getInt("high");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return high;
    }

    public int getLow(int number, JSONObject object) {
        int low = 0;
        try {
            JSONObject query = object.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject item = channel.getJSONObject("item");
            JSONArray forecast = item.getJSONArray("forecast");
            low = forecast.getJSONObject(number).getInt("low");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return low;
    }
}
