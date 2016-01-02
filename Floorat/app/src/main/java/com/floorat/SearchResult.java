package com.floorat;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toast.makeText(getApplicationContext(), "Call hua", Toast.LENGTH_SHORT).show();


        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("Query kuch yeh hai "+query);
            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
            //use the query to search your data somehow
        }
        else{
            System.out.println("Query aai hi nhi pa rhi");
            Toast.makeText(getApplicationContext(), "No query", Toast.LENGTH_SHORT).show();
        }
    }
}
