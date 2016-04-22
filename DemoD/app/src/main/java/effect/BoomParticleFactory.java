package effect;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by pe on 2015/12/21.
 *
 */
public abstract class BoomParticleFactory {
   public  abstract BoomParticle[][] getParticleList(Bitmap bitmap,Rect bound);
}
