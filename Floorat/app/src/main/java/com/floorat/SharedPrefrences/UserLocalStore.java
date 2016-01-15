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

        public void userData(String name,String gender,String url,String aptname)
        {
            SharedPreferences.Editor speditor = userLocalDatabase.edit();
            speditor.putString("name",name);
            speditor.putString("gender",gender);
            speditor.putString("url",url);
            speditor.putString("aptname",aptname);

            speditor.commit();
        }


        public void updatedata(String apartment)
        {
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putString("aptname",apartment);
        speditor.apply();
        }

        public String getdata(){

        String name = userLocalDatabase.getString("aptname", "");
        return name;

        }

    public String getname(){
        String name = userLocalDatabase.getString("name", "");
        return name;
    }

    public String getgender(){

        String name = userLocalDatabase.getString("gender", "");
        return name;

    }

    public String geturl(){

        String name = userLocalDatabase.getString("url", "");
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
