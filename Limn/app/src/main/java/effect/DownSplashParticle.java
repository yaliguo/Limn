package effect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by pe on 2015/12/29.
 *
 */
public class DownSplashParticle extends BoomParticle{
    static Random random = new Random();
    Rect bound;
    float alpha;
    float radius = DownSplashParticleFactory.PART_WH;
    float ox,oy;
    public DownSplashParticle(int color, float x, float y,Rect bound) {
        super(color, x, y);
        ox = x;
        oy = y;
        this.bound=bound;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha)); //����͸����ɫ�Ͳ��Ǻ�ɫ��
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    protected void caculate(float factor) {
        if(ox>bound.exactCenterX()){
            cx = cx + factor * random.nextInt(bound.width()) * (random.nextFloat());
        }else{
            cx = cx - factor * random.nextInt(bound.width()) * (random.nextFloat());
        }
        if(factor<=0.5){
            cy = cy - factor * random.nextInt(bound.height() / 2);
        }else{
            cy = cy + factor * random.nextInt(bound.height() / 2);
        }

        radius = radius - factor * random.nextInt(2);

        alpha = (1f - factor) * (1 + random.nextFloat());
    }
}
