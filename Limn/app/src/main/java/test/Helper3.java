package test;

import android.net.Uri;

import java.util.List;

/**
 * Created by pe on 2016/1/4.
 */
public class Helper3 {
    ApiWrapper2 wrapper2;

    public ApiJob<Uri> save(final String query){
        return new ApiJob<Uri>() {
            @Override
            public void start(final CallBack<Uri> callBack) {
                wrapper2.query(query)
                        .start(new CallBack<List<bean>>() {
                            @Override
                            public void onResult(List<bean> result) {
                                wrapper2.store(getMax())
                                        .start(new CallBack<Uri>() {
                                            @Override
                                            public void onResult(Uri result) {
                                                callBack.onResult(result);
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                    callBack.onError(e);
                                            }
                                        });
                            }

                            @Override
                            public void onError(Exception e) {
                                callBack.onError(e);
                            }
                        });
            }
        };
    }

    public ApiJob<Uri> save2(String query){
        final ApiJob<List<bean>> query1 = wrapper2.query(query);
        final ApiJob<bean> cur = new ApiJob<bean>() {
            @Override
            public void start(final CallBack<bean> callBack) {
                query1.start(new CallBack<List<bean>>() {
                    @Override
                    public void onResult(List<bean> result) {
                        callBack.onResult(getMax());
                }

                    @Override
                    public void onError(Exception e) {
                        callBack.onError(e);
                    }
                });
            }
        };
       ApiJob<Uri> cur2 =  new ApiJob<Uri>() {
            @Override
            public void start(final CallBack<Uri> callBack) {
                cur.start(new CallBack<bean>() {
                    @Override
                    public void onResult(bean result) {
                       wrapper2.store(result)
                               .start(new CallBack<Uri>() {
                                   @Override
                                   public void onResult(Uri result) {
                                       callBack.onResult(result);
                                   }

                                   @Override
                                   public void onError(Exception e) {
                                       callBack.onError(e);
                                   }
                               });
                    }

                    @Override
                    public void onError(Exception e) {
                        callBack.onError(e);
                    }
                });
            }
        };
        return cur2;
    }

    private bean getMax (){
        return new bean();
    }
}
