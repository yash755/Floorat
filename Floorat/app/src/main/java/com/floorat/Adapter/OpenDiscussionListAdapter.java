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

public class OpenDiscussionListAdapter extends ArrayAdapter<String> {

    ArrayList<String> uploadingdate = new ArrayList<>();
    ArrayList<String> id            = new ArrayList<>();
    ArrayList<String> user          = new ArrayList<>();
    ArrayList<String> pic           = new ArrayList<>();
    ArrayList<String> text          = new ArrayList<>();
    ArrayList<String> count         = new ArrayList<>();
    RoundImageLoader roundImageLoader;

    public OpenDiscussionListAdapter(Context context, ArrayList<String> uploadingdate, ArrayList<String> id, ArrayList<String> user,
                                     ArrayList<String> pic, ArrayList<String> text, ArrayList<String> count)
    {
        super(context, R.layout.opendiscussin_listview,uploadingdate);
        this.uploadingdate = uploadingdate;
        this.id  = id;
        this.user=user;
        this.pic=pic;
        this.text=text;
        this.count=count;

        roundImageLoader = new RoundImageLoader(context);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.opendiscussin_listview, parent, false);

        TextView comment  = (TextView)customView.findViewById(R.id.comments);
        TextView heading  = (TextView)customView.findViewById(R.id.headingop);
        TextView upload   = (TextView)customView.findViewById(R.id.uploaddate);
        ImageView image=(ImageView)customView.findViewById(R.id.pic);

        roundImageLoader.DisplayImage(pic.get(position),image);
        comment.setText(count.get(position));
        upload.setText("17 Days Ago");
        heading.setText(text.get(position));



//        imageLoader.DisplayImage(urls.get(position),url);
        return customView;
    }

}