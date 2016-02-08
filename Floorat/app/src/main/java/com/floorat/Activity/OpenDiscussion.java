package com.floorat.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.floorat.Adapter.GroupListAdapter;
import com.floorat.Adapter.Noticeboardlistadapter;
import com.floorat.Adapter.OpenDiscussionListAdapter;
import com.floorat.Objects.Noticelist;
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

public class OpenDiscussion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_discussion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getdiscussion();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                        LayoutInflater layoutInflater = LayoutInflater.from(OpenDiscussion.this);
                        View promptView = layoutInflater.inflate(R.layout.classified_comment_input_dialog, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OpenDiscussion.this);
                        alertDialogBuilder.setView(promptView);

                        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       // userLocalStore = new UserLocalStore(ctx);
                                            String str = editText.getText().toString();
                                            insertdiscussion(str);
                                    }
                                })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();

                    }
                });
    }


    void getdiscussion()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Discussion List...");
        pDialog.show();
        UserLocalStore userLocalStore;
        userLocalStore = new UserLocalStore(this);

        String url = "http://mogwliisjunglee.96.lt/groupapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "get_open_discussion");
        params.put("building_name", userLocalStore.getdata());

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                shownotices(response);
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


    void shownotices(JSONArray response) {
        System.out.println("Response is" + response.toString());

        final ArrayList<String> uploadingdate = new ArrayList<>();
        final ArrayList<String> id            = new ArrayList<>();
        ArrayList<String> user          = new ArrayList<>();
        final ArrayList<String> pic           = new ArrayList<>();
        final ArrayList<String> text          = new ArrayList<>();
        final ArrayList<String> count         = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                uploadingdate.add(json.getString("date"));
                id.add(json.getString("id"));
                user.add(json.getString("user"));
                pic.add(json.getString("pic"));
                text.add(json.getString("text"));
                count.add(json.getString("count"));


            } catch (JSONException e) {
            }
        }

        ListAdapter adpt = new OpenDiscussionListAdapter(this,uploadingdate,id,user,pic,text,count);
        final ListView lv = (ListView)findViewById(R.id.oplist);
        lv.setAdapter(adpt);

        final AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                Intent i = new Intent(getApplicationContext(),DiscussionDetails.class);
                i.putExtra("id",id.get(position));
                i.putExtra("pic",pic.get(position));
                i.putExtra("text",text.get(position));
                i.putExtra("count",count.get(position));

                startActivity(i);
            }
        };

        lv.setOnItemClickListener(listener);


    }
    void insertdiscussion(String str)
    {
        UserLocalStore userLocalStore;
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving Data...");
        pDialog.show();

        userLocalStore = new UserLocalStore(OpenDiscussion.this);

        String apt = userLocalStore.getdata();
        String name = userLocalStore.getname();

        String url = "http://mogwliisjunglee.96.lt/groupapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "insert_open_discussion");
        params.put("building_name",apt);
        params.put("user_name",name);
        params.put("text",str);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                confirmresult(response);
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

    void confirmresult(JSONArray response){

        String res = null;

        try {
            res = response.getString(0);
            if(res.equals("Success")){
                Toast.makeText(OpenDiscussion.this, "New Discussion Added", Toast.LENGTH_SHORT).show();
            }
            else
                new Util().showerrormessage(OpenDiscussion.this,"Something Went Wrong...." +
                        "Please Try Later");
        }
        catch (JSONException e) {
        }
    }
}