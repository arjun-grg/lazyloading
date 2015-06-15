package com.tsering.lazyloading;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by root on 1/5/15.
 */
public class DBManager {
    databaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    static DBManager INSTANCE;


    DBManager(Context context) {
        databaseHelper = new databaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public static DBManager getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new DBManager(context);
        return INSTANCE;
    }

    public void InsertDetail(ArrayList<Detail_info> list) {
//        sqLiteDatabase.delete(databaseHelper.Tbl_name_Detail, null, null);
        for (int i = 0; i < list.size(); i++) {
            ContentValues contentValues = new ContentValues();
            Detail_info detail = list.get(i);
            contentValues.put(databaseHelper.Detail_id, detail.id);
            contentValues.put(databaseHelper.Detail_cat_name, detail.cat_name);
            contentValues.put(databaseHelper.Detail_name, detail.name);
            contentValues.put(databaseHelper.Detail_Content, detail.content);
            contentValues.put(databaseHelper.Detail_isFav, detail.isFav);
            sqLiteDatabase.insert(databaseHelper.Tbl_name_Detail, null, contentValues);
        }
    }

    public void InsertDetail(Detail_info detail) {
//        sqLiteDatabase.delete(databaseHelper.Tbl_name_Detail, null, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.Detail_id, detail.id);
        contentValues.put(databaseHelper.Detail_cat_name, detail.cat_name);
        contentValues.put(databaseHelper.Detail_name, detail.name);
        contentValues.put(databaseHelper.Detail_Content, detail.content);
        contentValues.put(databaseHelper.Detail_date, detail.date);
        contentValues.put(databaseHelper.Detail_isFav, detail.isFav);
        sqLiteDatabase.insert(databaseHelper.Tbl_name_Detail, null, contentValues);

    }

