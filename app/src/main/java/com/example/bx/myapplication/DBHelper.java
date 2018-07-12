package com.example.bx.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BX on 05.06.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "detta";

    public static final String TABLE_SKU = "sku";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_COUNT = "count";

    public DBHelper(Context contex){
        super(contex,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_SKU+"(" + KEY_ID + " integer primary key,"
                + KEY_NAME + " text,"
                + KEY_COUNT + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_SKU);
        onCreate(db);
    }
}
