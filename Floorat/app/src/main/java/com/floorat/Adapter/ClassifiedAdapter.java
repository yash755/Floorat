package com.floorat.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class ClassifiedAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    ArrayList<String> urls;



    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ClassifiedAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb,ArrayList<String> urls) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.urls = urls;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {


        BuySell bs = new BuySell();
        Bundle bundle = new Bundle();
        bundle.putString("url", urls.get(position));
        bs.setArguments(bundle);

        return bs;


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {

        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}