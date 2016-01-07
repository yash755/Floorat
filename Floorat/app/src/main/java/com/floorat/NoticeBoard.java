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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeBoard extends AppCompatActivity {

    private ArrayList<Product> mProductArrayList = new ArrayList<Product>();
    private Noticeboardlistadapter adapter1;

   private List<String> list = new ArrayList<String>();




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
                sendnotifications();
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


    void sendnotifications()
    {
        String url = "http://mogwliisjunglee.96.lt/noticeapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "Droider");
        params.put("building_name","Amrapali Sapphire");

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                Log.d("Response: ", response.toString());


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }

}

