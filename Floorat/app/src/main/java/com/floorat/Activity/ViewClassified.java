package com.floorat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.floorat.Adapter.ClassifiedAdapter;
import com.floorat.Adapter.SlidingTabLayout;
import com.floorat.R;
import com.floorat.RequestHandler.CustomRequest;
import com.floorat.SharedPrefrences.UserLocalStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewClassified extends AppCompatActivity {

    String ids,imagecount;

    ViewPager pager;
    ClassifiedAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Sample 1","Sample 2","Sample 3"};

    TextView title,description,contact,price,condition;
    Button button2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classified);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        ids = extras.getString("id");
        imagecount = extras.getString("count");

        System.out.println("Count:" + ids + imagecount);

        title = (TextView)findViewById(R.id.title);
        description = (TextView)findViewById(R.id.description);
        contact = (TextView)findViewById(R.id.contact);
        price = (TextView)findViewById(R.id.price);
        condition = (TextView)findViewById(R.id.condition);

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewClassified.this, ClassifiedComment.class);
                intent.putExtra("id", ids);
                startActivity(intent);
            }
        });


        fetchbuysell();



      /*  String[] foods = {"Name" , "Name" , "Name" , "Name" , "Name" , "Name" , "Name" , "Name"};
        ListAdapter adpt = new CommentsAdapter(this, foods);
        ListView list = (ListView)findViewById(R.id.listView3);
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

*/
    }

    void fetchbuysell()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Building List...");
        pDialog.show();

        String url = "http://mogwliisjunglee.96.lt/classifiedapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "get_buy_sell_ads_details");
        params.put("bs_id", ids);

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

        final ArrayList<String> data = new ArrayList<>();
        final ArrayList<String> urls = new ArrayList<>();

        if (response.length() != 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject json = response.getJSONObject(i);
                    String title = json.getString("title");
                    String price = json.getString("price");
                    String spec = json.getString("description");
                    String ids = json.getString("user_id");
                    String cont = json.getString("contact");
                    String cond = json.getString("product_condition");
                    String i1 = json.getString("image1");
                    String i2 = json.getString("image2");
                    String i3 = json.getString("image3");

                    data.add(title);
                    data.add(price);
                    data.add(spec);
                    data.add(ids);
                    data.add(cond);
                    data.add(cont);

                    urls.add(i1);
                    urls.add(i2);
                    urls.add(i3);


                } catch (JSONException e) {
                }

                title.setText(data.get(0));
                price.setText(data.get(1));
                description.setText(data.get(2));
                condition.setText(data.get(4));
                contact.setText(data.get(5));

                if(imagecount.equals("0")){

                adapter =  new ClassifiedAdapter(getSupportFragmentManager(),Titles,1,urls);

                pager = (ViewPager) findViewById(R.id.pager1);
                pager.setAdapter(adapter);

                tabs = (SlidingTabLayout) findViewById(R.id.tabs);
                tabs.setCustomTabView(R.layout.custom_tab_title, R.id.tabtext);
                tabs.setDistributeEvenly(true);

                tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                    @Override
                    public int getIndicatorColor(int position) {
                        return getResources().getColor(R.color.tabsScrollColor);
                    }
                });

                tabs.setViewPager(pager);

                }
                else if(imagecount.equals("1")){

                    adapter =  new ClassifiedAdapter(getSupportFragmentManager(),Titles,2,urls);

                    pager = (ViewPager) findViewById(R.id.pager1);
                    pager.setAdapter(adapter);

                    tabs = (SlidingTabLayout) findViewById(R.id.tabs);
                    tabs.setCustomTabView(R.layout.custom_tab_title, R.id.tabtext);
                    tabs.setDistributeEvenly(true);

                    tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                        @Override
                        public int getIndicatorColor(int position) {
                            return getResources().getColor(R.color.tabsScrollColor);
                        }
                    });

                    tabs.setViewPager(pager);

                }
                else if(imagecount.equals("2")){

                    adapter =  new ClassifiedAdapter(getSupportFragmentManager(),Titles,3,urls);

                    pager = (ViewPager) findViewById(R.id.pager1);
                    pager.setAdapter(adapter);

                    tabs = (SlidingTabLayout) findViewById(R.id.tabs);
                    tabs.setCustomTabView(R.layout.custom_tab_title, R.id.tabtext);
                    tabs.setDistributeEvenly(true);

                    tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                        @Override
                        public int getIndicatorColor(int position) {
                            return getResources().getColor(R.color.tabsScrollColor);
                        }
                    });

                    tabs.setViewPager(pager);

                }
            }



        }
    }


}