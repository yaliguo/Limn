package test;

import android.net.Uri;

import java.util.List;

/**
 * Created by pe on 2016/1/4.
 */
public interface Api {
    interface CatsQueryCallback {
        void onCatListReceived(List<bean> cats);
        void onQueryFailed(Exception e);

    }

    interface StoreCallback{
        void onCatStored(Uri uri);
        void onStoreFailed(Exception e);
    }


    void queryCats(String query, CatsQueryCallback catsQueryCallback);

    void store(bean cat, StoreCallback storeCallback);

}
