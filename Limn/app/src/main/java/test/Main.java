package test;

import android.net.Uri;

/**
 * Created by pe on 2016/1/4.
 */
public class Main {
    Helper helper ;
    Helper2 helper2;
    Helper3 helper3;
    public void run (){
        helper.saveCat("beautiful", new Helper.saveCallBack() {
            @Override
            public void onCutestCatSaved(Uri uri) {

            }

            @Override
            public void onQueryFailed(Exception e) {

            }
        });
        helper2.saveCat("beautifule", new CallBack<Uri>() {
            @Override
            public void onResult(Uri result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        ApiJob<Uri> bueautiful = helper3.save("bueautiful");
        bueautiful.start(new CallBack<Uri>() {
            @Override
            public void onResult(Uri result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        helper3.save2("")
                .start(new CallBack<Uri>() {
                    @Override
                    public void onResult(Uri result) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }
}
