package base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import service.build.RetroControl;
import service.protocol.NetApi;

/**
 * Created by phoenix on 2015/12/2.
 */
public abstract class BaseActivity extends AppCompatActivity {
    /*最前显示的Activity*/
    private static BaseActivity mForegroundActivity;
    /*可用的context*/
    public Context mBContext;
    /*一个retrofit的build*/
    public static NetApi  api = RetroControl.getSimAPi();
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(setContentLayout());
        mBContext = this;
        setmForegroundActivity(this);
        init();
    }
    public abstract int setContentLayout();

    public abstract void init();


    /**
     * 运行在主线程中的Toast
     */
    public void showToastOnUI(final String msg) {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * UI线程执行一个任务
     *
     * @param run
     */
    public void runOnUI(Runnable run) {
        runOnUiThread(run);
    }

    /**
     * @return Top Activity now
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * @param mForegroundActivity Set Top Activity
     */
    public void setmForegroundActivity(BaseActivity mForegroundActivity) {
        BaseActivity.mForegroundActivity = mForegroundActivity;
    }

    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
    }


}
