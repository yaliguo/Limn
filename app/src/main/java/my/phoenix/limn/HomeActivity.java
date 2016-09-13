package my.phoenix.limn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;

import javax.inject.Inject;

import base.App;
import base.BaseActivity;
import base.BaseBinder;
import butterknife.BindView;
import butterknife.ButterKnife;
import my.phoenix.limn.adapter.HomeContentAdapter;
import pojo.Baby;
import pojo.page.FuliInfo;
import pojo.page.WeatherInfo;
import rx.SubScribeDot;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;
import view.act.BlurActView;
import vm.HomeModel;

/**
 * by phoenix
 */
public class HomeActivity extends BaseActivity {
//    @Inject
//    HomeStore wStore;
    @Inject
    HomeModel mHomeModel;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mToobarLayout;
    @BindView(R.id.at_home_scroLayout)
    LinearLayout mScroLayout;
    @BindView(R.id.at_home_stub)
    ViewStub mStub;
    @BindView(R.id.at_home_ic_sun)
    ImageView mSunIc;
    @BindView(R.id.at_home_rc)
    RecyclerView mContentRv;
    private ViewBind mViewBind;
    private boolean isBlur = false;
    BehaviorSubject<BlurActView> subject = BehaviorSubject.create();

    private WeatherInfo minfo;
    private Bitmap currentBitmap;

    @Override
    public int setContentLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        App.getInstance().mCompoent.inject(this);
        //initToolBar();
        mHomeModel.subToData();
        mViewBind = new ViewBind();
        mViewBind.bind();

    }
    private class ViewBind extends BaseBinder {
        @Override
        protected void onBind(@NonNull CompositeSubscription compositeSubscription) {
            compositeSubscription.add(mHomeModel.getDatas()
            .subscribe(new Action1<Baby>() {
                @Override
                public void call(Baby babies) {
                    if (babies instanceof FuliInfo) {
                        FuliInfo info = (FuliInfo) babies;
                        HomeContentAdapter homeContentAdapter = new HomeContentAdapter(HomeActivity.this, info.results);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                        mContentRv.setLayoutManager(staggeredGridLayoutManager);
                        HomeContentAdapter.SpacesItemDecoration decoration=new HomeContentAdapter.SpacesItemDecoration(8);
                        mContentRv.addItemDecoration(decoration);
                        mContentRv.setAdapter(homeContentAdapter);
                    } else {
//                        GankInfo info = (GankInfo) babies;
//                        HomeContentAdapter homeContentAdapter = new HomeContentAdapter(HomeActivity.this, info.results.妹纸List);
//                        mContentRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                        //mContentRv.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
//                        mContentRv.setAdapter(homeContentAdapter);
                    }

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    loadError(throwable);
                }
            }));
        }
    }



    /**
     * error net
     *
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
                .subscribe(subject);
        int[] mLocation = new int[2];
        mSunIc.getLocationOnScreen(mLocation);
        blurRun(mLocation);
        mToobarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlur) {
                    return;
                }
                if (mStub.getParent() != null) {
                    mStub.inflate();
                } else {
                    mStub.setVisibility(View.VISIBLE);
                }
                isBlur = true;
                mSunIc.setVisibility(View.GONE);
                int[] mLocation = new int[2];
                mSunIc.getLocationOnScreen(mLocation);
                blurRun(mLocation);
            }
        });
    }

    private void blurRun(int[] args) {
//        Observable.combineLatest()
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//
//                    }
//                })
//
//        Observable.combineLatest(wStore.query(""), subject, new Func2<WeatherInfo, BlurActView, BlurActView>() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public BlurActView call(WeatherInfo info, BlurActView blurActView) {
//                Bitmap bitmap = new BestBlur(HomeActivity.this).blurBitmap(currentScreent(), 16, 0.1f);
//                blurActView.setBackground(new BitmapDrawable(bitmap));
//                blurActView.setLoc(args);
//                blurActView.setBzLine(info);
//                blurActView.run();
//                return blurActView;
//            }
//
//        })
//                .subscribe(new Action1<BlurActView>() {
//                    @Override
//                    public void call(BlurActView blurActView) {
//                    }
//                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0
                && isBlur) {
            mStub.setVisibility(View.INVISIBLE);
            isBlur = false;
            mSunIc.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public Bitmap currentScreent() {
        if (currentBitmap != null && !currentBitmap.isRecycled()) {
            return currentBitmap;
        }
        View root = this.getWindow().getDecorView().findViewById(android.R.id.content);
        root.setDrawingCacheEnabled(true);
        Bitmap drawingCache = root.getDrawingCache();
        currentBitmap = Bitmap.createBitmap(drawingCache.getWidth(), drawingCache.getHeight(), Bitmap.Config.ARGB_8888);
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

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewBind.unbind();
//        currentBitmap.recycle();
    }
}
