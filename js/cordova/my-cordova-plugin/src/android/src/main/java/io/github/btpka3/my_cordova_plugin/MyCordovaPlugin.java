package io.github.btpka3.my_cordova_plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;

public class MyCordovaPlugin extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // your init code here
    }


    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        if ("add".equals(action)) {
            this.add(args.getInt(0), args.getInt(1), callbackContext);
            return true;
        }

        return false;
    }

    void add(final int a, final int b, final CallbackContext callbackContext) {

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                int sum = a + b;
                try {
                    Thread.sleep(sum * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callbackContext.success();
            }
        });
    }
}
