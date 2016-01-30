package com.floorat.Adapter;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class BuySell extends Fragment {

    ImageView img;
    Bitmap bitmap;
    TextView load;
    ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        Bundle bundle = this.getArguments();
        final String url = bundle.getString("url");

        View v = inflater.inflate(R.layout.buysellview,container,false);


        load= (TextView)v.findViewById(R.id.loading);
        img = (ImageView) v.findViewById(R.id.buysellclass);

        imageLoader=new ImageLoader(getContext());
        imageLoader.DisplayImage(url,img);
        load.setVisibility(View.GONE);



        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeImage_fullscreen.class);
                intent.putExtra("url", url);
                getActivity().startActivity(intent);
            }
        });


        System.out.println("Url Received:" + url);




        return v;
    }


}