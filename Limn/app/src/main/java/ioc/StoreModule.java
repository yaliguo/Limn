package ioc;

import dagger.Module;
import dagger.Provides;
import store.WeatherStore;

/**
 * Created by pe on 2016/4/14.
 */
@Module
public class StoreModule {
    @Provides
    public WeatherStore provideWeatherStore(){
        return  new WeatherStore();
    }
}
