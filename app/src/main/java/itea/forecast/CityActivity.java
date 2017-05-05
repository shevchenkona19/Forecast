package itea.forecast;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.test.tudou.library.WeekPager.adapter.WeekViewAdapter;
import com.test.tudou.library.WeekPager.view.WeekDayViewPager;
import com.test.tudou.library.WeekPager.view.WeekRecyclerView;
import com.test.tudou.library.model.CalendarDay;
import com.test.tudou.library.util.DayUtils;

import java.util.Calendar;

public class CityActivity extends AppCompatActivity {

    private static final String TAG = CityActivity.class.getSimpleName();

    private ImageView ivBackground;
    private TextView tvCityName;
    private TextView tvCityTemp;
    private ProgressBar pbLoading;
    private TextView tvAppBarSlidePanelLabel;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private WeekDayViewPager mPagerContent;
    private WeekRecyclerView weekRecyclerView;
    private TextView tvDateDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ivBackground = (ImageView) findViewById(R.id.ivCityActivityBackground);
        tvCityName = (TextView) findViewById(R.id.tvCityActivityCityName);
        tvCityTemp = (TextView) findViewById(R.id.tvCityActivityTemp);
        pbLoading = (ProgressBar) findViewById(R.id.pbCityActivityLoading);
        tvAppBarSlidePanelLabel = (TextView) findViewById(R.id.tvAppBarSlidePanelLabel);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidePanel);
        mPagerContent = (WeekDayViewPager) findViewById(R.id.vpPager);
        weekRecyclerView = (WeekRecyclerView) findViewById(R.id.wrvHeader);
        tvDateDisplay = (TextView) findViewById(R.id.tvDateDisplay);

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                switch (newState) {
                    case EXPANDED:
                        tvAppBarSlidePanelLabel.setText("");
                        break;
                    case COLLAPSED:
                        tvAppBarSlidePanelLabel.setText("Weather for 10 days");
                        break;
                }
            }
        });


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Bundle b = getIntent().getExtras().getBundle("BUNDLE");
        if (b == null) {
            Log.e(TAG, "bundle is null");
        }
        POJOCity pojoCity = b.getParcelable("CITY");
        if (pojoCity == null) {
            Log.e(TAG, "PojoCity is null");
        }
        tvCityName.setText(pojoCity.getCityName());
        tvCityTemp.setText(
                JSONWeatherParser.getInstance().getCurrentTemp(pojoCity.getCityObj())
                        + "°C"
        );
        Picasso.with(this)
                .load(pojoCity.getCityImage())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        ivBackground.setImageBitmap(bitmap);
                        pbLoading.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        ivBackground.setImageDrawable(errorDrawable);
                        pbLoading.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        pbLoading.setVisibility(View.VISIBLE);
                    }
                });
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, 0);
        maxDate.add(Calendar.DAY_OF_MONTH, (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 7));


        final CardViewPagerAdapter adapter = new CardViewPagerAdapter(getSupportFragmentManager(), pojoCity);
        mPagerContent.setAdapter(adapter);
        mPagerContent.setOffscreenPageLimit(3);
        mPagerContent.setPageMargin(110);
        mPagerContent.setWeekRecyclerView(weekRecyclerView);
        mPagerContent.setDayScrollListener(new WeekDayViewPager.DayScrollListener() {
            @Override
            public void onDayPageScrolled(int i, float v, int i1) {
                tvDateDisplay.setText(DayUtils.formatEnglishTime(adapter.getDatas().get(i + 1).getTime()));
            }

            @Override
            public void onDayPageSelected(int i) {

            }

            @Override
            public void onDayPageScrollStateChanged(int i) {

            }
        });
        WeekViewAdapter mWeekView = new WeekViewAdapter(this, mPagerContent);
        mWeekView.setTextNormalColor(getResources().getColor(android.R.color.darker_gray));
        weekRecyclerView.setAdapter(mWeekView);
        CalendarDay startDay = new CalendarDay();
        startDay.setDay(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        CalendarDay finishDay = new CalendarDay();
        finishDay.setDay(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 10);
        mWeekView.setData(startDay, finishDay, null);
        adapter.setData(startDay, finishDay);

        mWeekView.setTextUnableColor(getResources().getColor(R.color.color_CCCCCC));

        mWeekView.setIndicatorColor(getResources().getColor(R.color.color_CCCCCC));
        mWeekView.setTextSelectColor(getResources().getColor(R.color.text_color_normal));
        mWeekView.setTextNormalColor(getResources().getColor(R.color.text_color_normal));

    }


}
