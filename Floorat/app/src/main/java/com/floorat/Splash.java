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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                if(isConnected()) {
                    int fl = new Util().getFlag();
                    System.out.println("flag kuch yeh aaya hai "+fl);
                    if(fl==1){
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


