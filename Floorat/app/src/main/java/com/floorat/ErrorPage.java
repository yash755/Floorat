package com.floorat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ErrorPage extends AppCompatActivity implements View.OnClickListener {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_page);

        b1 = (Button)findViewById(R.id.button);

        b1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(Settings.ACTION_SETTINGS));

    }
}
