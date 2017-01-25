package itea.forecast;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.agenda.AgendaHeaderView;
import com.github.tibolte.agendacalendarview.calendar.CalendarView;
import com.github.tibolte.agendacalendarview.calendar.weekslist.WeekListView;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CityActivity extends AppCompatActivity{

    private static final String TAG = CityActivity.class.getSimpleName();

    private ImageView ivBackground;
    private TextView tvCityName;
    private TextView tvCityTemp;
    private ProgressBar pbLoading;
    private TextView tvAppBarSlidePanelLabel;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private ViewPager vpCardPager;
    private CardViewPagerAdapter viewPagerAdapter;
    private CalendarView cvCalendar;

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
        vpCardPager = (ViewPager) findViewById(R.id.vpCardPager);
        cvCalendar = (CalendarView) findViewById(R.id.cvCalendar);


        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                switch (newState){
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
        if (b == null){
            Log.e(TAG, "bundle is null");
        }
        POJOCity pojoCity = b.getParcelable("CITY");
        if (pojoCity == null){
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
        List<CalendarEvent> list = new ArrayList<>();
        minDate.add(Calendar.DAY_OF_MONTH, 0);
        maxDate.add(Calendar.DAY_OF_MONTH, (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +7));
        if (minDate == null|| minDate.equals(null)){
            Log.e(TAG, "mindate is null");
        }
        if (maxDate == null || maxDate.equals(null)){
            Log.e(TAG, "maxdate is null");
        }
        CalendarManager manager = CalendarManager.getInstance(this);
        manager.buildCal(minDate, maxDate, Locale.getDefault());
        cvCalendar.init(manager, R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
        cvCalendar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        viewPagerAdapter = new CardViewPagerAdapter(getApplicationContext(), pojoCity);
        vpCardPager.setAdapter(viewPagerAdapter);
        vpCardPager.setOffscreenPageLimit(3);
        vpCardPager.setPageMargin(new CardMarginCalc(getResources().getDisplayMetrics().density).getPageMargin() + 40);

    }

}
