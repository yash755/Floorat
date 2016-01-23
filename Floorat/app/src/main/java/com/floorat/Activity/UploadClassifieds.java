package com.floorat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.floorat.ImageUtils.ImageFilePath;
import com.floorat.R;
import com.floorat.RequestHandler.SendAd;
import com.floorat.RequestHandler.SendNoticeRequest;
import com.floorat.SharedPrefrences.UserLocalStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private int REQUEST_CAMERA     =4;
    private int REQUEST_CAMERA2     =5;
    private int REQUEST_CAMERA3     =6;

    Bitmap bitmap;

    Uri picUri;

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

        upload.setOnClickListener(this);


    }


    private void showFileChoosen(int req_code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),req_code);

    }

    private void selectpicture(int req_code){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = getOutputMediaFile(1);
        picUri = Uri.fromFile(file); // create
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file

        startActivityForResult(intent, req_code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST)
            {
                Uri selectedImageUri = data.getData();
                selectedImagePath[0] = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    i1.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {

                Uri uri=picUri;

                selectedImagePath[0] = ImageFilePath.getPath(getApplicationContext(), uri);
                System.out.println("File Path " + selectedImagePath);


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    i1.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            if (requestCode == PICK_IMAGE_REQUEST2)
            {
                Uri selectedImageUri = data.getData();
                selectedImagePath[1] = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    i2.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == REQUEST_CAMERA2 && resultCode == RESULT_OK) {

                Uri uri=picUri;

                selectedImagePath[1] = ImageFilePath.getPath(getApplicationContext(), uri);
                System.out.println("File Path " + selectedImagePath);


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    i2.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            if (requestCode == PICK_IMAGE_REQUEST3)
            {
                Uri selectedImageUri = data.getData();
                selectedImagePath[2] = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    i3.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (requestCode == REQUEST_CAMERA3 && resultCode == RESULT_OK) {

                Uri uri=picUri;

                selectedImagePath[2] = ImageFilePath.getPath(getApplicationContext(), uri);
                System.out.println("File Path " + selectedImagePath);


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    i3.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            System.out.println("Selected File paths : " + selectedImagePath[0] + selectedImagePath[1] + selectedImagePath[2]);
        }
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
                        selectpicture(REQUEST_CAMERA);
                    } else if (items[item].equals("Choose from Library")) {
                        showFileChoosen(PICK_IMAGE_REQUEST);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        } else if (v == buttonChoose2) {
            final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadClassifieds.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {
                        selectpicture(REQUEST_CAMERA2);
                    } else if (items[item].equals("Choose from Library")) {
                        showFileChoosen(PICK_IMAGE_REQUEST2);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        } else if (v == buttonChoose3) {
            final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadClassifieds.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {
                        selectpicture(REQUEST_CAMERA3);
                    } else if (items[item].equals("Choose from Library")) {
                        showFileChoosen(PICK_IMAGE_REQUEST3);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

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
