package test;

import android.net.Uri;

import java.util.List;

/**
 * Created by pe on 2016/1/4.
 */
public class ApiWrapper2 {
    Api api;
    public ApiJob<List<bean>> query(final String query){
        return  new ApiJob<List<bean>>() {
            @Override
            public void start(final CallBack<List<bean>> callBack) {
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
        };
    }

    public ApiJob<Uri> store(final bean bea){
        return new ApiJob<Uri>() {
            @Override
            public void start(final CallBack<Uri> callBack) {
                api.store(bea, new Api.StoreCallback() {
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
        };



    }
}
