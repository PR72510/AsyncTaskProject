package com.example.asynctaskproject;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MyServices extends IntentService {

    Bitmap bmImg = null;

    MainActivity activity = MainActivity.getInstance();

    private static final String TAG = "MyServices";

    public MyServices() {
        super("MyServices");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Toast.makeText(this,"Service Started", Toast.LENGTH_LONG).show();
       String url = intent.getStringExtra("URL");
        Log.i(TAG, "onHandleIntent: " + url);


        try {
            Log.i(TAG, "onHandleIntent: 1st");
            int count;
            URL url1 = new URL(url);

            URLConnection urlConnection = url1.openConnection();
            urlConnection.connect();

            InputStream inputStream = new BufferedInputStream(url1.openStream());
            byte[] data1 = new byte[1024];

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Log.i(TAG, "onHandleIntent: 2nd");
            /*returns int in the range of 0 to 255. If no byte is
             available because the end of the stream has been reached, the returned value is -1.
              */
            while((count = inputStream.read(data1)) != -1){             // Reads some number of bytes from the input stream and stores them into the buffer array b
                outputStream.write(data1, 0 , count);           // Writes len bytes from the specified byte array starting at offset off to this output stream
            }
            byte[] imageData = outputStream.toByteArray();

            bmImg = BitmapFactory.decodeByteArray(imageData, 0,imageData.length);
            Log.i(TAG, "onHandleIntent: 3rd" + bmImg);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        activity.image.setImageBitmap(bmImg);

        Log.i(TAG, "onDestroy: ");
    }

}
