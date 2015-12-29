package com.floorat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    LoginButton login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        callbackManager = CallbackManager.Factory.create();

      //  if(isConnected()) {
        login = (LoginButton)findViewById(R.id.login_button);
        login.setReadPermissions("user_friends");
        //}
        //else
            //Toast.makeText(getApplicationContext(), "You are Not Connected", Toast.LENGTH_SHORT).show();




//If implement Logout

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


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        //
                        //  profile.setProfileId(null);
                    }
                }
            });


        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {

                    int data = RequestData();

                    if(data == 1) {
                        login.setVisibility(View.INVISIBLE);
                    //    startActivity(new Intent(getApplicationContext(), StartActivity.class));
                    }
                    else
                        showerrormessage();

                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

                Toast.makeText(getApplicationContext(), "You are Not Connected", Toast.LENGTH_SHORT).show();
            }
        });


    }



    public int RequestData(){

        final int[] flag = new int[1];

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {


            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                String text = null;
                // System.out.println("Response is " + response.toString());
                // System.out.println("Response" + object.toString());
                try {
                    if (json != null) {
                        text = json.getString("gender");

                        if (text.equals("female")) {
                            System.out.println("I am" + text);
                            flag[0]++;
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



        Bundle parameters = new Bundle();
        parameters.putString("fields", "gender");
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
                        System.out.println("Response is " + json.toString());
                        String ram = null;


                        try {
                            JSONObject js = json.getJSONObject("summary");
                            ram = js.getString("total_count");
                            System.out.println("Response  is total_count" + ram);
                            int result = ram.compareTo("50");
                            if(result >0) {
                                System.out.println("I am" + ram);
                                flag[0]++;
                            }

                        } catch (JSONException e) {
                            System.out.println("Response was ");
                            e.printStackTrace();
                        }



                    }



                }
        ).executeAsync();

        System.out.println("I am" + flag[0]);

        if (flag[0] == 2 )
            return 1;
        else
            return 0;



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showerrormessage(){
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(Login.this);
        dialogbuilder.setMessage("Sorry but you must be femalse with minimum 50 friends");
        dialogbuilder.setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        login.performClick();
                    }

                });
        dialogbuilder.show();

    }




}
