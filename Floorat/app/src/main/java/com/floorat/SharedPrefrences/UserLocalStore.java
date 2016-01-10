package com.floorat.SharedPrefrences;

import android.content.Context;
import android.content.SharedPreferences;


public class UserLocalStore {

        public static final String SP_Name = "userDetails";
        SharedPreferences userLocalDatabase;

        public UserLocalStore(Context context)
        {
            userLocalDatabase = context.getSharedPreferences(SP_Name,0);
        }

        public void userData(String data,String apartment)
        {
            SharedPreferences.Editor speditor = userLocalDatabase.edit();
            speditor.putString("flag",data);
            speditor.putString("apartment",apartment);
            speditor.commit();
        }


        public void updatedata(String data,String apartment)
        {
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putString("flag",data);
        speditor.putString("apartment",apartment);
        speditor.apply();
        }

        public String getdata(){

        String name = userLocalDatabase.getString("apartment", "");
        return name;

        }

        public void setUserloggedIn(boolean loggedIn){
            SharedPreferences.Editor speditor = userLocalDatabase.edit();
            speditor.putBoolean("loggedIn",loggedIn);
            speditor.commit();

        }

        public void setApartment(boolean loggedIn){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putBoolean("Apartment",loggedIn);
        speditor.commit();

        }

         public boolean getApartment(){

        if(userLocalDatabase.getBoolean("Apartment",false) == true)
            return true;
        else
            return false;
        }



        public boolean getuserloggedIn(){

            if(userLocalDatabase.getBoolean("loggedIn",false) == true)
                return true;
            else
                return false;
        }



        public void clearUserdata(){
            SharedPreferences.Editor speditor = userLocalDatabase.edit();
            speditor.clear();

        }
    }
