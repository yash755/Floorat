package com.floorat.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.floorat.Adapter.Profile_groups;
import com.floorat.R;
import com.floorat.RoundImage;

import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    GridView gv;
    Button btn;
    Bitmap bitmap;
    EditText ed;
    TextView textView;
    RoundImage roundedImage;
    ImageView imageView;
    public static String [] prgmNameList={"Group 1","Group 2","Group 3","Group 4","Group 5","Group 6","Group 7","Group 8","Group 9"};
    public static int [] prgmImages={R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gv=(GridView) findViewById(R.id.gridView2);
        gv.setAdapter(new Profile_groups(this, prgmNameList, prgmImages));

        imageView = (ImageView) findViewById(R.id.edit);
        textView = (TextView) findViewById(R.id.textView);
        ed = (EditText)findViewById(R.id.ed);
        ed.setVisibility(View.GONE);
        btn = (Button)findViewById(R.id.btn);
        btn.setVisibility(View.GONE);
    }

    public void editImage(View view)
    {
        imageView = (ImageView) findViewById(R.id.imageView2);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri filePath;

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                roundedImage = new RoundImage(bitmap);
                imageView.setImageDrawable(roundedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void editStatus(View view){
        String status = textView.getText().toString();
        textView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        ed = (EditText) findViewById(R.id.ed);
        ed.setText(status, TextView.BufferType.EDITABLE);
        ed.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
    }

    public void onSave(View view){
        String status = ed.getText().toString();
        ed.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(status);
        textView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }

}