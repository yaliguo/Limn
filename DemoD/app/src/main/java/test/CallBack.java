package test;

/**
 * Created by pe on 2016/1/4.
 */
public interface CallBack <T> {
    void onResult(T result);
    void onError(Exception e );
}
