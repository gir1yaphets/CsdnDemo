package com.example.pengxiaolve.csdndemo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pengxiaolve on 16/6/19.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "News.db";

    private static final String SQL_CREATE = "create table newsitems (_id integer primary key autoincrement, " +
            "title text, link text, date text, imagelink text, content text, newstype integer)";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
