package com.floorat.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.floorat.Adapter.BuySelllistadapter;
import com.floorat.R;

import java.util.ArrayList;
import java.util.List;

public class BuySell extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell);

        final String [] category_name={"Electronics","Property","Mobiles & Tablets","Vehicle","Home & Lifestyle"};
        int [] category_img={R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic,R.drawable.nic};

        ListAdapter myadpt1 = new BuySelllistadapter(this, category_name, category_img);
        ListView mylist = (ListView) findViewById(R.id.listView);
        mylist.setAdapter(myadpt1);

        mylist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getBaseContext(), Classifieds.class);
                            intent.putExtra("cat", category_name[position]);
                            startActivity(intent);
                        }
                }
        );
    }
}