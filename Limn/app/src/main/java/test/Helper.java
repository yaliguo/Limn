package test;

import android.net.Uri;

import java.util.List;

/**
 * Created by pe on 2016/1/4.
 */
public class Helper {
    public interface saveCallBack {
        void onCutestCatSaved(Uri uri);
        void onQueryFailed(Exception e);
    }
    Api api;
   public void saveCat(String query, final saveCallBack callBack){
       api.queryCats(query, new Api.CatsQueryCallback() {
           @Override
           public void onCatListReceived(List<bean> cats) {
               api.store(getMax(), new Api.StoreCallback() {
                   @Override
                   public void onCatStored(Uri uri) {
                       callBack.onCutestCatSaved(uri);
                   }

                   @Override
                   public void onStoreFailed(Exception e) {
                        callBack.onQueryFailed(e   );
                   }
               });
           }
           @Override
           public void onQueryFailed(Exception e) {

           }
       });

   }

    private bean getMax (){
        return new bean();
    }
}
