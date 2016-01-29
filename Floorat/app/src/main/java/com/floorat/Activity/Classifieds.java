package com.floorat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
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
import com.floorat.Adapter.ClassifiedsAdapter;
import com.floorat.R;
import com.floorat.RequestHandler.CustomRequest;
import com.floorat.SharedPrefrences.UserLocalStore;
import com.floorat.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                intent.putExtra("cat", category);
                startActivity(intent);
            }
        });

        fetchbuysell();

    }

    void fetchbuysell()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Building List...");
        pDialog.show();
        UserLocalStore userLocalStore;
        userLocalStore = new UserLocalStore(this);

        String url = "http://mogwliisjunglee.96.lt/classifiedapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "get_buy_sell_ads");
        params.put("building_name", userLocalStore.getdata());
        params.put("category_name",category);

        System.out.println("Response" + params.toString());

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                showbuysell(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Time Out Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else if (error instanceof AuthFailureError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Authentication Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else if (error instanceof ServerError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Server Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else if (error instanceof NetworkError) {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), "Network Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else if (error instanceof ParseError) {
                    pDialog.hide();
                    Log.d("Response: ", error.toString());
                    Toast.makeText(getApplicationContext(), "Unknown Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }

    void showbuysell(JSONArray response) {
        System.out.println("Response is" + response.toString());

        ArrayList<String> heading = new ArrayList<>();
        ArrayList<String> url   = new ArrayList<>();
        ArrayList<String> specs = new ArrayList<>();
        ArrayList<String> id    = new ArrayList<>();

        if(response.length() != 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject json = response.getJSONObject(i);
                    String head = json.getString("title");
                    String urls = json.getString("image");
                    String spec = json.getString("description");
                    String ids = json.getString("id");

                    heading.add(head);
                    url.add(urls);
                    specs.add(spec);
                    id.add(ids);

                } catch (JSONException e) {
                }
            }


            ListAdapter adpt = new ClassifiedsAdapter(this, heading, url, specs);
            ListView list = (ListView) findViewById(R.id.listView2);
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
        else
            new Util().showerrormessage(Classifieds.this, "Sorry no notice as per now!!");
    }

    @Override
    public void onResume(){
        super.onResume();
        fetchbuysell();
    }

}
