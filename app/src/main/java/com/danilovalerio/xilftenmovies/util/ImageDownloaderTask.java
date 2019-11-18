package com.danilovalerio.xilftenmovies.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;

    public ImageDownloaderTask(ImageView imageView){
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String urlImg = params[0];
        HttpURLConnection urlConnection = null;

        try {
            URL requestUrl = new URL(urlImg);

            urlConnection = (HttpsURLConnection) requestUrl.openConnection();
            //espera somente 2 seg.
            urlConnection.setReadTimeout(2000);
            //quando a internet caiu, espera 2 seg. para mostrar o erro
            urlConnection.setConnectTimeout(2000);

            int responseCode = urlConnection.getResponseCode();
            if(responseCode != 200){
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if(inputStream != null){
                //decodifica stream para bityes de imagem
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled())
            bitmap = null;

        ImageView imageView = imageViewWeakReference.get();
        if(imageView != null && bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
    }
}
