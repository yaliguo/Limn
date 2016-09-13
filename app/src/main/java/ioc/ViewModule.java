package ioc;

import dagger.Module;
import dagger.Provides;
import data.DataLayer;
import vm.HomeModel;

/**
 * Created by pe on 2016/4/8.
 */
@Module
public class ViewModule {
    @Provides
    public HomeModel provideHomeViewModule(DataLayer.GetDater var1){
        return  new HomeModel(var1);
    }

}
