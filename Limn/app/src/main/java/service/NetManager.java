package service;

import okhttp3.ResponseBody;
import pojo.WeatherInfo;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import service.build.RetroControl;

/**
 * Created by phoenix on 2016/4/12.
 */
public class NetManager extends ManagerBase{
    PublishSubject<NetResponse<ResponseBody>> result = PublishSubject.create();

    public Observable<NetResponse<ResponseBody>> getWeather(){
        RetroControl.getSimAPi()
                //.getWeather("101010100","index_v", DataUtils.getDate(), Config.WEATHER_APPID2,DataUtils.createWeatherKey("101010100"))
                .getWeather("北京", "json", 1, "f9c6d9d59a3d944651128b61fea0de22")
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, NetResponse<ResponseBody>>() {
                    @Override
                    public NetResponse<ResponseBody> call(ResponseBody responseBody) {
                        return NetResponse.onNext(responseBody);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        result.onNext(NetResponse.fetchingError(throwable));
                    }
                })
                .subscribe(result);
       return create();
    }
    public Observable<NetResponse<ResponseBody>> create(){
        return  result.asObservable();
    }
}
