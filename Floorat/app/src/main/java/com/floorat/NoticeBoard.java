package com.floorat;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoticeBoard extends AppCompatActivity {

    private ArrayList<Product> mProductArrayList = new ArrayList<Product>();
    private Noticeboardlistadapter adapter1;
    ListView lvProducts;

   private List<String> list = new ArrayList<String>();
    List<String> mOriginalValues;
    Noticeboardlistadapter adpt;



    private String imageUrls[] = {
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

        list.add("Linux");
        list.add("Windows7");
        list.add("Suse");
        list.add("Eclipse");
        list.add("Ubuntu");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mProductArrayList.add(new Product("https://avatars.githubusercontent.com/u/14106541?v=3", 100));
        mProductArrayList.add(new Product("https://avatars.githubusercontent.com/u/14106541?v=3", 200));
        mProductArrayList.add(new Product("https://avatars.githubusercontent.com/u/14106541?v=3", 300));



        ListView li = (ListView) findViewById(R.id.lvProducts);
        adapter1 = new Noticeboardlistadapter(NoticeBoard.this, mProductArrayList);
        li.setAdapter(adapter1);


      /*  adpt = new Noticeboardlistadapter(this,imageUrls,list);

        li.setAdapter(adpt);
        li.setTextFilterEnabled(true);*/

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


                adapter1.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

}

