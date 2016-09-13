package base;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import pojo.Baby;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by phoenix on 2016/4/14.
 */
public abstract class BaseStore<T extends Baby> {
    PublishSubject<T> updataSubject  =  PublishSubject.create();
    public BaseStore() {
        updataSubject.observeOn(Schedulers.io())
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                             updataIfChange(t);
                    }
                });

    }

    private void updataIfChange(T t) {
        Integer cache_index = t.getCache_Index();
        if(cache_index==-1){
            return;
        }
        Preference<String> pf_json = App.getInstance().getRxPreferences().getString(cache_index+"", "");
            pf_json.set(new Gson().toJson(t));
    }
    protected void insertOrUpdate(T item) {
       // Preconditions.checkNotNull(item, "Item to be inserted cannot be null");
        updataSubject.onNext(item);
    }

    public abstract Observable<T> getPages();
}
