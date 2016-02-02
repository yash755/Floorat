package com.floorat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.R;
import com.floorat.RoundImage;

import java.io.IOException;
import java.util.ArrayList;

public class CommentsAdapter extends ArrayAdapter<String> {

    ArrayList<String> ques_list = new ArrayList<>();
    ArrayList<String> ans_list = new ArrayList<>();
    ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> pic_list = new ArrayList<>();
    ArrayList<String> head_pic_list = new ArrayList<>();
    ArrayList<String> head_name_list = new ArrayList<>();
    ImageLoader imageLoader;
    EditText editText;

    public CommentsAdapter(Context context, ArrayList<String> ques, ArrayList<String> ans, ArrayList<String> name, ArrayList<String> pic, ArrayList<String> head_pic, ArrayList<String> head_name) {
        super(context, R.layout.classifieds_rowview, ques);
        ques_list = ques;
        ans_list = ans;
        name_list = name;
        pic_list = pic;
        head_pic_list = head_pic;
        head_name_list = head_name;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View customView = myinflater.inflate(R.layout.comment_rowview, parent, false);


        TextView ques_name = (TextView) customView.findViewById(R.id.ques_name);
        TextView ques_text = (TextView) customView.findViewById(R.id.ques_text);
        TextView ans_name = (TextView) customView.findViewById(R.id.ans_name);
        TextView ans_text = (TextView) customView.findViewById(R.id.ans_text);
        ImageView ques_img = (ImageView) customView.findViewById(R.id.ques_image);
        ImageView ans_img = (ImageView) customView.findViewById(R.id.ans_image);
        editText = (EditText) customView.findViewById(R.id.ans_type);


        ques_name.setText(name_list.get(position));
        ques_text.setText(ques_list.get(position));
        ans_name.setText(head_name_list.get(position));

        if (ques_list.get(position).equals(ans_list.get(position))) {
            ans_text.setVisibility(View.GONE);
            editText.setEnabled(true);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setFocusable(true);
        } else {
            editText.setVisibility(View.GONE);
            ans_text.setText(ans_list.get(position));
        }
        System.out.println("url " + pic_list.get(position));
        imageLoader.DisplayImage(pic_list.get(position), ques_img);
        imageLoader.DisplayImage(head_pic_list.get(position), ans_img);
        return customView;
    }
}