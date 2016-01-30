package com.floorat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.floorat.ImageUtils.ImageFilePath;
import com.floorat.R;
import com.floorat.RequestHandler.SendAd;
import com.floorat.SharedPrefrences.UserLocalStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadClassifieds extends AppCompatActivity implements View.OnClickListener {

    String category;
    EditText title, description, contact, price;
    ImageView i1, i2, i3;
    Button buttonChoose1, buttonChoose2, buttonChoose3,upload;
    Spinner sp1;

    private int PICK_IMAGE_REQUEST =1;
    private int PICK_IMAGE_REQUEST2=2;
    private int PICK_IMAGE_REQUEST3=3;

    private int REQUEST_CAMERA      =4;
    private int REQUEST_CAMERA2     =5;
    private int REQUEST_CAMERA3     =6;

    Bitmap bitmap;


    Uri picUri = null;
    Uri picUri1= null ;
    Uri picUri2= null;

    String[] selectedImagePath = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_classifieds);

        Bundle extras = getIntent().getExtras();
        category = extras.getString("cat");

        selectedImagePath[0] = null;
        selectedImagePath[1] = null;
        selectedImagePath[2] = null;

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        contact = (EditText) findViewById(R.id.contact);
        price = (EditText) findViewById(R.id.price);

        i1 = (ImageView) findViewById(R.id.i1);
        i2 = (ImageView) findViewById(R.id.i2);
        i3 = (ImageView) findViewById(R.id.i3);

        sp1 =(Spinner) findViewById(R.id.spin1);

        buttonChoose1 = (Button) findViewById(R.id.buttonChoose1);
        buttonChoose2 = (Button) findViewById(R.id.buttonChoose2);
        buttonChoose3 = (Button) findViewById(R.id.buttonChoose3);

        upload = (Button)findViewById(R.id.upload_buy);


        buttonChoose1.setOnClickListener(this);
        buttonChoose2.setOnClickListener(this);
        buttonChoose3.setOnClickListener(this);

        buttonChoose2.setVisibility(View.GONE);
        buttonChoose3.setVisibility(View.GONE);

        upload.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (v == buttonChoose1) {
            final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadClassifieds.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        File file = getOutputMediaFile(1);
                        picUri = Uri.fromFile(file); // create
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file

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

            buttonChoose1.setVisibility(View.GONE);
            buttonChoose2.setVisibility(View.VISIBLE);
            buttonChoose3.setVisibility(View.GONE);

        }
        else if (v == upload){

            if(validate()) {
                UserLocalStore userLocalStore;
                userLocalStore = new UserLocalStore(this);

                String aptname = userLocalStore.getdata();

                if(selectedImagePath[0] != null)
                    sendad(selectedImagePath[0],aptname,title.getText().toString(),description.getText().toString(),
                            contact.getText().toString(),price.getText().toString(),
                            sp1.getSelectedItem().toString());
                if(selectedImagePath[1] != null)
                    sendad(selectedImagePath[1],aptname,title.getText().toString(),description.getText().toString(),
                            contact.getText().toString(),price.getText().toString(),
                            sp1.getSelectedItem().toString());
                if(selectedImagePath[2] != null)
                    sendad(selectedImagePath[2],aptname,title.getText().toString(),description.getText().toString(),
                            contact.getText().toString(),price.getText().toString(),
                            sp1.getSelectedItem().toString());

            }
            else
                Toast.makeText(UploadClassifieds.this,"Either no heading or Image", Toast.LENGTH_SHORT).show();

        }


    }

    private void sendad(String selectedImagePath,String aptname,String title,String description,String contact,String price,String sp1)
    {

        SendAd sendAd = new SendAd(this);
        sendAd.fetchuserdatainbackground(getApplicationContext(),selectedImagePath,aptname,title,description,contact,price,sp1,category);

    }

    private boolean validate() {
        return !title.getText().toString().trim().equals("") && !description.getText().toString().trim().equals("")
                && !contact.getText().toString().trim().equals("") && !price.getText().toString().trim().equals("")
                && !sp1.getSelectedItem().toString().trim().equals("")
                && (selectedImagePath[0] != null || selectedImagePath[1] !=null || selectedImagePath[2] != null);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = picUri;

            selectedImagePath[0] = ImageFilePath.getPath(getApplicationContext(), uri);
            System.out.println("File Path " + selectedImagePath);


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                i1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


            Uri selectedImageUri = data.getData();

            //MEDIA GALLERY
            selectedImagePath[0] = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
            System.out.println("File Path " + selectedImagePath);


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                i1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}