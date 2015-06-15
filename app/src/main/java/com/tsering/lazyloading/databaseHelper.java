package com.tsering.lazyloading;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 3/22/15.
 */
public class databaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final String DB_name = "Ekbana";
    public static final int version = 1;
    //Detail
    public static final String Tbl_name_Detail = "Detail";
    public static final String Detail_id= "_id";
    public static final String Detail_name= "Name";
    public static final String Detail_Content= "Content";
    public static final String Detail_isFav= "IsFav";
    public static final String Detail_cat_name= "Cat_name";
    public static final String Detail_date= "Date";
    public static final String create_detail = "Create table if not exists " + Tbl_name_Detail + "("+Detail_id+" varchar primary key,"+Detail_cat_name+" varchar,"+Detail_name+" varchar,"+Detail_Content+" varchar,"+Detail_date+" integer,"+Detail_isFav+" integer"+" );";
    public static final String drop_detail = "Drop table " + Tbl_name_Detail + ";";


    public databaseHelper(Context context) {
        super(context, DB_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_detail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(drop_detail);
        onCreate(sqLiteDatabase);
    }
}