package base;

import service.NetManager;

/**
 * Created by phoenix on 2016/4/8.
 */
public class BaseDataLayer {
    protected NetManager mNetManager;
    protected BaseStore[] stores;
    public BaseDataLayer(NetManager manager,BaseStore... stores) {
        this.mNetManager=manager;
        this.stores=stores;
    }
}
