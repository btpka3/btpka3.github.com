package io.github.btpka3.hi;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * 通过Messenger返回消息。
 * http://www.open-open.com/lib/view/open1437709353974.html
 */
public class MyTime2IntentService extends IntentService {
    public static final String TAG = "btpka3";
    // TODO: Rename actions, choose action names that describe tasks that this

    public static final int MSG_TIME = 12;
    public static final int MSG_TIME_DATA = 13;

    NotificationManager mNM;

    public MyTime2IntentService() {
        super("MyTime2IntentService");

        Log.e(TAG, "MyTime2IntentService created !!!!!!!!!!!!");
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "MyTime2IntentService onCreate  ");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "MyTime2IntentService onDestroy  ");
    }

    // @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "MyTime2IntentService onHandleIntent : " + intent);
    }


    private boolean timeRunning = false;

    public boolean isTimeRunning() {
        return timeRunning;
    }


    private Messenger mMessenger = new Messenger(new Handler() {

        @Override
        public void handleMessage(Message msgfromClient) {
            Log.e(TAG, "MyTime2IntentService handleMessage !!!!!!!!!!!!" + msgfromClient);

//            Message msgToClient = Message.obtain(msgfromClient);
            switch (msgfromClient.what) {
                //msg 客户端传来的消息
                case MSG_TIME:

                    if (isTimeRunning()) {
                        return;
                    }

                    timeRunning = true;

                    int time = msgfromClient.arg1;

                    for (int i = 0; i < time; i++) {

                        try {
                            Thread.sleep(1000);

                            // 返回给客户端的消息
                            Message msgToClient = Message.obtain(null, MyTime2IntentService.MSG_TIME);

                            CharSequence timeStr = DateFormat.format("hh:mm:ss", System.currentTimeMillis());

                            Bundle mBundle = new Bundle();
                            mBundle.putString("timeStr", timeStr.toString());
                            msgToClient.setData(mBundle);
                            Log.e(TAG, "MyTime2IntentService rely mBundle = " + mBundle);

                            msgfromClient.replyTo.send(msgToClient);


                            Log.e(TAG, "MyTime2IntentService rely message !!!!!!!!!!!!" + msgToClient);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    timeRunning = false;

                    break;
                default:
                    Log.e(TAG, "MyTime2IntentService  defalut  !!!!!!!!!!!!" + msgfromClient);
                    break;

            }

            super.handleMessage(msgfromClient);
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "MyTime2IntentService onBind !!!!!!!!!!!!");
        return mMessenger.getBinder();
    }
}