    public void UpdateDetail(String name, int isFav) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.Detail_isFav, isFav);
        String whereClause = databaseHelper.Detail_name + " = ? ";
        String[] whereArgs = new String[]{name};
        sqLiteDatabase.update(databaseHelper.Tbl_name_Detail, contentValues, whereClause, whereArgs);
    }
    public ArrayList<Detail_info> ShowingDetail(String id) {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String[] columns={databaseHelper.Detail_name,databaseHelper.Detail_Content,databaseHelper.Detail_isFav};
        String whereArgs=databaseHelper.Detail_id+"=?";
        String[] selectionArgs={id};
        Cursor cursor = sqLiteDatabase.query(databaseHelper.Tbl_name_Detail,columns,whereArgs,selectionArgs,null,null,databaseHelper.Detail_date+" DESC",null);
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_isFav);
            int index2 = cursor.getColumnIndex(databaseHelper.Detail_Content);
            detail.name = cursor.getString(index0);
            detail.isFav = cursor.getInt(index1);
            detail.content = cursor.getString(index2);
            list.add(detail);
        }
        cursor.close();
        return list;
    }
    public ArrayList<Detail_info> ShowingDetailTitle() {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String sql = "select " + databaseHelper.Detail_id+ ","+ databaseHelper.Detail_name + "," + databaseHelper.Detail_Content + "," + databaseHelper.Detail_isFav + " from " + databaseHelper.Tbl_name_Detail + " where " + databaseHelper.Detail_Content + "!='null' order by "+databaseHelper.Detail_date+" DESC;";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_isFav);
            int index2 = cursor.getColumnIndex(databaseHelper.Detail_Content);
            int index3 = cursor.getColumnIndex(databaseHelper.Detail_id);
            detail.id=cursor.getString(index3);
            detail.name = cursor.getString(index0);
            detail.isFav = cursor.getInt(index1);
            detail.content = cursor.getString(index2);
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail_info> ShowingDetailFavTitle() {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String sql = "select " + databaseHelper.Detail_id+ ","+ databaseHelper.Detail_name + "," + databaseHelper.Detail_Content + "," + databaseHelper.Detail_isFav + " from " + databaseHelper.Tbl_name_Detail + " where " + databaseHelper.Detail_isFav + "=1 and " + databaseHelper.Detail_Content + "!='null' order by "+databaseHelper.Detail_date+" DESC;";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_isFav);
            int index2 = cursor.getColumnIndex(databaseHelper.Detail_Content);
            int index3 = cursor.getColumnIndex(databaseHelper.Detail_id);
            detail.id=cursor.getString(index3);
            detail.name = cursor.getString(index0);
            detail.isFav = cursor.getInt(index1);
            detail.content = cursor.getString(index2);
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail_info> ShowingDetailTitle(String name) {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String sql = "select "+ databaseHelper.Detail_id+ "," + databaseHelper.Detail_name + "," + databaseHelper.Detail_Content + "," + databaseHelper.Detail_isFav + " from " + databaseHelper.Tbl_name_Detail + " where " + databaseHelper.Detail_cat_name + "=\"" + name + "\" and " + databaseHelper.Detail_Content + "!='null' order by "+databaseHelper.Detail_date+" DESC;";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_isFav);
            int index2 = cursor.getColumnIndex(databaseHelper.Detail_Content);
            int index3 = cursor.getColumnIndex(databaseHelper.Detail_id);
            detail.id=cursor.getString(index3);
            detail.name = cursor.getString(index0);
            detail.isFav = cursor.getInt(index1);
            detail.content = cursor.getString(index2);
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail_info> ShowingDetailIMg(String name) {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String sql = "select " + databaseHelper.Detail_id+ "," + databaseHelper.Detail_name + "," + databaseHelper.Detail_isFav + " from " + databaseHelper.Tbl_name_Detail + " where " + databaseHelper.Detail_cat_name + "=\"" + name + "\" and " + databaseHelper.Detail_Content + "='null' order by "+databaseHelper.Detail_date+" DESC;";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_isFav);
            int index3 = cursor.getColumnIndex(databaseHelper.Detail_id);
            detail.id=cursor.getString(index3);
            detail.name = cursor.getString(index0);
            detail.isFav = cursor.getInt(index1);
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail_info> ShowingDetailFavIMg() {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String sql = "select " + databaseHelper.Detail_id + "," + databaseHelper.Detail_name + "," + databaseHelper.Detail_isFav + " from " + databaseHelper.Tbl_name_Detail + " where " + databaseHelper.Detail_isFav + "=1 and " + databaseHelper.Detail_Content + "='null' order by "+databaseHelper.Detail_date+" DESC;";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_isFav);
            int index3 = cursor.getColumnIndex(databaseHelper.Detail_id);
            detail.id=cursor.getString(index3);
            detail.name = cursor.getString(index0);
            detail.isFav = cursor.getInt(index1);
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail_info> ShowingDetailIMg() {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String sql = "select " + databaseHelper.Detail_id + "," + databaseHelper.Detail_name + "," + databaseHelper.Detail_isFav + " from " + databaseHelper.Tbl_name_Detail + " where " + databaseHelper.Detail_Content + "='null' order by "+databaseHelper.Detail_date+" DESC;";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_isFav);
            int index3 = cursor.getColumnIndex(databaseHelper.Detail_id);
            detail.id=cursor.getString(index3);
            detail.name = cursor.getString(index0);
            detail.isFav = cursor.getInt(index1);
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail_info> ShowingDetailAll(String category) {
        ArrayList<Detail_info> list = new ArrayList<Detail_info>();
        String[] column = {databaseHelper.Detail_id, databaseHelper.Detail_name, databaseHelper.Detail_Content};
        String whereArgs = databaseHelper.Detail_cat_name + "=?";
        String[] selectionArgs = {category};
        Cursor cursor = sqLiteDatabase.query(databaseHelper.Tbl_name_Detail, column, whereArgs, selectionArgs, null, null,databaseHelper.Detail_date+" DESC");
        while (cursor.moveToNext()) {
            Detail_info detail = new Detail_info();
            int index0 = cursor.getColumnIndex(databaseHelper.Detail_id);
            int index1 = cursor.getColumnIndex(databaseHelper.Detail_name);
            int index2 = cursor.getColumnIndex(databaseHelper.Detail_Content);
            detail.id = cursor.getString(index0);
            detail.name = cursor.getString(index1);
            detail.content = cursor.getString(index2);
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    public Boolean CheckInDb(String id) {

        String[] column = {databaseHelper.Detail_id};
        String whereArgs = databaseHelper.Detail_id + "=?";
        String[] selectionArgs = {id};
        Cursor cursor = sqLiteDatabase.query(databaseHelper.Tbl_name_Detail, column, whereArgs, selectionArgs, null, null, null);
        int size = 0;
        while (cursor.moveToNext()) {
            size++;
            break;
        }
        cursor.close();
        if (size == 0) {
            return false;
        } else {
            return true;
        }
    }

}
