package base;

import android.support.annotation.NonNull;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by phoenix on 2016/4/7.
 */
public abstract class BaseModel {
    private CompositeSubscription compositeSubscription;

    public void subToData() {
        unsubscribeFromDataStore();
        compositeSubscription = new CompositeSubscription();
        onSubToData(compositeSubscription);
    }

    public void dispose() {

        if (compositeSubscription != null) {
            unsubscribeFromDataStore();
        }
    }

    public abstract void onSubToData(@NonNull CompositeSubscription compositeSubscription);

    public void unsubscribeFromDataStore() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
            compositeSubscription = null;
        }
    }
}
