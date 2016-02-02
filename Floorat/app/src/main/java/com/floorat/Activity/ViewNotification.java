package com.floorat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.floorat.Adapter.CommentsAdapter;
import com.floorat.Adapter.NotificationsAdapter;
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

public class ViewNotification extends AppCompatActivity {

    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fetchnotification();

    }

    void fetchnotification()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Notifications...");
        pDialog.show();

        String url = "http://mogwliisjunglee.96.lt/notificationapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "get_notifications");
        params.put("name", userLocalStore.getname());

        System.out.println("Sent arguments name " + userLocalStore.getname());

        System.out.println("Response" + params.toString());

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                showNotification(response);
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

    void showNotification(JSONArray response) {
        System.out.println("show notification Response is" + response.toString());

        final ArrayList<String> ques_list = new ArrayList<>();
        final ArrayList<String> ans_list     = new ArrayList<>();
        final ArrayList<String> name_list   = new ArrayList<>();
        final ArrayList<String> pic_list    = new ArrayList<>();
        final ArrayList<String> head_pic_list   = new ArrayList<>();
        final ArrayList<String> notification_id_list   = new ArrayList<>();

        if (response.length() != 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject json = response.getJSONObject(i);
                    String ques = json.getString("initiator_name");
                    String ans = json.getString("item_name");
                    String name = json.getString("added_date");
                    String pic  = json.getString("notification_type");
                    String head_pic = json.getString("item_id");
                    String notification_id = json.getString("item_id");


                    ques_list.add(ques);
                    ans_list.add(ans);
                    name_list.add(name);
                    pic_list.add(pic);
                    head_pic_list.add(head_pic);
                    notification_id_list.add(notification_id);

                } catch (JSONException e) {
                }
            }
            ListAdapter adpt = new NotificationsAdapter(this, ques_list, ans_list, name_list, pic_list, head_pic_list);
            ListView list = (ListView) findViewById(R.id.listView4);
            list.setAdapter(adpt);

            list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            notification_read(notification_id_list.get(position));
                            if(pic_list.get(position).equals("7")  ||  pic_list.get(position).equals("8"))
                            {
                                Intent i = new Intent(ViewNotification.this, ClassifiedComment.class);
                                i.putExtra("id", head_pic_list.get(position));
                                startActivity(i);
                            }
                        }
                    }
            );
        }
    }



    void notification_read(String id)
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Motifications...");
        pDialog.show();

        String url = "http://mogwliisjunglee.96.lt/notificationapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "read_notifications");
        params.put("notification_id",id);

        System.out.println("Sent arguments name " + userLocalStore.getname());

        System.out.println("Response" + params.toString());

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                showreadNotification(response);
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

    void showreadNotification(JSONArray response) {

        String res = null;

        try {
            res = response.getString(0);
            if(res.equals("Success")){
            }
            else
                new Util().showerrormessage(ViewNotification.this,"Something Went Wrong...." +
                        "Please Try Later");
        }
        catch (JSONException e) {
        }

    }
}