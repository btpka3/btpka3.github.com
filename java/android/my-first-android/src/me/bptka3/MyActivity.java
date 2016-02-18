package me.bptka3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d("LOG_CAT", "程序开始启动...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d("LOG_CAT", "程序运行结束");
    }
}
