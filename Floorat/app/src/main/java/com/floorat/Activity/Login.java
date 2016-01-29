package com.floorat.Activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.floorat.RequestHandler.CustomRequest;
import com.floorat.R;
import com.floorat.Adapter.SlidingImage_Adapter;
import com.floorat.SharedPrefrences.UserLocalStore;
import com.floorat.Utils.Util;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class Login extends AppCompatActivity{

    UserLocalStore userlocalstore;
    CallbackManager callbackManager;
    LoginButton login;

    int flag =0;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.nic,R.drawable.nic,R.drawable.nic};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        init();

        userlocalstore = new UserLocalStore(this);

        callbackManager = CallbackManager.Factory.create();

        login = (LoginButton)findViewById(R.id.login_button);
        login.setReadPermissions("user_friends");


//If implement Logout
/*
        Intent intent=this.getIntent();
        String value = null;
        if(intent.getExtras() !=null)
            value = intent.getExtras().getString("keyName");


        if(value!=null && value.equals("logout"))
        {
            if(AccessToken.getCurrentAccessToken() != null ) {
                // RequestData();
                login.performClick();
            }

        }
        else if (AccessToken.getCurrentAccessToken() != null){
           // startActivity(new Intent(this, StartActivity.class));

        }

        */

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
            }
        });


        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {

                    login.setVisibility(LoginButton.GONE);

                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {


                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            JSONObject json = response.getJSONObject();
                            System.out.println("Gender " + json.toString());
                            String gender, name, url;

                            try {
                                if (json != null) {
                                    gender = json.getString("gender");
                                    name = json.getString("name");

                                    if (gender.equals("male"))
                                        flag++;

                                    JSONObject picture = json.getJSONObject("picture");
                                    JSONObject data = picture.getJSONObject("data");

                                    url = data.getString("url");

                                    userlocalstore.userData(name, gender, url, "null");

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "gender,picture,name");
                    request.setParameters(parameters);
                    request.executeAsync();


                    new GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/me/friends",
                            null,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {


                                public void onCompleted(GraphResponse response) {
            /* handle the result */
                                    JSONObject json = response.getJSONObject();
                                    String friend_list;


                                    try {
                                        JSONObject js = json.getJSONObject("summary");
                                        friend_list = js.getString("total_count");
                                        //int result = friend_list.compareTo("50");

                                        int result = Integer.parseInt(friend_list);
                                        if (result > 50) {
                                            System.out.println("I am" + friend_list);
                                            flag++;
                                        }

                                    } catch (JSONException e) {
                                        System.out.println("Response was ");
                                        e.printStackTrace();
                                    }

                                    System.out.println("Flag value" + flag);

                                    if (flag == 2) {
                                         login.setVisibility(LoginButton.GONE);


                                         FacebookSdk.sdkInitialize(getApplicationContext());
                                         LoginManager.getInstance().logOut();

                                         userlocalstore.setApartment(true);

                                        Intent i = new Intent(getApplicationContext(), ApartmentsList.class);
                                        startActivity(i);

                                    } else {
                                        FacebookSdk.sdkInitialize(getApplicationContext());
                                        LoginManager.getInstance().logOut();
                                        login.setVisibility(LoginButton.VISIBLE);
                                        System.out.println("Flag Value" + flag);
                                        new Util().showerrormessage(Login.this, "Sorry but you must be female with minimum 50 friends");
                                    }
                                }
                            }
                    ).executeAsync();


                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Try After Sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(Login.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
                }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        if(!new Util().check_connection(Login.this)){
            Intent in = new Intent(Login.this,ErrorPage.class);
            startActivity(in);
        }

    }


}

