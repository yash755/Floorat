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

public class CommentsAdapter extends ArrayAdapter<String> {

    public CommentsAdapter(Context context, String[] foods) {
        super(context, R.layout.classifieds_rowview, foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View customView = myinflater.inflate(R.layout.comment_rowview, parent, false);
        String[] medtext = {"Specs" , "Specs" , "Specs" , "Specs" , "Specs" , "Specs" , "Specs" , "Specs"};
        int[] imgs = {R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic , R.drawable.nic };



        String singleFoodItem = getItem(position);
        String singleItem = medtext[position];
        int singleImage = imgs[position];


        TextView ques_name = (TextView)customView.findViewById(R.id.ques_name);
        TextView ques_text = (TextView)customView.findViewById(R.id.ques_text);
        TextView ans_name = (TextView)customView.findViewById(R.id.ans_name);
        TextView ans_text = (TextView)customView.findViewById(R.id.ans_text);
     //   ImageView img = (ImageView)customView.findViewById(R.id.imageView3);

        ques_name.setText("Name");
        ques_text.setText("Question");
        ans_name.setText("Name");
        ans_text.setText("Answer");
 //       img.setImageResource(singleImage);
        return customView;
    }
}