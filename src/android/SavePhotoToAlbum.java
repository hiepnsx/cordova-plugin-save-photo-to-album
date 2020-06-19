package net.apache.cordova.plugins;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Arrays;
import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.*;
import android.provider.MediaStore;
import android.media.MediaScannerConnection;

/**
 * SavePhotoToAlbum.java
 *
 * Extended Android implementation of the Base64ToGallery for Android. Inspirated by
 * StefanoMagrassi's code https://github.com/Nexxa/cordova-base64-to-gallery
 *
 * @author Alejandro Gomez <agommor@gmail.com>
 */
public class SavePhotoToAlbum extends CordovaPlugin {


    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext)
            throws JSONException {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    final String url = args.getString(0);
                    String filename = url.substring(url.lastIndexOf('/') + 1).split("\\?")[0].split("#")[0];
                    getImage(url, filename);

                    callbackContext.success();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return true;
    }

    private void getImage(String url, String filename) {

        File albumPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/thanksgift");
        File file = new File(albumPath, filename);

        try {
            // Make sure the Pictures directory exists.
            albumPath.mkdirs();

            if (!file.exists()) {
                file.createNewFile();
            }

            InputStream is = getImageStream(url);
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;
            while ((n = is.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }

            fos.close();
            is.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(cordova.getActivity().getApplicationContext(),
                    new String[] { file.toString() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });

        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public InputStream getImageStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }
}
