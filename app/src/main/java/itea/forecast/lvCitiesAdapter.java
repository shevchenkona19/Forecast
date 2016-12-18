package itea.forecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shevc on 14.12.2016.
 */

public class lvCitiesAdapter extends ArrayAdapter<POJOCity> {
    private List<POJOCity> list;
    private Context context;
    private LayoutInflater inflater;
    private POJOCity pojoCity;
    private int currPos;

    public lvCitiesAdapter(Context context, int resource) {
        super(context, resource);
        list = new ArrayList<>();
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        currPos = -1;
    }

    public void setPojoCity(POJOCity pojoCity){
        this.pojoCity = pojoCity;
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
        ImageView ivBackground  = (ImageView) view.findViewById(R.id.ivBackground);
        TextView tvCityName = (TextView) view.findViewById(R.id.tvCityName);
        TextView tvCityTemp = (TextView) view.findViewById(R.id.tvCityTemp);

        if(position >= currPos) {
            tvCityName.setText(pojoCity.getCityName());
            tvCityTemp.setText(pojoCity.getCityTemp());
            /*Picasso.with(context)
                    .load()
                    .into(ivBackground);
                    */
        }
        return view;
    }

}
