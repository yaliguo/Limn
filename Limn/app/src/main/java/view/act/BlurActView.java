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

import my.phoenix.limn.R;

/**
 * Created by pe on 2016/5/3.
 */
public class BlurActView extends BaseView implements ValueAnimator.AnimatorUpdateListener {

    private ImageView mWetherIc;

    public BlurActView(Context context) {
        super(context);
    }

    public BlurActView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlurActView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWetherIc = (ImageView) findViewById(R.id.stub_home_weatherIc);
    }

    public void run(){

        TranslateAnimation translate = new TranslateAnimation(0.1f, 500.0f,0.1f,500.0f);
        translate.setDuration(1000);
        translate.setInterpolator(new AccelerateDecelerateInterpolator());
        mWetherIc.startAnimation(translate);

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
