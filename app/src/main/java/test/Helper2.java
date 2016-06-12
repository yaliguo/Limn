package test;

import android.net.Uri;


import java.util.List;

/**
 * Created by pe on 2016/1/4.
 */
public class Helper2 {
    Api api;
    ApiWrapper apiWrapper;
    public void saveCat(String query, final CallBack<Uri> callBack){
        apiWrapper.query(query, new CallBack<List<bean>>() {
            @Override
            public void onResult(List<bean> result) {
                apiWrapper.store(getMax(),callBack );
            }

            @Override
            public void onError(Exception e) {
                callBack.onError(e);
            }
        });

    }

    private bean getMax (){
        return new bean();
    }
}
