package com.floorat;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    static int SPLASH_TIME_OUT = 3000;
    UserLocalStore userlocalstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userlocalstore = new UserLocalStore(this);


        new Handler().postDelayed(new Runnable() {

            @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                Intent in = new Intent(Splash.this, Home.class);
                startActivity(in);


                if(isConnected()) {
                    if(userlocalstore.getuserloggedIn()){
                        Intent i = new Intent(Splash.this, Home.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(Splash.this, Login.class);
                        startActivity(i);
                    }
                }else{
                    Intent i = new Intent(Splash.this, ErrorPage.class);
                    startActivity(i);
                }


                finish();
                }
            }, SPLASH_TIME_OUT);
        }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}


