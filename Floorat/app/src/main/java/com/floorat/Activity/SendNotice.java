package com.floorat.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import com.floorat.Utils.Util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.floorat.RequestHandler.GetUserCallBack;;

public class SendNotice extends AppCompatActivity  implements View.OnClickListener {

    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_CAMERA     = 2;

    private Button buttonChoose;
    private Button buttonUpload;
    EditText heading;

    private ImageView imageView;

    private Bitmap bitmap;
    String selectedImagePath;

    Uri picUri;



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


        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {

            Uri uri=picUri;

            selectedImagePath = ImageFilePath.getPath(getApplicationContext(), uri);
            System.out.println("File Path " + selectedImagePath);


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


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
            selectImage();
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

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SendNotice.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File file=getOutputMediaFile(1);
                    picUri = Uri.fromFile(file); // create
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,picUri); // set the image file

                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            PICK_IMAGE_REQUEST);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /** Create a File for saving an image */
    private  File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyApplication");

        /**Create the storage directory if it does not exist*/
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpeg");
        } else {
            return null;
        }

        return mediaFile;
    }







}