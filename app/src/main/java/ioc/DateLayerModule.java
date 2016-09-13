package ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import data.DataLayer;
import pojo.Baby;
import rx.Observable;
import service.NetManager;
import store.StoreFactory;

/**
 * Created by phoenix on 2016/4/8.
 */
@Module(includes = {ServiceModule.class,StoreModule.class})
public class DateLayerModule {
    @Provides
    public DataLayer.GetDater provideGetWeather(DataLayer layer){
        return new DataLayer.GetDater() {
            @NonNull
            @Override
            public Observable<Baby> call() {
                return layer.getDate();
            }
        };
    }
    @Provides
    public DataLayer provideDataLayer(NetManager mg, StoreFactory store){
        return  new DataLayer(mg,store.stores);
    }


}
