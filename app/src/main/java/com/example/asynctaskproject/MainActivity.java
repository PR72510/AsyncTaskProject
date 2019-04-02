package com.example.asynctaskproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    MyBroadcastReciever reciever;
    static MainActivity activity;
    private static final String TAG = "MainActivity";
    ProgressBar mProgressBar;
    String src = "https://i.redd.it/efcrd1vc4ql21.jpg";
    Button btn_dwld, btn_dwld_ser;
    ImageView image;
//    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reciever = new MyBroadcastReciever();

        activity = this;
        btn_dwld = (Button) findViewById(R.id.btn_dwld);
        btn_dwld_ser = (Button) findViewById(R.id.btn_dwld_ser);
        image = (ImageView) findViewById(R.id.image);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        pDialog = new ProgressDialog(this);


        btn_dwld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                new DownloadTask(MainActivity.this).execute(src);
            }
        });

        btn_dwld_ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MyServices.class);
                intent.putExtra("URL", src);
                Log.i(TAG, "onClick: Service");
                startService(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(reciever, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(reciever);
    }

    public static MainActivity getInstance() {
        return activity;
    }
}