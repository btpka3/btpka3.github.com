package io.github.btpka3.my_cordova_plugin;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;

import java.util.concurrent.atomic.AtomicInteger;

public class MyCordovaPlugin extends CordovaPlugin {

    final String TAG = "btpka3";

    // 为了演示成功与失败的计数器。
    final AtomicInteger counter = new AtomicInteger();

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // your init code here
    }


    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        if ("add".equals(action)) {
            Log.e(TAG, "======== android : args = [" + args.getInt(0) + ", " + args.getInt(1) + "]");
            this.add(args.getInt(0), args.getInt(1), callbackContext);
            return true;
        }

        return false;
    }

    void add(final int a, final int b, final CallbackContext callbackContext) {

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                Log.e(TAG, "======== android : running = [" + a + ", " + b + "]");
                int sum = a + b;
                try {
                    Thread.sleep(sum * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (counter.get() % 2 == 0) {
                    callbackContext.success(sum);
                } else {
                    callbackContext.error(sum);
                }
                counter.incrementAndGet();
            }
        });
    }
}
