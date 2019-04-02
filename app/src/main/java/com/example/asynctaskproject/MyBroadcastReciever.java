package com.example.asynctaskproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class MyBroadcastReciever extends BroadcastReceiver {

//    static INetState iNetState;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean notConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if(notConnected){
                Toast.makeText(context, "Disconnected", Toast.LENGTH_LONG).show();
//                iNetState.mOnStateChangeListener(false);
            }
            else{

                Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
//                iNetState.mOnStateChangeListener(true);
            }
        }
    }

//    public static void setConnectivityListener(MyBroadcastReciever.INetState iNetStateChangeOb) {
//        //this method is used to instantiate connectivity listener
//        iNetState = iNetStateChangeOb;
//    }

//    public interface INetState{
//        void mOnStateChangeListener(boolean isConnected);
//    }
}
