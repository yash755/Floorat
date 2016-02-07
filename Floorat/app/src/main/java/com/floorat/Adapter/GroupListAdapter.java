package com.floorat.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.floorat.ImageUtils.RoundImageLoader;
import com.floorat.R;

import java.util.ArrayList;

public class GroupListAdapter extends ArrayAdapter<String> {

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> url  = new ArrayList<>();
    RoundImageLoader roundImageLoader;

    GroupListAdapter(Context context, ArrayList<String> name, ArrayList<String> url)
    {
        super(context, R.layout.grouplist, name);
        this.name = name;
        this.url  = url;

        roundImageLoader = new RoundImageLoader(context);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.grouplist, parent, false);

        TextView gpname   = (TextView)customView.findViewById(R.id.gpname);

        ImageView image=(ImageView)customView.findViewById(R.id.gpimage);
        roundImageLoader.DisplayImage(url.get(position), image);

        gpname.setText(name.get(position));



//        imageLoader.DisplayImage(urls.get(position),url);
        return customView;
    }

}