package rx;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import rx.android.MainThreadSubscription;
import view.act.BlurActView;

public  class ViewStubOnSub implements Observable.OnSubscribe<BlurActView>{
        private ViewStub stub;
        public ViewStubOnSub(ViewStub stub) {
            this.stub=stub;
        }
        @Override
        public void call(Subscriber<? super BlurActView> subscriber) {
            stub.setOnInflateListener(new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub stub1, View inflated) {
                    subscriber.onNext((BlurActView) inflated);
                }
            });
            subscriber.add(new MainThreadSubscription() {
                @Override
                protected void onUnsubscribe() {
                    stub.setOnInflateListener(null);
                }
            });
        }
    }