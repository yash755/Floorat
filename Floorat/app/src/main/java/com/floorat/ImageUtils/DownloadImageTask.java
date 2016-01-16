/*package com.floorat.ImageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.floorat.RoundImage;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  pd.show();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        RoundImage roundedImage = null;
        try {
            System.out.println("In try");
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
         //   roundedImage = new RoundImage(mIcon11);
            System.out.println("Done try");
        } catch (Exception e) {
            System.out.println("In catch");
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        //  pd.dismiss();
        bmImage.setImageBitmap(result);
//        bmImage.setImageDrawable(result);
    }
}
*/
package com.floorat.ImageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.floorat.RoundImage;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, RoundImage> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  pd.show();
    }

    protected RoundImage doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        RoundImage roundedImage = null;
        try {
            System.out.println("In try");
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            roundedImage = new RoundImage(mIcon11);
            System.out.println("Done try");
        } catch (Exception e) {
            System.out.println("In catch");
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return roundedImage;
    }

    @Override
    protected void onPostExecute(RoundImage result) {
        System.out.println("In postExecute");
        super.onPostExecute(result);
        //  pd.dismiss();
        bmImage.setImageDrawable(result);
        System.out.println("Done postExecute");
    }
}
