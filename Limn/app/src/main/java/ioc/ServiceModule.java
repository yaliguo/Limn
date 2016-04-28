package ioc;

import dagger.Module;
import dagger.Provides;
import service.NetManager;

/**
 * Created by phoenix on 2016/4/12.
 */
@Module
public class ServiceModule {
    @Provides
    public NetManager provideNetManager(){
        return  new NetManager();
    }
}
