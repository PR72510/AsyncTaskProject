package com.example.asynctaskproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends AsyncTask<String, String, Bitmap> {

    boolean resumeDownload = true;
    public static final String FILE_NAME_ASYNC = "/imge_Async.jpeg";
    Bitmap bitImage = null;
    private static final String TAG = "DownloadTask";
    private WeakReference<MainActivity> activityWeakReference;

    public DownloadTask(MainActivity activity) {
        activityWeakReference = new WeakReference<MainActivity>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity activity = activityWeakReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Log.i(TAG, "onPreExecute: ");

        activity.mProgressBar.setIndeterminate(false);
        activity.mProgressBar.setVisibility(View.VISIBLE);


//        activity.pDialog.setMessage("Downloading file. Please wait...");
//        activity.pDialog.setIndeterminate(false);
//        activity.pDialog.setMax(100);
//        activity.pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        activity.pDialog.setCancelable(true);
//        activity.pDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... data) {


        Log.i(TAG, "doInBackground:  " + String.valueOf(data));
        try {
            int count;
            URL url = new URL(data[0]);                                // URL object

            URLConnection urlConnection = url.openConnection();         // connection object is created
            urlConnection.connect();                                    // actual connection to the remote object is made

            int fileLength = urlConnection.getContentLength();
            Log.i(TAG, "doInBackground: " + fileLength);

            InputStream inputStream = new BufferedInputStream(url.openStream());
            byte[] data1 = new byte[1024];                               // Get information in 1kb

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            long total = 0;

                while ((count = inputStream.read(data1)) != -1) {  // Reads some number of bytes from the input stream and stores them into the buffer array b
                    total += count;
                    publishProgress("" + (int) ((total * 100) / fileLength));

                    outputStream.write(data1, 0, count);           // Writes len bytes from the specified byte array starting at offset off to this output stream

                }

            byte[] imageData = outputStream.toByteArray();

            bitImage = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Bitmap bitmap = null;
//        try {
//            // Download Image from URL
//            InputStream input = new java.net.URL(imageURL).openStream();   //openStream() methods provides a stream from which we can read contents of URL
//            // Decode Bitmap
//            bitmap = BitmapFactory.decodeStream(input);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return bitImage;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        MainActivity activity = activityWeakReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        activity.mProgressBar.setMax(100);
        activity.mProgressBar.setProgress(Integer.parseInt(values[0]));
        Log.i(TAG, "onProgressUpdate: " + values[0]);
//        activity.pDialog.setProgress(Integer.parseInt(values[0]));
    }

    @Override

    protected void onPostExecute(Bitmap result) {

        MainActivity activity = activityWeakReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        } // Set the bitmap into ImageView
        Log.i(TAG, "onPostExecute: ");
        activity.image.setImageBitmap(result);
        activity.mProgressBar.setVisibility(View.INVISIBLE);

//        activity.pDialog.hide();
    }

//    @Override
//    public void mOnStateChangeListener(boolean isConnected) {
//        if (isConnected && resumeDownload) {
//            Toast.makeText(this, "Network available and connected", Toast.LENGTH_SHORT).show();
//            //execute async task
//            new DownloadTask().execute();
//        } else {
//            Toast.makeText(this, "Network not available OR Download cancelled.", Toast.LENGTH_SHORT).show();
//            if (progressDialog.isShowing())
//                progressDialog.dismiss();
//        }
//    }
}
