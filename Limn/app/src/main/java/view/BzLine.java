package view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pojo.WeatherInfo;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import utils.DataUtils;
import utils.SystemUtils;

/**
 * Created by pe on 2016/2/26.
 */
public class BzLine extends View {
    private BzMode mMode;
    private float[] mCurrentPosition = new float[2];
    public static final int  M_VIEW_WIGHT = 6;     //默认的曲线横坐标段数
    public static final int M_VIEW_HEIGHT = 300;  //默认的view高度
    public static final int M_VIEW_LEFT_MARGIN =20; //曲线MarGin的距离
    private Point var1;
    private Point var2;
    BzInfo mInfo;                   //数据类
    private int mCoorX;
    private List<Point> mPts;
    private  Path  mAnimPath = new Path();   //绘制路径动画用到的path
    private boolean isAnim =false;
    private boolean isEnd =false;

    public BzLine(Context context, WeatherInfo infos,BzMode mode) {
        super(context);
        this.mMode=mode;
        init(context, infos);
    }

    private void init(Context context,WeatherInfo infos) {
        int screenWidth = SystemUtils.getScreenWidth(context);
        int screenHeight = SystemUtils.getScreenHeight(context);
        mCoorX = (screenWidth-2*M_VIEW_LEFT_MARGIN)/ M_VIEW_WIGHT;
        var1 = new Point();
        var2 = new Point();
        mInfo = CreateInfo(infos);
    }

    /**
     *
     * @param infos  将info中的数据分组
     * @return
     */
    private BzInfo CreateInfo(WeatherInfo infos) {
        BzInfo bzInfo = new BzInfo();
        for (int var = 0;var< infos.result.data.weather.size();var++) {
            String s = infos.result.data.weather.get(var).info.day.get(2);
            String d = infos.result.data.weather.get(var).info.night.get(2);
            String f = infos.result.data.weather.get(var).info.day.get(0);
            String g = infos.result.data.weather.get(var).info.day.get(1);
            bzInfo.highTms[var] = DataUtils.ValueOf(s);
            bzInfo.lowTms[var] = DataUtils.ValueOf(d);
            bzInfo.imgs[var] = DataUtils.ValueOf(f);
            bzInfo.days[var] = g;
        }
        //init paint
        Paint linePaint = new Paint();
        Paint textPaint = new Paint();
        Paint imgPaint = new Paint();
        Paint dayPaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLACK);
        textPaint.setColor(Color.GRAY);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20);

        bzInfo.mPaints[0]=linePaint;
        bzInfo.mPaints[1]=textPaint;
        bzInfo.mPaints[2]=imgPaint;
        bzInfo.mPaints[3]=dayPaint;

        return bzInfo;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        return M_VIEW_HEIGHT;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 绘制曲线路径动画
     * @param duration
     */
    public  void startAnimator(long duration){
        isAnim = true;
        PathMeasure pathMeasure = new PathMeasure(getPzPath(mInfo), false);
        ValueAnimator va = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        va.setDuration(duration);
        va.addUpdateListener(animation -> {
            float animatedValue = (float) va.getAnimatedValue();
            if(animatedValue==pathMeasure.getLength()){
                isEnd = true;
            }
            pathMeasure.getPosTan(animatedValue, mCurrentPosition, null);
            postInvalidate();
        });
        va.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(!isAnim){
            Path mPath =  getPzPath(mInfo);
            canvas.drawPath(mPath, mInfo.mPaints[0]);
            for(int var = 0;var < 7;var++){
                if(mMode==BzMode.TEMPERATURE_h){
                    canvas.drawText(mInfo.highTms[var]+"℃",mPts.get(var).x,mPts.get(var).y-15,mInfo.mPaints[1]);
                }else {
                    canvas.drawText(mInfo.lowTms[var]+"℃",mPts.get(var).x,mPts.get(var).y+25,mInfo.mPaints[1]);
                }
            }
        }else {
            mAnimPath.lineTo(mCurrentPosition[0], mCurrentPosition[1]);
            CornerPathEffect cornerPathEffect = new CornerPathEffect(10);
            mInfo.mPaints[0].setPathEffect(cornerPathEffect);
            canvas.drawPath(mAnimPath,mInfo.mPaints[0]);
            if(isEnd){
                for(int var = 0;var < 7;var++){
                    if(mMode==BzMode.TEMPERATURE_h){
                        canvas.drawText(mInfo.highTms[var]+"℃",mPts.get(var).x,mPts.get(var).y-15,mInfo.mPaints[1]);
                    }else {
                        canvas.drawText(mInfo.lowTms[var]+"℃",mPts.get(var).x,mPts.get(var).y+25,mInfo.mPaints[1]);
                    }
                }
            }
        }
    }

    /**
     * 获得贝塞尔路线
     * @param infos
     * @return
     */
    private Path getPzPath(BzInfo infos) {
        int [] nowTms;
        if(mMode==BzMode.TEMPERATURE_h) {
            nowTms = infos.highTms;
        }else {
            nowTms =infos.lowTms;
        }
        /**对温度点对坐标的转化     y =  20度的范围，中间就是10 （300）
         ——————————————————————————
         |
         |
         |
         150|-------------------------
         |
         |
         300|
         1  2   3   4   5   6   7
         */
        //坐标点集合
        mPts = new ArrayList<>();
        int minTmp = nowTms[0];
        //min值，计算减掉min值保证从中间开始
        for (int temp : nowTms) {
            minTmp =  temp <minTmp? temp:minTmp;
        }
        for (int var4 = 0; var4 < 7; var4++) {                  //150位置是中线点，减去的就是温度的偏移量
            Point point = new Point(var4 * mCoorX, M_VIEW_HEIGHT / 2 - (nowTms[var4]-minTmp)*10);
            mPts.add(point);
        }
        Path path = new Path();
        mAnimPath.moveTo(M_VIEW_LEFT_MARGIN, M_VIEW_HEIGHT /2- (nowTms[0]-minTmp)*10);
        path.moveTo(M_VIEW_LEFT_MARGIN, M_VIEW_HEIGHT /2- (nowTms[0]-minTmp)*10);
        for (int i = 1; i < mPts.size() - 1; i++) {
            Point startTp = mPts.get(i);
            Point endTp = mPts.get(i + 1);
            int centerX = (int) (startTp.x + endTp.x) / 2;
            int centerY = (int) (startTp.y + endTp.y) / 2;
            var1.y = startTp.y;
            var1.x = centerX;
            var2.y = endTp.y;
            var2.x = centerX;
            path.cubicTo(var1.x, var1.y, var2.x, var2.y, endTp.x, endTp.y);
        }

        return path;
    }

    public class  BzInfo{
        int [] highTms = new int[7];
        int [] lowTms = new int[7];
        int [] imgs = new int [7];
        String [] days = new String [7];
        Paint [] mPaints = new Paint[4];
    }
    public static class Builder {
        private BzMode mode = BzMode.TEMPERATURE_h;
        private Context context;
        private WeatherInfo info;
        public Builder(Context context){
            this.context=context;
        }
        public BzLine create (){
            return  new BzLine(context,info,mode);
        }
        public Builder configMode(BzMode mode){
            this.mode=mode;
            return this;
        }
        public Builder confinTempperInfo(WeatherInfo info){
            this.info=info;
            return  this;
        }
    }
    public  enum  BzMode{
       TEMPERATURE_h,
       TEMPERATURE_l
    }
}
