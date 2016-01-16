package com.floorat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.floorat.R;
import com.floorat.SharedPrefrences.UserLocalStore;
import com.floorat.Utils.Util;

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

                if(new Util().check_connection(Splash.this)) {
                    if (userlocalstore.getuserloggedIn()) {
                        String apartment = userlocalstore.getdata();
                        System.out.println("Apartment" + apartment);
                        Intent i = new Intent(Splash.this, Home.class);
                        startActivity(i);
                    } else {

                        if (userlocalstore.getApartment()) {

                            System.out.println("In Apartment");
                            Intent i = new Intent(Splash.this, ApartmentsList.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(Splash.this, Login.class);
                            startActivity(i);

                        }
                    }
                }
                else{
                    Intent i = new Intent(Splash.this, ErrorPage.class);
                    startActivity(i);
                }


                finish();
                }
            }, SPLASH_TIME_OUT);
        }
}


