package test;

import android.net.Uri;


import java.util.List;

/**
 * Created by pe on 2016/1/4.
 */
public class ApiWrapper {
    Api api;
    public void query(String query, final CallBack<List<bean>> callBack){
        api.queryCats(query, new Api.CatsQueryCallback() {
            @Override
            public void onCatListReceived(List<bean> cats) {
                callBack.onResult(cats);
            }

            @Override
            public void onQueryFailed(Exception e) {
                callBack.onError(e);
            }
        });
    }

    public void store(bean b, final CallBack<Uri> callBack){
        api.store(b, new Api.StoreCallback() {
            @Override
            public void onCatStored(Uri uri) {
                callBack.onResult(uri);
            }

            @Override
            public void onStoreFailed(Exception e) {
                callBack.onError(e);
            }
        });
    }
}
