package com.floorat.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
import com.floorat.Adapter.CommentsAdapter;
import com.floorat.ImageUtils.ImageLoader;
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

public class ClassifiedAskQuestion extends AppCompatActivity {

    ImageLoader imageLoader;
    UserLocalStore userlocalstore;
    EditText editText;
    String ids;
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classified_ask_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        ids = extras.getString("bs_id");

        imageLoader = new ImageLoader(ClassifiedAskQuestion.this);

        ImageView imageView = (ImageView) findViewById(R.id.imageView4);
        editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button3);

        userlocalstore = new UserLocalStore(this);
        imageLoader.DisplayImage(userlocalstore.geturl(), imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertComment();

            }
        });
    }


    void insertComment() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving Data...");
        pDialog.show();

        String apt = userlocalstore.getdata();
        String name = userlocalstore.getname();
        String comment = editText.getText().toString();

        String url = "http://mogwliisjunglee.96.lt/classifiedapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "insert_buy_sell_ques");
        params.put("bs_id", ids);
        params.put("user_name", name);
        params.put("comment", comment);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                fetchresult(response);
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

    void fetchresult(JSONArray response) {

        System.out.println("Response is this check " + response.toString());

        if (response.length() != 0) {
            for (int i = 0; i < response.length(); i++) {
                try {

                    JSONObject json = response.getJSONObject(i);
                    pid = json.getString("parent_id");
                }
                catch (JSONException e) {
                }
            }
        }
    }
}