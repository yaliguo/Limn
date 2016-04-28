package base;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Pair;

import com.f2prateek.rx.preferences.Preference;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.litesuits.orm.LiteOrm;

import pojo.WeatherInfo;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by phoenix on 2016/4/14.
 */
public class BaseStore <T>{
    PublishSubject<T> updataSubject  =  PublishSubject.create();
    public BaseStore() {
        updataSubject.observeOn(Schedulers.io())
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                             updataIfChange(t);
                    }
                });

    }

    private void updataIfChange(T t) {
        Preference<String> weathers = App.getInstance().getRxPreferences().getString("time", "");
        Preference<String> json = App.getInstance().getRxPreferences().getString("json", "");

        WeatherInfo t1 = (WeatherInfo) t;
        if(!t1.result.data.realtime.time.equals(weathers.get())){
            json.set(new Gson().toJson(t1));
            weathers.set(t1.result.data.realtime.time);
        }
    }
    protected void insertOrUpdate(T item) {
       // Preconditions.checkNotNull(item, "Item to be inserted cannot be null");
        updataSubject.onNext(item);
    }

}
