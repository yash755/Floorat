package com.floorat.Adapter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.R;

public class NoticeImage_fullscreen extends AppCompatActivity {

    public ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_image_fullscreen);
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        String text = getIntent().getStringExtra("url");
        imageLoader=new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage(text, imageView);
    }
}
