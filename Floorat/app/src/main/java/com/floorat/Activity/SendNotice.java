package com.floorat.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.floorat.ImageUtils.ImageFilePath;
import com.floorat.R;
import com.floorat.RequestHandler.SendNoticeRequest;
import com.floorat.SharedPrefrences.UserLocalStore;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class SendNotice extends AppCompatActivity  implements View.OnClickListener {

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    EditText heading;

    private ImageView imageView;

    private Bitmap bitmap;
    String selectedImagePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        heading      = (EditText)findViewById(R.id.heading);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        imageView = (ImageView) findViewById(R.id.imageView);


        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    private void showFileChoosen() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


            Uri selectedImageUri = data.getData();

            //MEDIA GALLERY
            selectedImagePath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
            System.out.println("File Path " + selectedImagePath);


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChoosen();
        }
        if(v == buttonUpload){

            if(validate()) {
                UserLocalStore userLocalStore;
                userLocalStore = new UserLocalStore(this);

                String aptname = userLocalStore.getdata();

                sendnotice(selectedImagePath,aptname,heading.getText().toString());
            }
            else
                Toast.makeText(SendNotice.this,"Either no heading or Image", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this,NoticeBoard.class);
        startActivityForResult(myIntent, 0);
        return true;
    }




    private void sendnotice(String path,String aptname,String head) {

        SendNoticeRequest sendNoticeRequest = new SendNoticeRequest(this);
       sendNoticeRequest.fetchuserdatainbackground(getApplicationContext(), path, aptname, head);

    }

    private boolean validate() {
        return !heading.getText().toString().trim().equals("") && selectedImagePath != null;
    }

    public void uploadFile(String sourceFileUri,String action) {
        HttpURLConnection conn ;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File oldFile = new File(sourceFileUri);

        String fileExt = "jpg";
        String imageid = UUID.randomUUID().toString().replaceAll("-", "");

        File sourceFile = new File(oldFile.getParent(), imageid + "."+fileExt);
        oldFile.renameTo(sourceFile);

        String fileName = sourceFile.getName();

        System.out.println("Filename" + fileName + "-----" + sourceFileUri);

        try {

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(
                    sourceFile);
            URL url = new URL("http://mogwliisjunglee.96.lt/noticeapi.php");

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);
            conn.setRequestProperty("action", action);

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
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

            // Responses from the server (code and message)
            int serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer responsebuffer = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responsebuffer.append(inputLine);
            }

            // print result
            String resStr = responsebuffer.toString();

            in.close();

            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getFileType(String url)
    {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }
}