package com.android.tonyvu.sc.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loven on 27-04-2017.
 */

public class productDatabase extends SQLiteOpenHelper {

    public productDatabase(Context context) {
        super(context, "productDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Orders(email TEXT NOT NULL , price FLOAT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Orders";
        db.execSQL(sql);
        onCreate(db);
    }

    public void dbInsert(Order order) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues orderData = getUserContentValues(order);
        db.insert("Orders",null,orderData);

    }

    private ContentValues getUserContentValues(Order order) {
        ContentValues userData = new ContentValues();
        userData.put("email",order.getEmail());
        userData.put("price",order.getPrice());
        return userData;

    }

    public List<Order> dbSearch(){
        String sql="SELECT * FROM Orders;";
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery(sql,null);

        List<Order> orderList = new ArrayList<Order>();
        while(c.moveToNext()){
            Order order=new Order();
            order.setEmail(c.getString(c.getColumnIndex("email")));
            order.setPrice(c.getFloat(c.getColumnIndex("price")));
            orderList.add(order);
        }
        c.close();
        return orderList;
    }

}