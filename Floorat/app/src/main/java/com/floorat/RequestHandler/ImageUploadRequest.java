package com.floorat.RequestHandler;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ImageUploadRequest {
    ProgressDialog progressDialog;

    public ImageUploadRequest (Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Working....");
        progressDialog.setMessage("Please Wait");


    }


    public void fetchuserdatainbackground(Context context,Bitmap user){
        progressDialog.show();
        new fetchuserdataasynctask(context,user).execute();
    }



    public class fetchuserdataasynctask extends AsyncTask<Void,Void,String> {

        Bitmap user;
        Context context;



        public fetchuserdataasynctask(Context context,Bitmap user){

            this.user = user;
            this.context = context;


        }

        @Override
        protected String doInBackground(Void... voids) {

            Bitmap bitmap = user;

            String attachmentName = "bitmap";
            String attachmentFileName = "bitmap.bmp";
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";


            HttpURLConnection httpUrlConnection = null;
            URL url = null;
            try {
                url = new URL("http://mogwliisjunglee.96.lt/noticeapi.php");
                httpUrlConnection = (HttpURLConnection) url.openConnection();

            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

                httpUrlConnection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);


            DataOutputStream request = null;
            try {
                request = new DataOutputStream(
                        httpUrlConnection.getOutputStream());

                request.writeBytes(twoHyphens + boundary + crlf);

                request.writeBytes("Content-Disposition: form-data; name=\"" +
                        attachmentName + "\";filename=\"" +
                        attachmentFileName + "\"" + crlf);

                request.writeBytes(crlf);
            } catch (IOException e) {
                e.printStackTrace();
            }

//I want to send only 8 bit black & white bitmaps
            byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            for (int i = 0; i < bitmap.getWidth(); ++i) {
                for (int j = 0; j < bitmap.getHeight(); ++j) {
                    //we're interested only in the MSB of the first byte,
                    //since the other 3 bytes are identical for B&W images
                    pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
                }
            }

            try {
                request.write(pixels);
                request.writeBytes(crlf);
                request.writeBytes(twoHyphens + boundary +
                        twoHyphens + crlf);
                request.flush();
                request.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            InputStream responseStream = null;
            try {

                responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                String line = "";
                StringBuilder stringBuilder = new StringBuilder();


                while ((line = responseStreamReader.readLine()) != null)
                    stringBuilder.append(line).append("\n");

                responseStreamReader.close();


                String response = stringBuilder.toString();

                responseStream.close();
                httpUrlConnection.disconnect();

                return response;
            }
            catch (IOException e) {
                e.printStackTrace();
            }


            return "error";


        }



        @Override
        protected void onPostExecute(String response) {


            progressDialog.dismiss();
            //userCallBack.done(returneduser);


            super.onPostExecute(null);
        }
    }



}
