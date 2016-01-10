package com.floorat.DataBaseHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudentList.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "gender";
    public static final String COL_2 = "friend_list";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(gender String ,friend_list String)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public Cursor getlist(){

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
            return c;
    }

    void insertlist(String gender, String friend_list) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, gender);
        contentValues.put(COL_2, friend_list);

        db.insert(TABLE_NAME, null, contentValues);

    }
}

