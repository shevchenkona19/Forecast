package itea.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shevc on 14.12.2016.
 */
public class POJOCity implements Parcelable {
    private String cityName;
    private String cityObj;
    private String cityImage;
    private String woeid;

    private static final String TAG = POJOCity.class.getSimpleName();

    public POJOCity(String cityName, String cityObj, String cityImage, String woeid) {
        this.cityName = cityName;
        this.cityObj = cityObj;
        this.cityImage = cityImage;
        this.woeid = woeid;
    }

    public String getWoeid() {
        return woeid;
    }

    protected POJOCity(Parcel in) {
        cityName = in.readString();
        cityObj = in.readString();
        cityImage = in.readString();
        woeid = in.readString();
    }

    public static final Creator<POJOCity> CREATOR = new Creator<POJOCity>() {
        @Override
        public POJOCity createFromParcel(Parcel in) {
            return new POJOCity(in);
        }

        @Override
        public POJOCity[] newArray(int size) {
            return new POJOCity[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public JSONObject getCityObj() {
        JSONObject object = null;
        try {
            object = new JSONObject(cityObj);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return object;
    }

    public void setCityObj(String cityObj) {
        this.cityObj = cityObj;
    }

    public void setCityObj(JSONObject cityObj) {
        this.cityObj = cityObj.toString();
    }

    public String getCityImage() {
        return cityImage;
    }

    public void setCityImage(String cityImage) {
        this.cityImage = cityImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cityName);
        parcel.writeString(cityObj);
        parcel.writeString(cityImage);
        parcel.writeString(woeid);
    }
}
