package ioc;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import phoenix.pe.demod.HomeActivity;
import vm.HomeModel;

/**
 * Created by phoenix on 2016/4/8.
 */
@Singleton
@Component(modules = {ApplicationModule.class,DateLayerModule.class,
               ViewModule.class})
public interface IocComponent {
    void inject(HomeModel homeModel);
    void inject(HomeActivity activity);

    final class Initializer {
        public static IocComponent init(Application application) {
            return DaggerIocComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
