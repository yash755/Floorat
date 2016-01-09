package com.floorat;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeBoard extends AppCompatActivity {

    private ArrayList<Noticelist> nlist = new ArrayList<>();
    private Noticeboardlistadapter adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(NoticeBoard.this, SendNotice.class));
            }
        });

        fetchbuildings();





    }

    void fetchbuildings()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Building List...");
        pDialog.show();

        String url = "http://192.168.1.102/social/noticeapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "get_url");

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                insertbuildings(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Time Out Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Authentication Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Server Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Network Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    pDialog.hide();
                    Log.d("Response: ", error.toString());
                    Toast.makeText(getApplicationContext(), "Unknown Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }


    void insertbuildings(JSONArray response) {
        System.out.println("Response is" + response.toString());
        String name;
        Integer len = response.length();

        for (int i = 0; i < response.length(); i++) {
            try {
                name = response.getString(i);
                nlist.add(new Noticelist("Music",name));

            } catch (JSONException e) {
            }
        }
        ListView li = (ListView) findViewById(R.id.notices);
        adapter1 = new Noticeboardlistadapter(NoticeBoard.this,nlist);
        li.setAdapter(adapter1);

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

