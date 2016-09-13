package ioc;

import dagger.Module;
import dagger.Provides;
import store.StoreFactory;

/**
 * Created by pe on 2016/4/14.
 */
@Module
public class StoreModule {
    @Provides
    public StoreFactory provideStoreFactory(){
        return  new StoreFactory();
    }
}
