package data;

import base.BaseDataLayer;
import service.NetManager;
import store.WeatherStore;

/**
 * Created by phoenix on 2016/4/8.
 */
public class NetLayer  extends BaseDataLayer{

    public NetLayer(NetManager manager, WeatherStore store) {
        super(manager, store);
    }
}
