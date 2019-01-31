package demo.randompage.modules.ThirdView.CountDown;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import demo.randompage.R;


/**
 * Created by panwenjuan on 17-10-24.
 */
public class FloatCountDownView extends FrameLayout {

    private static final int DEFAULT_COUNT_DOWN = 3;
    public static WindowManager.LayoutParams winParams = new WindowManager.LayoutParams();

    private Context mContext;
    private TextView mCountTv;
    private CountDownAnimation mCountDownAnimation;

    public FloatCountDownView(Context context) {
        super(context);
        mContext = context;
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.count_down_anim_layout, null);
        mCountTv = (TextView) frameLayout.findViewById(R.id.tv_count);
        removeAllViews();
        addView(frameLayout, winParams);
        initCountDownAnimation();
    }

    public FloatCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void initCountDownAnimation() {
        if (mCountTv == null) {
            return;
        }
        mCountDownAnimation = new CountDownAnimation(mCountTv, getStartCount());
        mCountDownAnimation.setCountDownListener(new CountDownAnimation.CountDownListener() {
            @Override
            public void onCountDownEnd(CountDownAnimation animation) {
//                FloatCountDownManager.getInstant(mContext).removeCountDownView();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        ScreenRecorderManager.getInstance(mContext).startScreenRecord();
                    }
                }, 500);
            }
        });
    }

    private int getStartCount() {
        int count = 3;
//                (int) SharedPreferencesUtil.get(mContext, Constants.PREF_COUNT_DOWN_KEY, DEFAULT_COUNT_DOWN);
        return count;
    }

    public void startCountDownAnimation() {
        if (mCountDownAnimation == null) {
            return;
        }
        Animation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
                1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(500);
        mCountDownAnimation.setAnimation(animationSet);

        // Customizable start count
        mCountDownAnimation.setStartCount(getStartCount());
        mCountDownAnimation.start();
    }

    public void cancelCountDownAnimation() {
        if (mCountDownAnimation != null) {
            mCountDownAnimation.cancel();
            mCountDownAnimation.setCountDownListener(null);
        }
    }
}
