package itea.forecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.test.tudou.library.WeekPager.adapter.WeekPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shevc on 19.01.2017.
 */

public class CardViewPagerAdapter extends WeekPagerAdapter {

    private List<CardFragment> list;
    private POJOCity city;


    public CardViewPagerAdapter(FragmentManager fm, POJOCity city) {
        super(fm);
        this.city = city;
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(CardFragment.newInstance(city, i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("MY", String.valueOf(position));
        return list.get(position);
    }

    @Override
    protected Fragment createFragmentPager(int i) {
        return CardFragment.newInstance(city, i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
