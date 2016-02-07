package com.floorat.Adapter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.R;

public class BuySell extends Fragment {

    ImageView img;
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