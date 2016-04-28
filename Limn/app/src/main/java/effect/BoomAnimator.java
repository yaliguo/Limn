package effect;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by pe on 2015/12/21.
 * 粒子动画控制
 *
 *          Boom系列爆炸粒子效果
 *          原帖地址：http://blog.csdn.net/crazy__chen/article/details/50149619
 *
 *
 *      类：
 *
 *     BooMZoo -   动画场地，作为一个全屏覆盖的view，explode方法控制动画的开始
 *                      参数传入粒子factory控制粒子爆炸效果
 *
 *     BoomParticle - 动画粒子，抽象类，所有粒子继承自此类，具有粒子运动方法
 *
 *
 *      BoomParticleFactory - 粒子工厂，作用就是将传入的view转化为BoomParticle类型的粒子二维数组
 *
 *     BoomAnimator - 粒子动画的控制，开始单元
 *

 *
 *
 *
 *
 *
 */
public class BoomAnimator extends ValueAnimator{
    public static final int DEFAULT_DURATION = 0x400;
    private BoomParticle[][] mParticles;
    private Paint mPaint;
    private View mContainer;
    private BoomParticleFactory mParticleFactory;
    public BoomAnimator(View boomZoo, Bitmap bitmapFromView, Rect rect, BoomParticleFactory mParticleFactory) {
        this.mParticleFactory=mParticleFactory;
        mPaint = new Paint();
        mContainer = boomZoo;
        setFloatValues(0.0f, 1.0f);
        setDuration(DEFAULT_DURATION);
        mParticles = mParticleFactory.getParticleList(bitmapFromView, rect);
    }
    public void draw(Canvas canvas) {
        if(!isStarted()) { //动画结束时停止
            return;
        }
        //所有粒子运动
        for (BoomParticle[] particle : mParticles) {
            for (BoomParticle p : particle) {
                p.advance(canvas,mPaint,(Float) getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }
    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }


}
