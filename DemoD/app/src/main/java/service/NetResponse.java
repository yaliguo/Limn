package service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by phoenix on 2016/4/14.
 */
public class NetResponse<T> {
    private T value;
    private Throwable error;
    private   Type type;
    public enum Type {
        FETCHING_START, FETCHING_ERROR, ON_NEXT
    }
    public NetResponse(@NonNull Type type,T value, Throwable error) {
        this.value = value;
        this.error = error;
        this.type  = type;
    }

    public T getValue() {
        return value;
    }
    @NonNull
    public static<T> NetResponse<T> fetchingStart() {
        return new NetResponse<>(Type.FETCHING_START, null, null);
    }

    @NonNull
    public static<T> NetResponse<T> onNext(T value) {
        return new NetResponse<>(Type.ON_NEXT, value, null);
    }

    @NonNull
    public static<T> NetResponse<T> fetchingError(Throwable throwable) {
        return new NetResponse<>(Type.FETCHING_ERROR, null, throwable);
    }

    public boolean isFetchingStart() {
        return type.equals(Type.FETCHING_START);
    }

    public boolean isOnNext() {
        return type.equals(Type.ON_NEXT);
    }

    public boolean isFetchingError() {
        return type.equals(Type.FETCHING_ERROR);
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

}
