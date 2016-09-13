package view.act;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import my.phoenix.limn.HomeActivity;
import my.phoenix.limn.R;
import pojo.page.WeatherInfo;
import utils.DataUtils;
import utils.SystemUtils;
import view.BzLine;

/**
 * Created by pe on 2016/5/3.
 */
public class BlurActView extends BaseView implements ValueAnimator.AnimatorUpdateListener {

private Context context;
    private int screenWidth;
    private int screenHeight;
    private BzLine bzLine1;
    private BzLine bzLine2;
    private LinearLayout mContent;
    private ImageView mIc;

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
        mContent = (LinearLayout) findViewById(R.id.stub_home_content);
        mIc = (ImageView) findViewById(R.id.stub_home_ic);
        screenWidth = SystemUtils.getScreenWidth(context);
        screenHeight = SystemUtils.getScreenHeight(context);

    }
    public void setBzLine(WeatherInfo weatherInfo) {
        if(DataUtils.CheckNull(weatherInfo))return;
        if(bzLine1!=null&&bzLine2!=null){
            mContent.removeView(bzLine1);
            mContent.removeView(bzLine2);
        }
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
    public void setLoc(int [] rgs){
        setLayout(mIc,rgs[0],rgs[1]- HomeActivity.getStatusBarHeight(context));
    }
    /*
              * 设置控件所在的位置YY，并且不改变宽高，
              * XY为绝对位置
              */
    public static void setLayout(View view, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    public void run(){
        mIc.setVisibility(View.VISIBLE);
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f ,Animation.RELATIVE_TO_SELF,Animation.RELATIVE_TO_SELF+300);
        translate.setDuration(800);
        translate.setInterpolator(new AccelerateDecelerateInterpolator());
        translate.setFillAfter(true);
        if(bzLine1!=null){
            bzLine1.startAnimator(800);
        }
        if(bzLine2!=null){
            bzLine2.startAnimator(800);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }


}
