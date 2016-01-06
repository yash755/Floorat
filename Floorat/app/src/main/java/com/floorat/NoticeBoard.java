package com.floorat;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NoticeBoard extends AppCompatActivity {

    android.widget.SearchView sv;
    ListAdapter adpt;


    private String imageUrls[] = {
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3",
            "https://avatars.githubusercontent.com/u/14106541?v=3"



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

       // sv = (android.widget.SearchView) findViewById(R.id.searchView);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        adpt = new Noticeboardlistadapter(this,imageUrls);
        ListView li = (ListView) findViewById(R.id.listView1);
        li.setAdapter(adpt);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
           //     Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
           //     Intent in = new Intent(Home.this, SearchResult.class);
          //      startActivity(in);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

            //    adpt.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

}
