package com.floorat;

import android.content.Context;
import android.content.SharedPreferences;


public class UserLocalStore {

        public static final String SP_Name = "userDetails";
        SharedPreferences userLocalDatabase;

        public UserLocalStore(Context context)
        {
            userLocalDatabase = context.getSharedPreferences(SP_Name,0);
        }

        public void userData(String data)
        {
            SharedPreferences.Editor speditor = userLocalDatabase.edit();
            speditor.putString("flag",data);
            speditor.commit();
        }

        public void setUserloggedIn(boolean loggedIn){
            SharedPreferences.Editor speditor = userLocalDatabase.edit();
            speditor.putBoolean("loggedIn",loggedIn);
            speditor.commit();

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
