package data;

import android.support.annotation.NonNull;

import base.BaseDataLayer;
import base.BaseStore;
import pojo.Baby;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import service.NetManager;
import service.NetResponse;
import store.HomeStore;

/**
 * Created by phoenix on 2016/4/8.
 */
public class DataLayer extends BaseDataLayer {

        public DataLayer(NetManager manager, BaseStore... stores) {
                super(manager,stores);
        }

        @NonNull
        public Observable<Baby> getDate(){
                mNetManager.getGankDaily()
                      //  .compose(checkNetError())
                        .retry()
                        .subscribe(new Action1<NetResponse<Baby>>() {
                                @Override
                                public void call(NetResponse<Baby> gankInfoNetResponse) {
                                        ((HomeStore)stores[0]).put(gankInfoNetResponse.getValue());
                                }
                        },new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
                mNetManager.getGankMeiZhi()
                      //  .compose(checkNetError())
                        .retry()
                        .subscribe(new Action1<NetResponse<Baby>>() {
                            @Override
                            public void call(NetResponse<Baby> gankInfoNetResponse) {
                                ((HomeStore) stores[0]).put(gankInfoNetResponse.getValue());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
                return  ((HomeStore)stores[0]).getPages();
        }
        public interface GetDater{
                @NonNull
                Observable<Baby> call();
        }

    public Observable.Transformer<NetResponse<Baby>,NetResponse<Baby>> checkNetError(){

        return new Observable.Transformer<NetResponse<Baby>, NetResponse<Baby>>() {
            @Override
            public Observable<NetResponse<Baby>> call(Observable<NetResponse<Baby>> netResponseObservable) {

                return netResponseObservable.filter(new Func1<NetResponse<Baby>, Boolean>() {
                    @Override
                    public Boolean call(NetResponse<Baby> babyNetResponse) {
                        if(!babyNetResponse.isOnNext()){
                           //##########################
                           //##########################
                           //##########################
                        }
                        return babyNetResponse.isOnNext();
                    }
                });
            }
        };
    }
}
