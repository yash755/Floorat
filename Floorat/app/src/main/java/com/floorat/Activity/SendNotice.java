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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.floorat.ImageUtils.ImageFilePath;
import com.floorat.R;
import com.floorat.RequestHandler.SendNoticeRequest;
import com.floorat.SharedPrefrences.UserLocalStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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


}








