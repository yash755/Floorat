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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.floorat.Adapter.OpenDiscussionCommentAdapter;
import com.floorat.Adapter.OpenDiscussionListAdapter;
import com.floorat.ImageUtils.RoundImageLoader;
import com.floorat.R;
import com.floorat.RequestHandler.CustomRequest;
import com.floorat.RoundImage;
import com.floorat.SharedPrefrences.UserLocalStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscussionDetails extends AppCompatActivity {

    String ids,pic,text,count;
    RoundImageLoader roundImageLoader;
    UserLocalStore userLocalStore;
    String userpic;
    EditText postcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);

        Bundle extras = getIntent().getExtras();
        ids = extras.getString("id");
        pic = extras.getString("pic");
        text= extras.getString("text");
        count=extras.getString("count");

        ImageView pics= (ImageView)findViewById(R.id.pic);
        TextView  head= (TextView)findViewById(R.id.headingop);
        TextView  com = (TextView)findViewById(R.id.comments);
        ImageView upic= (ImageView)findViewById(R.id.userpic);
          postcomment = (EditText)findViewById(R.id.commentis);

        getdiscussiondetails();

        roundImageLoader = new RoundImageLoader(this);

        head.setText(text);
        com.setText(count);

        roundImageLoader.DisplayImage(pic, pics);

        userpic = userLocalStore.geturl();

        roundImageLoader.DisplayImage(userpic,upic);

    }

    void getdiscussiondetails()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Data...");
        pDialog.show();
        UserLocalStore userLocalStore;
        userLocalStore = new UserLocalStore(this);

        String url = "http://mogwliisjunglee.96.lt/groupapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "get_open_discussion_details");
        params.put("id", ids);

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

        final ArrayList<String> text  = new ArrayList<>();
        final ArrayList<String> image = new ArrayList<>();


        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                text.add(json.getString("text"));
                image.add(json.getString("image"));


            } catch (JSONException e) {
            }
        }

            ListAdapter adpt = new OpenDiscussionCommentAdapter(this, text, image);
            final ListView lv = (ListView) findViewById(R.id.ddlist);
            lv.setAdapter(adpt);


    }

    public void senddd(View view){

        String data = postcomment.getText().toString();

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving Data...");
        pDialog.show();
        UserLocalStore userLocalStore;
        userLocalStore = new UserLocalStore(this);

        String name =  userLocalStore.getname();
        String apt  =  userLocalStore.getdata();

        String url = "http://mogwliisjunglee.96.lt/groupapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "insert_open_discussion_details");
        params.put("id", ids);
        params.put("text",data);
        params.put("user_name",name);
        params.put("building_name",apt);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Comment Saved!!!", Toast.LENGTH_SHORT).show();
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

}
