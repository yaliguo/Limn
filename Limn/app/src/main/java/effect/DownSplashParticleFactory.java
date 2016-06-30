package effect;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by pe on 2015/12/21.
 * 从下方渐散粒子效果
 */
public class DownSplashParticleFactory extends BoomParticleFactory {
    public static final int PART_WH = 7; //小球长宽
    @Override
    public BoomParticle[][] getParticleList(Bitmap bitmap, Rect bound) {
        int w = bound.width();//场景宽度
        int h = bound.height();//场景高度

        int partW_Count = w / PART_WH; //横向个数
        int partH_Count = h / PART_WH; //竖向个数


        int bitmap_part_w = bitmap.getWidth() / partW_Count;
        int bitmap_part_h = bitmap.getHeight() / partH_Count;

        BoomParticle[][] particles = new BoomParticle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row ++) { //行
            for (int column = 0; column < partW_Count; column ++) { //列
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);

                float x = bound.left + DownSplashParticleFactory.PART_WH * column;
                float y = bound.top + DownSplashParticleFactory.PART_WH * row;
                particles[row][column] = new DownSplashParticle(color,x,y,bound);
            }
        }
        return particles;
    }
}
