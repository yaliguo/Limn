package store;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences.Preference;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import base.App;
import base.BaseStore;
import pojo.WeatherInfo;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pe on 2016/4/14.
 */
public class WeatherStore extends BaseStore<WeatherInfo> {
    public void put(@NonNull WeatherInfo item) {
        //Preconditions.checkNotNull(item, "Item cannot be null.");
        insertOrUpdate(item);
    }
    @NonNull
    public Observable<WeatherInfo> query() {
        return Observable.just("")
                .observeOn(Schedulers.computation())
                .map(s -> {
                    Preference<String> json = App.getInstance().getRxPreferences().getString("json", "");
                    return new Gson().fromJson(json.get(), WeatherInfo.class);
                });
    }
}
