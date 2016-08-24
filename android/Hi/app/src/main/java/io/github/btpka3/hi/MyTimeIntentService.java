package io.github.btpka3.hi;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * 通过广播的形式返回消息。
 */
public class MyTimeIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_ZZZ = "io.github.btpka3.hi.action.ZZZ";
    private static final String ACTION_FOO = "io.github.btpka3.hi.action.FOO";
    private static final String ACTION_BAZ = "io.github.btpka3.hi.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_TIME = "io.github.btpka3.hi.extra.time";
    public static final String EXTRA_TIME_DATA = "io.github.btpka3.hi.extra.time.data";
    private static final String EXTRA_PARAM1 = "io.github.btpka3.hi.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "io.github.btpka3.hi.extra.PARAM2";

    public MyTimeIntentService() {
        super("MyTimeIntentService");
    }

    public static void startActionXxx(Context context, int time) {
        Intent intent = new Intent(context, MyTimeIntentService.class);
        intent.setAction(ACTION_ZZZ);
        intent.putExtra(EXTRA_TIME, time);

        context.startService(intent);
    }


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyTimeIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyTimeIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ZZZ.equals(action)) {
                final int time = intent.getIntExtra(EXTRA_TIME, 0);
                handleActionTime(time);
            } else if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }


    private static boolean timeRunning = false;

    public static boolean isTimeRunning() {
        return timeRunning;
    }


    public static final String TAG = "zll";

    private void handleActionTime(int time) {
        Log.d(TAG, "-------------- handleActionTime : " + time);

        timeRunning = true;

        for (int i = 0; i < time; i++) {

            try {
                Thread.sleep(1000);
                Intent intent = new Intent(MainActivity.TIME_NOTIFY);
                CharSequence timeStr = DateFormat.format("hh:mm:ss", System.currentTimeMillis());
                intent.putExtra(EXTRA_TIME_DATA, timeStr);
                sendBroadcast(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

        timeRunning = false;


    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
