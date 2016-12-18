package itea.forecast;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shevc on 14.12.2016.
 */
public class POJOCity implements Parcelable{
    private String cityName;
    private String cityTemp;
    private String cityHtml;

    public POJOCity(String cityName, String cityTemp, String cityHtml) {
        this.cityName = cityName;
        this.cityTemp = cityTemp;
        this.cityHtml = cityHtml;
    }

    protected POJOCity(Parcel in) {
        cityName = in.readString();
        cityTemp = in.readString();
        cityHtml = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cityName);
        parcel.writeString(cityTemp);
        parcel.writeString(cityHtml);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityTemp() {
        return cityTemp;
    }

    public void setCityTemp(EditorWeather editorWeather) {

    }

    public String getCityHtml() {
        return cityHtml;
    }

    public void setCityHtml(String cityHtml) {
        this.cityHtml = cityHtml;
    }
}
