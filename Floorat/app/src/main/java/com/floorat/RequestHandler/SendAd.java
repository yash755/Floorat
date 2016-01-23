package com.floorat.RequestHandler;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.floorat.Utils.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SendAd {

    ProgressDialog progressDialog;

    public SendAd (Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Working....");
        progressDialog.setMessage("Please Wait");


    }


    public void fetchuserdatainbackground(Context context,String selectedImagePath,String aptname,String title,
                                          String description,String contact,String price,String sp1,String category){
        progressDialog.show();
        new fetchuserdataasynctask(context,selectedImagePath,aptname,title,description,contact,price,sp1,category).execute();
    }



    public class fetchuserdataasynctask extends AsyncTask<Void,Void,String> {

        String selectedimagepath;
        String aptname;
        String title;
        String description;
        String contact;
        String price;
        String sp1;
        String category;
        Context context;



        public fetchuserdataasynctask(Context context,String selectedImagePath,String aptname,String title,
                                      String description,String contact,String price,String sp1,String category){

            this.selectedimagepath = selectedImagePath;
            this.aptname = aptname;
            this.title = title;
            this.description=description;
            this.contact = contact;
            this.price=price;
            this.sp1=sp1;
            this.category = category;
            this.context = context;


        }

        @Override
        protected String doInBackground(Void... voids) {

            String fileName = selectedimagepath;
            int serverResponseCode = 0;
            System.out.println("Aresponse" + selectedimagepath);

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(selectedimagepath);

            if (!sourceFile.isFile()) {

                progressDialog.dismiss();
                String msg = "Source File not exist";
                new Util().showerrormessage(context, msg);
                return "0";

            } else {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL("http://mogwliisjunglee.96.lt/uploadclassified.php");

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


                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"title\""
                            + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(title);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"category\""
                            + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(category);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"description\""
                            + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(description);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"contact\""
                            + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(contact);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"price\""
                            + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(price);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"sp1\""
                            + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(sp1);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"aptname\""
                            + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(aptname);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

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
                    return  "[MalformedURLException Exception]";


                } catch (Exception e) {

                    progressDialog.dismiss();
                    e.printStackTrace();
                    return "[Time Out try later !!!]";

                }




            } // End else block
        }





        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(null);
            String aresponse = response.substring(2,response.length()-3);

            System.out.println("Aresponse" + response + "/nrsese" + aresponse);

            progressDialog.dismiss();
            Toast.makeText(context, aresponse, Toast.LENGTH_SHORT).show();




        }
    }



}