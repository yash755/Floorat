package com.floorat.Adapter;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.floorat.Activity.OpenDiscussion;
import com.floorat.R;

import java.util.ArrayList;


public class Groups extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.group,container,false);
        System.out.println("gplist");


        final ArrayList<String> name = new ArrayList<>();
        name.add("Open Discussion");

        ArrayList<String> url = new ArrayList<>();
        url.add("http://mogwliisjunglee.96.lt/uploads/IMG-20160206-WA0000.jpg");

        ListAdapter adpt = new GroupListAdapter(getActivity(),name,url);
        final ListView lv = (ListView)v.findViewById(R.id.gplist);
        lv.setAdapter(adpt);

       final AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                if(name.get(position).equals("Open Discussion"))
                    startActivity(new Intent(getActivity(),OpenDiscussion.class));
            }
        };

        lv.setOnItemClickListener(listener);



        return v;
    }


}

