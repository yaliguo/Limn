package data;

import base.BaseDataLayer;
import service.NetManager;
import store.HomeStore;

/**
 * Created by phoenix on 2016/4/8.
 */
public class NetLayer  extends BaseDataLayer{

    public NetLayer(NetManager manager, HomeStore store) {
        super(manager, store);
    }
}
