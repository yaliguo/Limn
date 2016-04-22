package phoenix.pe.demod;

import android.content.Intent;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


import java.util.concurrent.TimeUnit;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by pe on 2015/12/17.
 */
public class SplashActivity extends BaseActivity {
    @Bind(R.id.at_splash_sf)
    SurfaceView mContentView;
    @Bind(R.id.at_splash_loadic)
    ImageView mLoadImg;
    public int setContentLayout() {
        return R.layout.activity_splash;
    }


    @Override
    public void init() {
        ButterKnife.bind(this);
        final RotateAnimation animation =new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(2500);
        mLoadImg.setAnimation(animation);
        Observable.just("")
                .delay(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        SplashActivity.this.finish();
                        Intent intent = new Intent(mBContext,HomeActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
