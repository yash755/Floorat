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

public class OpenDiscussionCommentAdapter extends ArrayAdapter<String> {

    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> imageis= new ArrayList<>();

    RoundImageLoader roundImageLoader;

    public OpenDiscussionCommentAdapter(Context context, ArrayList<String> text, ArrayList<String> imageis)
    {
        super(context, R.layout.opencomment_listview,text);
        this.text = text;
        this.imageis= imageis;


        roundImageLoader = new RoundImageLoader(context);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.opencomment_listview, parent, false);

        TextView comment  = (TextView)customView.findViewById(R.id.comment);
        ImageView image=(ImageView)customView.findViewById(R.id.userpicis);

        roundImageLoader.DisplayImage(imageis.get(position),image);
        comment.setText(text.get(position));



//        imageLoader.DisplayImage(urls.get(position),url);
        return customView;
    }

}