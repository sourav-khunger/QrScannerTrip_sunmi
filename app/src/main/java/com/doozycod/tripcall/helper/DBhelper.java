package com.doozycod.tripcall.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "location.db";
    public final static String TABLE_NAME = "location_table";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(column_id integer primary key, qrcode text, origin_lat text,origin_long text,dest_lat text, dest_long text,start_time text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean updateDestination(String qrcode, String dest_lati, String dest_long) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("qrcode", qrcode);
        contentValues.put("dest_lat", dest_lati);
        contentValues.put("dest_long", dest_long);

//        contentValues.put("start_time", end_time);
        db.update("location_table", contentValues, "qrcode=?", new String[]{qrcode});

        //        db.close();
        return true;
    }

    public boolean insertOrigin(String qrcode, double latitude, double longitude,String start_time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("qrcode", qrcode);
        contentValues.put("origin_lat", latitude);
        contentValues.put("origin_long", longitude);
        contentValues.put("start_time", start_time);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }
    public void delete(String qrcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("location_table",  "qrcode=?", new String[]{qrcode});
    }
    public List<DatabaseModel> getDataFromDb() {

        List<DatabaseModel> modelList = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DatabaseModel model = new DatabaseModel();

                model.setQRCode(cursor.getString(1));


                modelList.add(model);

            } while (cursor.moveToNext());
        }

        Log.d("Location Table", modelList.toString());

        return modelList;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}