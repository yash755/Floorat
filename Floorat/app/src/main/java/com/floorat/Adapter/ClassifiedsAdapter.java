package com.floorat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.R;

import java.util.ArrayList;

public class ClassifiedsAdapter extends ArrayAdapter<String> {

    ArrayList<String> head = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();
    ArrayList<String> spes = new ArrayList<>();

    ImageLoader imageLoader;
    Activity    activity;

    public ClassifiedsAdapter(Context context, ArrayList<String> heading, ArrayList<String> url, ArrayList<String> specs)
    {
        super(context, R.layout.classifieds_rowview,heading);
        head = heading;
        urls = url;
        spes = specs;

        imageLoader=new ImageLoader(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.classifieds_rowview, parent, false);

        TextView hd   = (TextView)customView.findViewById(R.id.heading_classfied);
        TextView sp   = (TextView)customView.findViewById(R.id.textView3);
        ImageView url = (ImageView)customView.findViewById(R.id.image_classfied);

        hd.setText(head.get(position));
        sp.setText(spes.get(position));


        imageLoader.DisplayImage(urls.get(position),url);




        return customView;
    }


}

