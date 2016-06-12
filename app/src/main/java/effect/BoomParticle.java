package effect;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by pe on 2015/12/21.
 * 粒子抽象
 *          粒子最清楚自己的运动
 */
public abstract class BoomParticle {
    float cx;
    float cy;
    int color;
    public BoomParticle(int color, float x, float y) {
        this.color=color;
        this.cx=x;
        this.cy=y;
    }
    protected abstract void draw(Canvas canvas,Paint paint);

    protected abstract void caculate(float factor);

    public void advance(Canvas canvas,Paint paint,float factor) {
        caculate(factor);
        draw(canvas,paint);
    }
}
