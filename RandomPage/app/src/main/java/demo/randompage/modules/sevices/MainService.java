package demo.randompage.modules.sevices;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;

import demo.randompage.modules.main.NetTestActivity;

import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;

public class MainService extends Service {
   private static final String TAG = "MainService";

   private static final int STATE_IDLE = 0;
    private static final int STATE_RINGING = 1;
    private static final int STATE_CALLING = 2;

   private CallFlashReceiver mCallFlashReceiver;
    private int mCallState = STATE_IDLE;
    private boolean mIsCallOutGoing = false;
    private long mStartTime = 0;



    @Override
    public void onCreate() {
        super.onCreate();
        registerCallReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterCallReceiver();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startMainService(Context context) {
        Intent intent = new Intent(context, MainService.class);
        startMainService(context,intent);
    }
    public static void startMainService(final Context context, final Intent intent) {
        Log.v("MainService", "RandomPage startMainService()");
        if (Build.VERSION.SDK_INT >= 26) {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    context.startForegroundService(intent);
                }
            },1);
        } else {
            context.startService(intent);
        }
    }


    public void registerCallReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        mCallFlashReceiver = new CallFlashReceiver();
        registerReceiver(mCallFlashReceiver, intentFilter);
    }

    public void unregisterCallReceiver() {
        unregisterReceiver(mCallFlashReceiver);
    }

    public class CallFlashReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
//                mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                if (mTelephonyManager != null) {
//                    mTelephonyManager.listen(mPhoneState, PhoneStateListener.LISTEN_CALL_STATE);
//                }
                try {
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        Log.d(TAG, "switch CallState = EXTRA_STATE_IDLE" + state);
                        callStateIdle(incomingNumber);
                    }
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        Log.d(TAG, "switch CallState = EXTRA_STATE_RINGING" + state);
                        callStateRinging(incomingNumber);
                    }
                    if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        Log.d(TAG, "switch CallState = EXTRA_STATE_OFFHOOK" + state);
                        callStateOffhook();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void callStateIdle(String incomingNumber) {
        // Handle outgoing call.
//                    if (!mIsCallOutGoing) {
//        if (!PrimeManager.isAdsRemove(FlashMainService.this)) {
////                        ResultNativeAdCache cache = ResultNativeAdCache.getInstance(FlashMainService.this);
////                        cache.init(FlashMainService.this);
//
////            mAdCheckCount = 0;
//
//            ResultData data = null;
//
//            if (mCallState == STATE_RINGING) {
//                data = new ResultData(END_TYPE_MISSING, incomingNumber, 0l);
//                hideCallScreenUI();
//            } else if (mCallState == CALL_STATE_OFFHOOK) {
//                long duration = System.currentTimeMillis() - mStartTime;
//                data = new ResultData(mIsCallOutGoing ? END_TYPE_CALLOUT : END_TYPE_COMPLETE, incomingNumber, duration);
//                hideCallScreenUI();
//            }
//
//            Message message = new Message();
//            message.what = MSG_CHECK_AD;
//            if (data != null) {
//                message.obj = data;
//            }
//            mHandler.sendMessageDelayed(message, 200);
//        } else {
            if (mCallState == STATE_RINGING) {
                showCallResultUI(incomingNumber,0l);
//                showCallResultUI(new ResultData(END_TYPE_MISSING, incomingNumber, 0l));
//                hideCallScreenUI();
            } else if (mCallState == CALL_STATE_OFFHOOK) {
                long duration = System.currentTimeMillis() - mStartTime;
                showCallResultUI(incomingNumber,duration);
//                showCallResultUI(new ResultData(mIsCallOutGoing ? END_TYPE_CALLOUT : END_TYPE_COMPLETE, incomingNumber, duration));
//                hideCallScreenUI();
            }
//        }
//                    }

        mCallState = STATE_IDLE;
        mStartTime = 0l;
//        stopCallFlash();
        mIsCallOutGoing = false;
    }

    private void callStateRinging(String incomingNumber) {
//        showCallScreenUI(incomingNumber);
        mIsCallOutGoing = false;
        mCallState = STATE_RINGING;
        mStartTime = 0l;
//        startCallFlash();
    }

    private void callStateOffhook() {
        Log.d(TAG, "CALL_STATE_OFFHOOK mCallState = " + mCallState);
//        hideCallScreenUI();
        mIsCallOutGoing = mCallState != TelephonyManager.CALL_STATE_RINGING;
        mCallState = STATE_CALLING;
        mStartTime = System.currentTimeMillis();
//        stopCallFlash();
    }
    void showCallResultUI(String incomingNumber,long durtion){
        Intent intent = new Intent(this,NetTestActivity.class);
        intent.putExtra("phone_number",incomingNumber);
        intent.putExtra("phone_time",durtion);
        startActivity(intent);
     }

}
