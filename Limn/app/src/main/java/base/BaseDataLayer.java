package base;

import service.NetManager;
import store.WeatherStore;

/**
 * Created by phoenix on 2016/4/8.
 */
public class BaseDataLayer {
    protected NetManager mNetManager;
    protected WeatherStore store;
    public BaseDataLayer(NetManager manager,WeatherStore store) {
        this.mNetManager=manager;
        this.store=store;
    }
}
