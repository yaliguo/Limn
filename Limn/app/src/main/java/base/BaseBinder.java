package base;

import android.support.annotation.Nullable;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by phoenix on 2016/4/7.
 */
public abstract class BaseBinder {
    @Nullable
    private CompositeSubscription compositeSubscription;
    public void bind(){
        unbind();
        compositeSubscription = new CompositeSubscription();
        onBind(compositeSubscription);
    }

    public void unbind(){
        if (compositeSubscription != null) {
            compositeSubscription.clear();
            compositeSubscription = null;
        }
    }
    public  CompositeSubscription getComposite(){
        return compositeSubscription;
    }

    protected abstract void onBind(@Nullable final CompositeSubscription compositeSubscription);
}
