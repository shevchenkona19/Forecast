package itea.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by shevc on 14.12.2016.
 */
public class POJOCity implements Parcelable{
    private String cityName;
    private JSONObject cityObj;

    public POJOCity(String cityName, JSONObject cityObj) {
        this.cityName = cityName;
        this.cityObj = cityObj;
    }

    protected POJOCity(Parcel in) {
        cityName = in.readString();
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
        return cityObj;
    }

    public void setCityObj(JSONObject cityObj) {
        this.cityObj = cityObj;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cityName);
    }
}
