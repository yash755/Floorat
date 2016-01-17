package com.floorat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.Objects.Noticelist;
import com.floorat.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BuySelllistadapter extends ArrayAdapter<String> {

    LayoutInflater inflater;
    public Context ctx;
    String[] heading;
    int[] img;
    public BuySelllistadapter(Context context, String[] list1, int[] list2) {
        super(context, R.layout.buysell_rowview, list1);
        ctx = context;
        heading = list1;
        img = list2;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.buysell_rowview, parent, false);

        TextView textView = (TextView) customView.findViewById(R.id.heading_buysell);
        textView.setText(heading[position]);

        ImageView imageView = (ImageView) customView.findViewById(R.id.image_buysell);
        imageView.setImageResource(img[position]);
        return customView;
    }
}