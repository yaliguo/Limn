package view.act;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import my.phoenix.limn.R;
import pojo.WeatherInfo;
import utils.DataUtils;
import utils.SystemUtils;
import view.BzLine;

/**
 * Created by pe on 2016/5/3.
 */
public class BlurActView extends BaseView implements ValueAnimator.AnimatorUpdateListener {

    private ImageView mWetherIc;
private Context context;
    private int screenWidth;
    private int screenHeight;
    private BzLine bzLine1;
    private BzLine bzLine2;
    private LinearLayout mContent;

    public BlurActView(Context context) {
        super(context);
        this.context=context;
    }

    public BlurActView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public BlurActView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWetherIc = (ImageView) findViewById(R.id.stub_home_weatherIc);
        mContent = (LinearLayout) findViewById(R.id.stub_home_content);
        screenWidth = SystemUtils.getScreenWidth(context);
        screenHeight = SystemUtils.getScreenHeight(context);

    }
    public void setBzLine(WeatherInfo weatherInfo) {
        if(DataUtils.CheckNull(weatherInfo))return;
        bzLine1 = new BzLine.Builder(context)
                .configMode(BzLine.BzMode.TEMPERATURE_h)
                .confinTempperInfo(weatherInfo)
                .create();
        bzLine2 = new BzLine.Builder(context)
                .configMode(BzLine.BzMode.TEMPERATURE_l)
                .confinTempperInfo(weatherInfo)
                .create();
        mContent.addView(bzLine1);
        mContent.addView(bzLine2);

    }
    public void run(){
        TranslateAnimation translate = new TranslateAnimation( screenWidth/2, screenWidth/2,0.1f,screenHeight/3);
        translate.setDuration(1000);
        translate.setInterpolator(new AccelerateDecelerateInterpolator());
        translate.setFillAfter(true);
        mWetherIc.startAnimation(translate);
        bzLine1.startAnimator(5000);
        bzLine2.startAnimator(5000);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
