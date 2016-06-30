package rx;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.ViewStub;
import android.widget.ImageView;

import view.act.BlurActView;

/**
 * Created by pe on 2016/4/29.
 */
public class SubScribeDot {
    public static Observable<BlurActView> inflateEvent(ViewStub stub){
        return Observable.create(new ViewStubOnSub(stub));
    }


}
