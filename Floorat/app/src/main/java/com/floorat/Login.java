package com.floorat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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


public class Login extends AppCompatActivity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    LoginButton login;
    int flag =0;
    public Util u = new Util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseHelper db =new DatabaseHelper(this);
//Database object


        callbackManager = CallbackManager.Factory.create();


        login = (LoginButton)findViewById(R.id.login_button);
        login.setReadPermissions("user_friends");




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

                }
            });


        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {

                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {


                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            JSONObject json = response.getJSONObject();
                            String text = null;

                            try {
                                if (json != null) {
                                    text = json.getString("gender");

                                    if (text.equals("female")) {
                                        System.out.println("I am" + text);
                                        flag++;
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
                                    String friend_list = null;


                                    try {
                                        JSONObject js = json.getJSONObject("summary");
                                        friend_list = js.getString("total_count");
                                        System.out.println("Response  is total_count" + friend_list);
                                        //int result = friend_list.compareTo("50");

                                        int result = Integer.parseInt(friend_list);
                                        if (result > 0) {
                                            System.out.println("I am" + friend_list);
                                            flag++;
                                        }

                                    } catch (JSONException e) {
                                        System.out.println("Response was ");
                                        e.printStackTrace();
                                    }

                                    if(flag == 2){
                                        System.out.println("Flag Value" + flag);
                                        u.setFlag(flag);
                                    }
                                    else {
                                        System.out.println("Flag Value" + flag);
                                        login.performClick();
                                        u.setFlag(flag);
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
            public void onError(FacebookException exception) {

            }
        });


    }


   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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