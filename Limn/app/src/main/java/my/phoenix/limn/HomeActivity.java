package my.phoenix.limn;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.transform.Transformer;

import base.App;
import base.BaseActivity;
import base.BaseBinder;
import butterknife.Bind;
import butterknife.ButterKnife;
import my.phoenix.limn.adapter.LayerActivity;
import my.phoenix.limn.adapter.WeatherWeekAdapter;
import pojo.WeatherInfo;
import pojo.WeatherItem;
import rx.SubScribeDot;
import rx.TransFormers;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import utils.BestBlur;
import utils.DataUtils;
import utils.SystemUtils;
import view.BzLine;
import view.act.BlurActView;
import vm.HomeModel;

/**
 * Created by pe on 2015/12/18.
 */
public class HomeActivity extends BaseActivity {
    @Inject
    HomeModel mHomeModel;
    @Bind(R.id.at_home_nsv)
    NestedScrollView mScrollview;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mToobarLayout;
    @Bind(R.id.at_home_template)
    TextView mTemplate;
    @Bind(R.id.at_home_template2)
    TextView mTemplateSmall;
    @Bind(R.id.at_home_air)
    TextView mAri;
    @Bind(R.id.at_home_weatherImg)
    ImageView mWeatherImg;
    @Bind(R.id.at_home_weatherTx)
    TextView mWeatherTx;
    @Bind(R.id.at_home_windTx)
    TextView mWindTxtx;
    @Bind(R.id.at_home_scroLayout)
    LinearLayout mScroLayout;
    @Bind(R.id.at_home_weatherGrid)
    GridView mWerGrid;
    @Bind(R.id.at_home_time)
    TextView mUpTime;
    @Bind(R.id.at_home_data)
    TextView mDate;
    @Bind(R.id.at_home_bzlayout)
    LinearLayout mBzLayout;
    @Bind(R.id.at_home_stub)
    ViewStub mStub;
    private ViewBind mViewBind;
    private boolean isBlur = false;

    @Override
    public int setContentLayout() {
        return R.layout.activity_home;
    }
    @Override
    public void init() {
        ButterKnife.bind(this);
        App.getInstance().mCompoent.inject(this);
        initToolBar();
        mViewBind= new ViewBind();
        mHomeModel.subToData();
        mViewBind.bind();
    }

    private class ViewBind extends BaseBinder{
        @Override
        protected void onBind(@NonNull CompositeSubscription compositeSubscription) {
           compositeSubscription.add(mHomeModel.getWeathers()
                   .observeOn(AndroidSchedulers.mainThread())
                   .map(weatherInfo -> {
                       setBzLine(weatherInfo);
                       return weatherInfo;
                   })
                   .subscribe(responseBody -> {
                       if (responseBody != null)
                           HomeActivity.this.setUI(responseBody);
                   }, HomeActivity.this::loadError));
        }
    }

    private void setBzLine(WeatherInfo weatherInfo) {
        if(DataUtils.CheckNull(weatherInfo))return;
        BzLine bzLine1 = new BzLine.Builder(this)
                .configMode(BzLine.BzMode.TEMPERATURE_h)
                .confinTempperInfo(weatherInfo)
                .create();
        BzLine bzLine2 = new BzLine.Builder(this)
                .configMode(BzLine.BzMode.TEMPERATURE_l)
                .confinTempperInfo(weatherInfo)
                .create();
        mBzLayout.addView(bzLine1);
        mBzLayout.addView(bzLine2);
        bzLine1.startAnimator(5000);
                bzLine2.startAnimator(5000);
        int screenWidth = SystemUtils.getScreenWidth(this);
        int screenHeight = SystemUtils.getScreenHeight(this);
    }

    /**
     * notify data
     * @param weatherInfo
     */
    private void setUI(WeatherInfo weatherInfo) {
        if(DataUtils.CheckNull(weatherInfo))return;
        mUpTime.setText("更新时间："+weatherInfo.result.data.realtime.time);
        mDate.setText("今日 :"+weatherInfo.result.data.realtime.date);
        mTemplate.setText("22");
        mTemplateSmall.setText(weatherInfo.result.data.weather.get(0).info.night.get(2) + "℃~" +
                weatherInfo.result.data.weather.get(0).info.day.get(2) + "℃");
        mAri.setText(weatherInfo.result.data.pm25.pm.pm25_ + weatherInfo.result.data.pm25.pm.quality);
        mWeatherImg.setImageLevel(DataUtils.ValueOf(weatherInfo.result.data.realtime.weather.img));
        mWeatherTx.setText(weatherInfo.result.data.realtime.weather.info);
        mWindTxtx.setText(weatherInfo.result.data.realtime.wind.direct + weatherInfo.result.data.realtime.wind.power);
        List<WeatherItem> list = new ArrayList<>();
        for(int var = 0; var<5 ;var++){
            WeatherItem weatherItem = new WeatherItem();
            String s = weatherInfo.result.data.weather.get(var + 1).week;
            String ss = weatherInfo.result.data.weather.get(var + 1).info.day.get(0);
            weatherItem.day=s;
            weatherItem.img=DataUtils.ValueOf(ss);
            list.add(weatherItem);
        }
        mWerGrid.setAdapter(new WeatherWeekAdapter(this, list, R.layout.item_home_weather));

    }

@Bind(R.id.ceshi1)
CoordinatorLayout layout;
    @Bind(R.id.donghua)
    ImageView img;
    /**
     * error net
     * @param throwable
     */
    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Snackbar.make(mScroLayout, "链接失败请重试..", Snackbar.LENGTH_LONG)
                .setAction("再试一次", v -> {
                  mViewBind.bind();
                })
        .show();

    }
    /**
     * Toolbar  init
     */
    private void initToolBar() {
        this.setSupportActionBar(mToolbar);
        mToobarLayout.setTitle("天气");
            SubScribeDot.inflateEvent(mStub)
        .subscribe(new Action1<BlurActView>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void call(BlurActView view) {
                Bitmap bitmap = new BestBlur(HomeActivity.this).blurBitmap(currentScreent(), 16, 0.1f);
                view.setBackground(new BitmapDrawable(bitmap));
                view.run();
            }
        });

        mToobarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStub.getParent() != null) {
                    mStub.inflate();
                } else {
                    mStub.setVisibility(View.VISIBLE);
                }
                img.setVisibility(View.INVISIBLE);
                isBlur =true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0
                &&isBlur) {
            mStub.setVisibility(View.INVISIBLE);
            isBlur =false;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public Bitmap currentScreent(){
        View root = this.getWindow().getDecorView().findViewById(android.R.id.content);
        root.setDrawingCacheEnabled(true);
        Bitmap drawingCache = root.getDrawingCache();

        Bitmap  currentBitmap = Bitmap.createBitmap(drawingCache.getWidth(), drawingCache.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(currentBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(drawingCache, 0, 0, paint);
        root.destroyDrawingCache();
        return currentBitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mViewBind.bind();
    }
}
