package view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by pe on 2015/12/17.
 */
public class SplashSurface extends SurfaceView implements SurfaceHolder.Callback,SensorEventListener {

    private SurfaceHolder holder;
    private int mWindowWidth;
    private int mWindowHeight;
    private SurfaceThread surfaceThread;
    private SensorManager mSensorMgr;
    private Context mContext;
    private Bitmap mBg;
    private RectF rectF;

    public SplashSurface(Context context) {
        super(context);
        init(context);

    }

    private void init(Context   context ) {
        mContext = context;
        holder = this.getHolder();
        holder.addCallback(this);
        WindowManager windowManager = ((Activity) context).getWindow().getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        mWindowWidth = defaultDisplay.getWidth();
        mWindowHeight = defaultDisplay.getHeight();
        surfaceThread = new SurfaceThread(holder);
        mSensorMgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rectF = new RectF(0, 0, mWindowWidth, mWindowHeight);
        //mBg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bg_sky);
    }

    public SplashSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SplashSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SplashSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class SurfaceThread extends  Thread {
        private SurfaceHolder holder;
        private boolean isRun =false;
        private Paint paint ;
        private float x;
        private float y;
        private float z;
        private float mx;
        private float my;
        public SurfaceThread(SurfaceHolder holder) {
                this.holder=holder;
                isRun=true;
                paint=new Paint();
                paint.setTextSize(40);
                paint.setColor(Color.GREEN);


        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {



            Sensor defaultSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorMgr.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    x = event.values[SensorManager.DATA_X];

                    y = event.values[SensorManager.DATA_Y];

                    z = event.values[SensorManager.DATA_Z];

                    // Log.e("MSG","xxx="+ x +"yyy="+ y +"zzz="+ z);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, defaultSensor, SensorManager.SENSOR_DELAY_GAME);


            while (isRun){


                Canvas canvas = holder.lockCanvas();

                mx -= x*2;
                my += y*2;

                canvas.drawBitmap(mBg, null, rectF, null);
                canvas.drawRect(mx, my, mx + 30, my + 30, paint);
                Path path = new Path();
                path.quadTo(mx,my,mWindowWidth,mWindowHeight);
                canvas.drawPath(path,paint);
                holder.unlockCanvasAndPost(canvas);

            }

//            long startTime = System.currentTimeMillis();
//            float v = (float) (mWindowWidth / Math.sqrt(2 * mWindowHeight *1.0f /400.78033f));
//
//            while (isRun){
//                float t = (System.currentTimeMillis() - startTime)/1000 *1.0f;
//
//                Canvas canvas = holder.lockCanvas();
//                Log.i("msg","xxxx"+v * t+"yyyyy"+0.5f * 400.78033f * t * t);
//                //canvas.drawPoint(v * t, 0.5f * 400.78033f * t * t, paint);
//                canvas.drawLine(0,0,v * t,0.5f * 400.78033f * t * t, paint);
//
//                holder.unlockCanvasAndPost(canvas);
//            }






        }
    }
}
