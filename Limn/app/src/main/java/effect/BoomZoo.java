package effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import utils.BitmapUtils;

/**
 * Created by pe on 2015/12/21.
 * 场所
 */
public class BoomZoo extends View{
    private static final String TAG = "ExplosionField";
    private ArrayList<BoomAnimator> explosionAnimators;
    private HashMap<View,BoomAnimator> explosionAnimatorsMap;
    private View.OnClickListener onClickListener;
    private BoomParticleFactory mParticleFactory;

    /**
     * ��Ҫ����������
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (BoomAnimator animator : explosionAnimators) {
            animator.draw(canvas);
        }
    }

    public BoomZoo(Context context,BoomParticleFactory factory) {
        super(context);
        init(factory);
    }

    public BoomZoo(Context context, AttributeSet attrs,BoomParticleFactory factory) {
        super(context, attrs);
        init(factory);

    }

    public BoomZoo(Context context, AttributeSet attrs, int defStyleAttr,BoomParticleFactory factory) {
        super(context, attrs, defStyleAttr);
        init(factory);

    }

    private void init(BoomParticleFactory particleFactory) {
        explosionAnimators = new ArrayList<BoomAnimator>();
        explosionAnimatorsMap = new HashMap<View,BoomAnimator>();
        mParticleFactory = particleFactory;
        attach2Activity((Activity) getContext());
    }
    /**
     * ��Activity����ȫ�����ǵ�ExplosionField
     */
    private void attach2Activity(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this, lp);
    }

    /**
     * ������ʼ����ڷ���
     * ����
     * @param view ʹ�ø�view����
     */
    public void explode(final View view) {
        //��ֹ�ظ����
        if(explosionAnimatorsMap.get(view)!=null&&explosionAnimatorsMap.get(view).isStarted()){
            return;
        }
        if(view.getVisibility()!=View.VISIBLE||view.getAlpha()==0){
            return;
        }

        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);            //�õ�view�����������Ļ������
        int contentTop = ((ViewGroup)getParent()).getTop();
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        rect.offset(0, -contentTop - statusBarHeight);      //ȥ��״̬���߶Ⱥͱ������߶�
        if(rect.width()==0||rect.height()==0){
            return;
        }

        //�𶯶���
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            Random random = new Random();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((random.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((random.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                explode(view, rect);
            }
        });
        animator.start();
    }

    /**
     * ���ӵĵĶ�����ʼ���
     * @param view
     * @param rect
     */
    private void explode(final View view,Rect rect) {
        final BoomAnimator animator = new BoomAnimator(this, BitmapUtils.createBitmapFromView(view), rect,mParticleFactory);
        explosionAnimators.add(animator);
        explosionAnimatorsMap.put(view, animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //��С,͸������
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(150).start();

                //��������ʱ�Ӷ��������Ƴ�
                explosionAnimators.remove(animation);
                explosionAnimatorsMap.remove(view);
                animation = null;
            }
        });
        animator.start();
    }

}
