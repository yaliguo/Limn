package vm;

import android.support.annotation.NonNull;

import base.BaseModel;
import data.DataLayer;
import pojo.Baby;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by phoenix on 2016/4/7.
 */
public class HomeModel extends BaseModel {
    BehaviorSubject<Baby> varSub = BehaviorSubject.create();
    DataLayer.GetDater mGetDater;

    public HomeModel(DataLayer.GetDater var){
        this.mGetDater=var;
    }

        public void onSubToData(@NonNull CompositeSubscription compositeSubscription) {
            compositeSubscription.add(mGetDater.call()
                    .subscribe(varSub));
    }
    public Observable<Baby> getDatas(){
       return varSub.asObservable();
    }
}
