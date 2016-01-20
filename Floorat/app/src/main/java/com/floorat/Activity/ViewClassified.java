package com.floorat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.floorat.Adapter.ClassifiedsAdapter;
import com.floorat.Adapter.CommentsAdapter;
import com.floorat.R;
import com.floorat.UploadClassifieds;

public class ViewClassified extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classified);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        String[] foods = {"Name" , "Name" , "Name" , "Name" , "Name" , "Name" , "Name" , "Name"};
        ListAdapter adpt = new CommentsAdapter(this, foods);
        ListView list = (ListView)findViewById(R.id.listView3);
        list.setAdapter(adpt);


        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name = String.valueOf(parent.getItemAtPosition(position));
                        Intent intent = new Intent(getBaseContext(), ViewClassified.class);
                        intent.putExtra("ans", name);
                        startActivity(intent);
                    }
                }
        );


    }

}




