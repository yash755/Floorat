package com.floorat;


import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
    static int flag;
    public int getFlag(){
        return flag;
    }

    public void setFlag(int x)
    {
        System.out.println("Flag is was" + x);
        flag = x;
    }

}
