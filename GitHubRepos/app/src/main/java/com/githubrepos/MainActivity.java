package com.githubrepos;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sendnotifications();


    }

    void sendnotifications()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        String url = "https://api.github.com/users/deathping1994/repos";

        System.out.println("I am two");

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("username", "ram");

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            // JSONArray jsonArray = null;
                            try {

                                //  jsonArray = response.getJSONArray("success");
                                //   JSONObject jsonobj1 = new JSONObject(response);
                                response = response.getJSONObject("");
                                //  String site = response.getString("fname");
                                //         network = response.getString("network");
                                System.out.println("I am response" + response.toString());
                                pDialog.hide();
                            } catch (JSONException e) {
                                System.out.println("I am catch" + response.toString());
                                pDialog.hide();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_SHORT).show();
                    }
                }
            })

            {


                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;

                }


            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(postRequest);





    }


}
