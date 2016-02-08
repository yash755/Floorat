package com.floorat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.floorat.Adapter.CommentsAdapter;
import com.floorat.R;
import com.floorat.RequestHandler.CustomRequest;
import com.floorat.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassifiedComment extends AppCompatActivity {

    String ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classified_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        ids = extras.getString("id");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassifiedComment.this, ClassifiedAskQuestion.class);
                intent.putExtra("bs_id", ids);
                startActivity(intent);
            }
        });



        fetchcomments();
    }

    void fetchcomments()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Comments...");
        pDialog.show();

        String url = "http://mogwliisjunglee.96.lt/classifiedapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "get_buy_sell_ads_comments");
        params.put("bs_id", ids);

        System.out.println("Response" + params.toString());

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                showcomments(response);
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

    void showcomments(JSONArray response) {
        System.out.println("Response is" + response.toString());

        final ArrayList<String> ques_list = new ArrayList<>();
        final ArrayList<String> ans_list     = new ArrayList<>();
        final ArrayList<String> name_list   = new ArrayList<>();
        final ArrayList<String> pic_list    = new ArrayList<>();
        final ArrayList<String> head_pic_list   = new ArrayList<>();
        final ArrayList<String> head_name_list    = new ArrayList<>();
        final ArrayList<String> comment_id_list    = new ArrayList<>();

        if (response.length() != 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject json = response.getJSONObject(i);
                    String ques = json.getString("ques");
                    String ans = json.getString("ans");
                    String name = json.getString("name");
                    String pic  = json.getString("pic");
                    String head_pic = json.getString("head_pic");
                    String head_name  = json.getString("head_name");
                    String comment_id  = json.getString("id");

                    ques_list.add(ques);
                    ans_list.add(ans);
                    name_list.add(name);
                    pic_list.add(pic);
                    head_pic_list.add(head_pic);
                    head_name_list.add(head_name);
                    comment_id_list.add(comment_id);

                } catch (JSONException e) {
                }
            }


            ListAdapter adpt = new CommentsAdapter(this, ids, comment_id_list, ques_list, ans_list, name_list, pic_list, head_pic_list, head_name_list);
            ListView list = (ListView) findViewById(R.id.listView3);
            list.setAdapter(adpt);
     /*       list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      }
                    }
            );*/
        }
        else
            new Util().showerrormessage(ClassifiedComment.this, "Sorry no comments as per now!!");
    }
}