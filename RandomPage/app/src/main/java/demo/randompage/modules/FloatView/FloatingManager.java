package demo.randompage.modules.FloatView;

/**
 * Created by smy on 18-4-3.
 */


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import demo.randompage.R;
import demo.randompage.modules.ThirdView.player.JCVideoPlayer;
import demo.randompage.modules.ThirdView.player.JCVideoPlayerStandard;


/**
 * Created by liruiyuan on 2016/1/19.
 * 1浮动窗口
 * 2自动靠边
 */
public class FloatingManager extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    private static final long SLIDE_TO_BOUNDARY_TIME = 500;
    private static final int SLIDE_TO_BOUNDARY_FLAG = 10;


    //定义浮动窗口布局
    private FrameLayout mlayout;
    //悬浮窗控件
//    private ImageView mfloatingIv;
    private ImageView mTouch;
    private MyJCVideoPlayerStandard mMyJCVideoPlayerStandard;
    //悬浮窗的布局
    private WindowManager.LayoutParams wmParams;
    private LayoutInflater inflater;
    //创建浮动窗口设置布局参数的对象
    private WindowManager mWindowManager;
    private int mScreenWidth,mScreenHeight;
    private int mIconWidth, mIconHeight;
    //触摸监听器
    private GestureDetector mGestureDetector;
    private Context context;
    private boolean isExit;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SLIDE_TO_BOUNDARY_FLAG:
                    MotionEvent event = (MotionEvent) msg.obj;
                    Log.d("1234", "handleMessage:  mScreenWidth = " + mScreenWidth);
                    slideToBound(mlayout, event.getRawX(), mScreenWidth);
                    break;
            }
        }
    };

    public FloatingManager(Context context) {
        this.context = context;
        isExit = false;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
        Log.d("1234", "w = "+ mScreenWidth + "; h = " + mScreenHeight);
    }

    public void open(){
        if (!isExit){
            initWindow(context);
            initFloating();//设置悬浮窗图标
            isExit = true;
        }
    }

    public void close(){
        if (mlayout != null && isExit) {
            isExit = false;
            mWindowManager.removeViewImmediate(mlayout);
        }
    }

    /**
     * 初始化windowManager
     */
    private void initWindow(Context context) {

        //得到容器，通过这个inflater来获得悬浮窗控件
        inflater = LayoutInflater.from(context);
        // 获取浮动窗口视图所在布局
        mlayout = (FrameLayout) inflater.inflate(R.layout.floating_layout, null);
        mIconWidth = mlayout.getWidth();
        mIconHeight = mlayout.getHeight();

        wmParams = getParams(wmParams);//设置好悬浮窗的参数
        // 悬浮窗默认显示以左上角为起始坐标
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //悬浮窗的开始位置，因为设置的是从左上角开始，所以屏幕左上角是x=0;y=0
        //设置靠右边三分之二的高度处
        wmParams.x = mScreenWidth - mIconWidth;
        wmParams.y = mScreenHeight / 3 - mIconHeight / 2;

        // 添加悬浮窗的视图
        mWindowManager.addView(mlayout, wmParams);
    }

    /** 对windowManager进行设置
     * @param wmParams
     * @return
     */
    public WindowManager.LayoutParams getParams(WindowManager.LayoutParams wmParams){
        wmParams = new WindowManager.LayoutParams();
        //设置window type 下面变量2002是在屏幕区域显示，2003则可以显示在状态栏之上
        //wmParams.type = LayoutParams.TYPE_PHONE;
        //wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
        //wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;//依赖应用而存在其他需要权限
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        //wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置可以显示在状态栏上
        wmParams.flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR|
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return wmParams;
    }

    /**
     * 找到悬浮窗的图标，并且设置事件
     * 设置悬浮窗的点击、滑动事件
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initFloating() {
//        mfloatingIv = (ImageButton) mlayout.findViewById(R.id.floating_imageView);
//        mfloatingIv.getBackground().setAlpha(0);
        mTouch = (ImageButton)mlayout.findViewById(R.id.floating_View);
        mMyJCVideoPlayerStandard = (MyJCVideoPlayerStandard)mlayout.findViewById(R.id.jc_video);

        mMyJCVideoPlayerStandard.setUp("http://video.jiecao.fm/11/23/xin/%E5%81%87%E4%BA%BA.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子不信");
        Picasso.with(context)
                .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
                .into(mMyJCVideoPlayerStandard.thumbImageView);

        mGestureDetector = new GestureDetector(context, this);
        mTouch.getBackground().setAlpha(0);
        mTouch.setOnTouchListener(this);
//
// mfloatingIv.setOnTouchListener(this);

    }

    //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private int mTouchStartX,mTouchStartY,mTouchCurrentX,mTouchCurrentY;
    //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private int mStartX,mStartY,mStopX,mStopY;
    private boolean isMove;//判断悬浮窗是否移动

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                isMove = false;
                mTouchStartX = (int)event.getRawX();
                mTouchStartY = (int)event.getRawY();
                mStartX = (int)event.getX();
                mStartY = (int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchCurrentX = (int) event.getRawX();
                mTouchCurrentY = (int) event.getRawY();
                wmParams.x += mTouchCurrentX - mTouchStartX;
                wmParams.y += mTouchCurrentY - mTouchStartY;
                mWindowManager.updateViewLayout(mlayout, wmParams);

                mTouchStartX = mTouchCurrentX;
                mTouchStartY = mTouchCurrentY;
                break;
            case MotionEvent.ACTION_UP:
                mStopX = (int)event.getX();
                mStopY = (int)event.getY();
                if(Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1){
                    isMove = true;
                }
                //先让控件随手势滑动指定位置再进行靠边
                Message msg = Message.obtain();
                msg.what = SLIDE_TO_BOUNDARY_FLAG;
                msg.obj = event;
                handler.sendMessageAtTime(msg, 200);
                break;
        }
        //这里需返回是否触摸事件，否则OnClickListener获取不到监听
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (!isMove) {
            Toast.makeText(context, "你点击了悬浮窗", Toast.LENGTH_SHORT).show();
        }
        return super.onSingleTapConfirmed(e);
    }

    /**
     * 自动靠边
     * @param view
     * @param rawX
     * @param totalWidth
     */
    private void slideToBound(final View view, float rawX, int totalWidth){
        float toX;
        if (rawX < totalWidth / 4) {
            toX = 0;
        } else {
            toX = totalWidth - view.getWidth() / 2;
        }
//        Log.d("1234", "rawX = "+ rawX + " : toX = "+ toX);
        // 创建从当前x到目标x的过程
        ValueAnimator mSlideToBoundaryAnim = ValueAnimator.ofFloat(rawX, toX);
        // 设置动画运行时间
        mSlideToBoundaryAnim.setDuration(SLIDE_TO_BOUNDARY_TIME);
        // 减速效果，为了贴边的时候显得柔和
        mSlideToBoundaryAnim.setInterpolator(new DecelerateInterpolator());
        mSlideToBoundaryAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 拿出当前时刻回调的值
                float currentValue = (Float) valueAnimator.getAnimatedValue();
                // 将值不断设给浮动控件的layoutParams，从而形成动效
                wmParams.x = (int) currentValue;
                fixLayoutParams(wmParams);
                // 改变WindowManager添加的view的位置
                mWindowManager.updateViewLayout(view, wmParams);
            }
        });
//        mSlideToBoundaryAnim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Log.d("1234", "wmParams.x  = " + wmParams.x );
//            }
//        });
        mSlideToBoundaryAnim.start();
    }

    // 对x,y的值做修复
    private void fixLayoutParams(WindowManager.LayoutParams layoutParams) {
        int x = layoutParams.x;
        int y = layoutParams.y;
        y = y > mScreenHeight ? mScreenHeight : y < 0 ? 0 : y;
        x = x < 0 ? 0 : x > (mScreenWidth - mIconWidth) ? (mScreenWidth - mIconWidth) : x;
        layoutParams.x = x;
        layoutParams.y = y;
    }

}
