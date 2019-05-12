package com.buddman.zepespot.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkDrawable {

    public static Drawable drawableFromUrl(String _url) throws IOException {
        try {
            AsyncTask<String, Void, Drawable> asyncTask = new AsyncTask<String, Void, Drawable>() {
                @Override
                protected Drawable doInBackground(String... url) {
                    Bitmap x;

                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) new URL(_url).openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        x = BitmapFactory.decodeStream(input);
                        return new BitmapDrawable(x);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
            return asyncTask.execute(_url).get();
        } catch (Exception e) {
            // No error
            return null;
        }

    }

}
