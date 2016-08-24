package io.github.btpka3.hi;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "btpka3";

    public static final String TIME_NOTIFY = "TIME_NOTIFY";
    public static final int MSG_KEY_TIME = 11;

    private Messenger mService;
    private ServiceConnection mConn;
    private Messenger replyTo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z1);


        // ------------------------- time
        final TextView textTime = (TextView) findViewById(R.id.textTime);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TIME_NOTIFY);
        BroadcastReceiver updateTime = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == TIME_NOTIFY) {
                    String timeStr = intent.getStringExtra(MyTimeIntentService.EXTRA_TIME_DATA);

                    // 更新
                    textTime.setText(timeStr);

                }

            }
        };
        registerReceiver(updateTime, filter);


        // 开始计时
        Button btn = (Button) findViewById(R.id.startTime);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!MyTimeIntentService.isTimeRunning()) {

                    MyTimeIntentService.startActionXxx(v.getContext(), 60);
                }
            }

        });


        // ------------------------- time 2


        final TextView textTime2 = (TextView) findViewById(R.id.textTime2);




        // 绑定服务

        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(TAG, "onServiceConnected!!!!!!!!!!!!");
                mService = new Messenger(service);

                replyTo = new Messenger(new Handler() {
                    @Override
                    public void handleMessage(Message msgFromServer) {
                        Log.e(TAG, "activity  = handleMessage !!!!!!!!!!!!!!!!!!!!" + msgFromServer);
                        switch (msgFromServer.what) {
                            case MyTime2IntentService.MSG_TIME:

                                String timeStr = msgFromServer.getData().getString("timeStr");
                                // 更新
                                textTime2.setText(timeStr);
                                break;
                        }
                        super.handleMessage(msgFromServer);
                    }
                });

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e(TAG, "onServiceDisconnected!!!!!!!!!!!!!!!!!!!!");
                mService = null;
            }
        };

        Intent intent = new Intent();
        intent.setAction("io.github.btpka3.hi.MyTime2IntentService");
        boolean bindResult = bindService(intent, mConn, Context.BIND_AUTO_CREATE);

        Log.e(TAG, "bindResult = " + bindResult + "!!!!!!!!!!!!!!!!!!!!");


        // 开始计时
        Button btn2 = (Button) findViewById(R.id.startTime2);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                textTime2.setText("1111111");
;
                if(mService==null){
                    return;
                }


                // 接收响应信息。FIXME 为何不能reply多个消息?
                Message msgFromClient = Message.obtain(null, MyTime2IntentService.MSG_TIME, 60, 0);
                msgFromClient.replyTo = replyTo;

                try {
                    mService.send(msgFromClient);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }



            }

        });


    }

    public final static String EXTRA_MESSAGE = "em";

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Main1Activity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    protected void onCreate1(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConn != null) {
            unbindService(mConn);
        }
    }


}
