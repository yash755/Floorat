package com.floorat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.floorat.Adapter.ClassifiedsAdapter;
import com.floorat.R;

public class Classifieds extends AppCompatActivity {

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifieds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        category = extras.getString("cat");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Classifieds.this, UploadClassifieds.class);
                intent.putExtra("cat",category);
                startActivity(intent);
            }
        });

        String[] foods = {"Name" , "Name" , "Name" , "Name" , "Name" , "Name" , "Name" , "Name"};
        ListAdapter adpt = new ClassifiedsAdapter(this, foods);
        ListView list = (ListView)findViewById(R.id.listView2);
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
