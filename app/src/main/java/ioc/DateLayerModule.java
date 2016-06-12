package ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import data.DataLayer;
import okhttp3.ResponseBody;
import pojo.WeatherInfo;
import rx.Observable;
import service.NetManager;
import store.WeatherStore;

/**
 * Created by phoenix on 2016/4/8.
 */
@Module(includes = {ServiceModule.class,StoreModule.class})
public class DateLayerModule {
    @Provides
    public DataLayer.GetWeather provideGetWeather(DataLayer layer){
        return new DataLayer.GetWeather() {
            @NonNull
            @Override
            public Observable<WeatherInfo> call() {
                return layer.getWehther();
            }
        };
    }

    @Provides
    public DataLayer provideDataLayer(NetManager mg, WeatherStore store){
        return  new DataLayer(mg,store);
    }


}
