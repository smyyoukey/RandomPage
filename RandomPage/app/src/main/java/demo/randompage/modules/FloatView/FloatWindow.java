package demo.randompage.modules.FloatView;

/**
 * Created by smy on 18-4-2.
 */
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;

public class FloatWindow {

    /**
     * Created by john on 2017/3/10.
     */
        private final Context mContext;
        private WindowManager windowManager;
        private View floatView;
        private WindowManager.LayoutParams params;

        public FloatWindow(Context mContext) {
            this.mContext = mContext;
            this.params = new WindowManager.LayoutParams();
        }


        /**
         * 显示浮动窗口
         * @param view
         * @param x    view距离左上角的x距离
         * @param y    view距离左上角的y距离
         */
        void show(View view, int x, int y) {
            this.windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.format = PixelFormat.TRANSLUCENT;
            params.x = x;
            params.y = y;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | FLAG_NOT_FOCUSABLE | FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
            floatView = view;
                windowManager.addView(floatView, params);
        }


        /**
         * 显示浮动窗口
         * @param view
         * @param x
         * @param y
         * @param listener  窗体之外的监听
         * @param backListener 返回键盘监听
         */

        void show(View view, int x, int y, OutsideTouchListener listener, KeyBackListener backListener) {
            this.windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            final FloatWindowContainerView containerView = new FloatWindowContainerView(this.mContext, listener, backListener);
            containerView.addView(view, WRAP_CONTENT, WRAP_CONTENT);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.format = PixelFormat.TRANSLUCENT;
            params.x = x;
            params.y = y;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
//
//        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                | WindowManager.LayoutParams. FLAG_NOT_FOCUSABLE ;

            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

            floatView = containerView;
            windowManager.addView(floatView, params);
        }

        /**
         * 更新view对象文职
         *
         * @param offset_X x偏移量
         * @param offset_Y Y偏移量
         */
        public void updateWindowLayout(float offset_X, float offset_Y) {
            params.x += offset_X;
            params.y += offset_Y;
            windowManager.updateViewLayout(floatView, params);
        }

        /**
         * 关闭界面
         */
        void dismiss() {
            if (this.windowManager == null) {
                this.windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            }
            if (floatView != null) {
                windowManager.removeView(floatView);
            }
            floatView = null;
        }

        public void justHideWindow() {
            this.floatView.setVisibility(View.GONE);
        }


        private class FloatWindowContainerView extends FrameLayout {

            private OutsideTouchListener listener;
            private KeyBackListener backListener;

            public FloatWindowContainerView(Context context, OutsideTouchListener listener, KeyBackListener backListener) {
                super(context);
                this.listener = listener;
                this.backListener = backListener;
            }


            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (getKeyDispatcherState() == null) {
                        if (backListener != null) {
                            backListener.onKeyBackPressed();
                        }
                        return super.dispatchKeyEvent(event);
                    }

                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                        KeyEvent.DispatcherState state = getKeyDispatcherState();
                        if (state != null) {
                            state.startTracking(event, this);
                        }
                        return true;
                    } else if (event.getAction() == KeyEvent.ACTION_UP) {
                        KeyEvent.DispatcherState state = getKeyDispatcherState();
                        if (state != null && state.isTracking(event) && !event.isCanceled()) {
                            System.out.println("dsfdfdsfds");
                            if (backListener != null) {
                                backListener.onKeyBackPressed();
                            }
                            return super.dispatchKeyEvent(event);
                        }
                    }
                    return super.dispatchKeyEvent(event);
                } else {
                    return super.dispatchKeyEvent(event);
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if ((event.getAction() == MotionEvent.ACTION_DOWN)
                        && ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight()))) {
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    if (listener != null) {
                        listener.onOutsideTouch();
                    }
                    System.out.println("dfdf");
                    return true;
                } else {
                    return super.onTouchEvent(event);
                }
            }
        }
    }

