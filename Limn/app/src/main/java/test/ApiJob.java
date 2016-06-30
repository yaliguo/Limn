package test;

/**
 * Created by pe on 2016/1/4.
 */
public abstract class ApiJob<T> {
    public abstract  void start(CallBack<T> callBack);
}
