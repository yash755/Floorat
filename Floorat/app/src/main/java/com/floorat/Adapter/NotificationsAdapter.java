package com.floorat.Adapter;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.R;

import java.util.ArrayList;

public class NotificationsAdapter extends ArrayAdapter<String> {

    ArrayList<String> initiator_name_list = new ArrayList<>();
    ArrayList<String> item_name_list = new ArrayList<>();
    ArrayList<String> added_date_list = new ArrayList<>();
    ArrayList<String> notification_type_list = new ArrayList<>();
    ArrayList<String> item_id_list = new ArrayList<>();
    EditText editText;

    public NotificationsAdapter(Context context, ArrayList<String> ques, ArrayList<String> ans, ArrayList<String> name, ArrayList<String> pic, ArrayList<String> head_pic) {
        super(context, R.layout.notifications_rowview, ques);
        initiator_name_list = ques;
        item_name_list = ans;
        added_date_list = name;
        notification_type_list = pic;
        item_id_list = head_pic;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View customView = myinflater.inflate(R.layout.notifications_rowview, parent, false);

        TextView textView4 = (TextView) customView.findViewById(R.id.textView4);
        TextView textView5 = (TextView) customView.findViewById(R.id.textView5);
        TextView textView6 = (TextView) customView.findViewById(R.id.textView6);

        textView4.setText(item_name_list.get(position));
        textView5.setText(initiator_name_list.get(position));
        textView6.setText(added_date_list.get(position));

        return customView;
    }
}