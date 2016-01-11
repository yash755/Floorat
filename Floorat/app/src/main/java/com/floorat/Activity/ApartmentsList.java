package com.floorat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SearchView;
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
import com.floorat.RequestHandler.CustomRequest;
import com.floorat.R;
import com.floorat.SharedPrefrences.UserLocalStore;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ApartmentsList extends AppCompatActivity {

    UserLocalStore userlocalstore;

    GridView gv;
    SearchView sv;

    String[] buildinglist;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userlocalstore = new UserLocalStore(this);

        fetchbuildings();

        gv = (GridView)   findViewById(R.id.gridView);
        sv = (SearchView) findViewById(R.id.searchView);


/*

        Button b2 = (Button) findViewById(R.id.button2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ApartmentsList.this, Home.class);
                startActivity(in);
            }
        });*/
    }
    @Override
    public void onBackPressed() {
    }

    void fetchbuildings()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Building List...");
        pDialog.show();

        String url = "http://mogwliisjunglee.96.lt/buildingapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "Droider");

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


    void insertbuildings(JSONArray response){
        System.out.println("Response is" + response.toString());
        String [] name = new String[response.length()];
        Integer len = response.length();

        for (int i = 0; i <response.length(); i++) {
            try {
                name[i] = response.getString(i);
            }
            catch (JSONException e) {
            }
        }

        System.out.println("Response is" + name[0]);
        buildinglist = name;
        System.out.println("Response is" + buildinglist[0]);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,buildinglist);


        gv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = (String) parent.getItemAtPosition(position);
                        sv.setQuery(value, false);

                    }
                });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                userlocalstore.updatedata(query);
                userlocalstore.setUserloggedIn(true);

                Intent in = new Intent(ApartmentsList.this, Home.class);
                startActivity(in);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    gv.setAdapter(adapter);


                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });

    }




}