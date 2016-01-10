package com.floorat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.floorat.R;
import com.floorat.SharedPrefrences.UserLocalStore;
import com.floorat.Utils.Util;

public class ErrorPage extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    UserLocalStore userlocalstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_page);


        userlocalstore = new UserLocalStore(this);

        b1 = (Button)findViewById(R.id.button);

        b1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    @Override
    public void onResume(){
        super.onResume();

        if(new Util().check_connection(this)) {
            if (userlocalstore.getuserloggedIn()) {
                String apartment = userlocalstore.getdata();
                System.out.println("Apartment" + apartment);
                Intent i = new Intent(this, Home.class);
                startActivity(i);
            } else {

                if (userlocalstore.getApartment()) {

                    System.out.println("In Apartment");
                    Intent i = new Intent(this, ApartmentsList.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(this, Login.class);
                    startActivity(i);

                }
            }
        }

    }


}