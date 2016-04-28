package base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.litesuits.orm.LiteOrm;

import ioc.IocComponent;

/**
 * Created by pe on 2015/12/15.
 */
public class App extends Application {
    private static App instance;
    private final  String DB_NAME = "ph.db";
    private Context mContext;
    public static LiteOrm mOrmInstance;
    public IocComponent mCompoent;
    private RxSharedPreferences rxPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        instance=this;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        rxPreferences = RxSharedPreferences.create(preferences);
        if(null==mOrmInstance){
            mOrmInstance=LiteOrm.newCascadeInstance(mContext,DB_NAME);
        }
        if(Config.DE_BUG){
            mOrmInstance.setDebugged(true);
        }
        mCompoent = IocComponent.Initializer.init(this);
    }
    @NonNull
    public static App getInstance() {
        return instance;
    }
    public Context getContext(){
        return  mContext;
    }
    @NonNull
    public IocComponent getGraph() {
        return mCompoent;
    }

    @NonNull
    public RxSharedPreferences getRxPreferences(){
        return rxPreferences;
    }
}
