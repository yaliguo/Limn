package service;

import okhttp3.ResponseBody;
import pojo.Baby;
import pojo.page.FuliInfo;
import pojo.page.GankInfo;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import service.build.RetroControl;

/**
 * Created by phoenix on 2016/4/12.
 */
public class NetManager extends ManagerBase{
    PublishSubject<NetResponse<ResponseBody>> result = PublishSubject.create();
    PublishSubject<NetResponse<Baby>> homePublish = PublishSubject.create();
    public Observable<NetResponse<Baby>> getGankMeiZhi(){
        RetroControl.getSimAPi()
                .getGankMeiZhi(1)
                .subscribeOn(Schedulers.io())
                .map(new Func1<FuliInfo, NetResponse<Baby>>() {
                    @Override
                    public NetResponse<Baby> call(FuliInfo fuliInfo) {
                        return NetResponse.onNext(fuliInfo);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                      //  homePublish.onNext(NetResponse.fetchingError(throwable));
                    }
                })
                .subscribe(homePublish);
        return  homeCreate();
    }
    public Observable<NetResponse<Baby>> getGankDaily(){
        RetroControl.getSimAPi()
                .getGankDaily(2016,8,26)
                .subscribeOn(Schedulers.io())
                .map(new Func1<GankInfo, NetResponse<Baby>>() {
                    @Override
                    public NetResponse<Baby> call(GankInfo responseBody) {
                        return NetResponse.onNext(responseBody);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       // homePublish.onNext(NetResponse.fetchingError(throwable));
                    }
                })
                .subscribe(homePublish);
        return  homeCreate();
    }
    public Observable<NetResponse<Baby>> homeCreate(){
        return  homePublish.asObservable();
    }

    public Observable<NetResponse<ResponseBody>> create(){
        return  result.asObservable();
    }
}
