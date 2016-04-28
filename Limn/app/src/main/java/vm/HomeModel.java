package vm;

import android.support.annotation.NonNull;
import android.util.Log;

import base.BaseModel;
import data.DataLayer;
import okhttp3.ResponseBody;
import pojo.WeatherInfo;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by phoenix on 2016/4/7.
 */
public class HomeModel extends BaseModel {
    BehaviorSubject<WeatherInfo> getweatherSub = BehaviorSubject.create();
    DataLayer.GetWeather mGetWeather;

    public HomeModel(DataLayer.GetWeather g){
        this.mGetWeather=g;
    }

        public void onSubToData(@NonNull CompositeSubscription compositeSubscription) {
            compositeSubscription.add(mGetWeather.call()
                    .subscribe(getweatherSub));
    }
    public Observable<WeatherInfo> getWeathers(){
       return getweatherSub.asObservable();
    }

}
