package com.floorat.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floorat.R;

public class ClassifiedsAdapter extends ArrayAdapter<String> {

    public ClassifiedsAdapter(Context context, String[] foods) {
        super(context, R.layout.classifieds_rowview, foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View customView = myinflater.inflate(R.layout.classifieds_rowview, parent, false);
        String[] medtext = {"Specs" , "Specs" , "Specs" , "Specs" , "Specs" , "Specs" , "Specs" , "Specs"};
        int[] imgs = {R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic };
        int[] back = {Color.parseColor("#00A2A6") , Color.parseColor("#FA005B") , Color.parseColor("#8FCC3A") , Color.parseColor("#FFB000") , Color.parseColor("#6F194F") , Color.parseColor("#00A2A6") , Color.parseColor("#FA005B") , Color.parseColor("#8FCC3A")};



        String singleFoodItem = getItem(position);
        String singleItem = medtext[position];
        int singleImage = imgs[position];
        int bck = back[position];


        TextView lt = (TextView)customView.findViewById(R.id.textView2);
        TextView mt = (TextView)customView.findViewById(R.id.textView3);
        ImageView img = (ImageView)customView.findViewById(R.id.imageView3);
        LinearLayout rs = (LinearLayout) customView.findViewById(R.id.rightside);

        int c = Color.parseColor("#00A2A6");
        lt.setText(singleFoodItem);
        mt.setText(singleItem);
        img.setImageResource(singleImage);
        rs.setBackgroundColor(bck);
        return customView;
    }
}

