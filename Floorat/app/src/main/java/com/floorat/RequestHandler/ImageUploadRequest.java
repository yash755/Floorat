package com.floorat.RequestHandler;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.floorat.Activity.SendNotice;
import com.floorat.Utils.Util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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


    public void fetchuserdatainbackground(Context context,String user){
        progressDialog.show();
        new fetchuserdataasynctask(context,user).execute();
    }



    public class fetchuserdataasynctask extends AsyncTask<Void,Void,String> {

        String user;
        Context context;



        public fetchuserdataasynctask(Context context,String user){

            this.user = user;
            this.context = context;


        }

        @Override
        protected String doInBackground(Void... voids) {

            String fileName = user;
            int serverResponseCode = 0;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(user);

            if (!sourceFile.isFile()) {

                progressDialog.dismiss();
                String msg = "Source File not exist";
                new Util().showerrormessage(context, msg);
                return "0";

            } else {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL("http://mogwliisjunglee.96.lt/uploadimage.php");

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(60000);
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                    //Get Response
                    InputStream is = conn.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();


                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);

                  /*  if (serverResponseCode == 200) {

                        progressDialog.dismiss();

                       return  "File Upload Completed";

                        // return response.toString();

                    }*/

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                    return response.toString();

                } catch (MalformedURLException ex) {

                    progressDialog.dismiss();
                    ex.printStackTrace();
                    return  "MalformedURLException Exception";


                } catch (Exception e) {

                    progressDialog.dismiss();
                    e.printStackTrace();
                    return "Time Out try later !!!";

                }




            } // End else block
        }





        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(null);
            progressDialog.dismiss();
            String aresponse = response.substring(1,response.length()-2);

            System.out.println("Resonse which is:" + aresponse);

            Toast.makeText(context,aresponse, Toast.LENGTH_SHORT).show();
        }
    }



}
