package com.android.tonyvu.sc.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbookpro on 2017-04-18.
 */

public class C0699653TEST1 extends SQLiteOpenHelper {

    public C0699653TEST1(Context context) {
        super(context, "C0699653_Test1", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE USERS(email TEXT NOT NULL  PRIMARY KEY ,password TEXT NOT NULL,number TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS USERS";
        db.execSQL(sql);
        onCreate(db);
    }

    public void dbInsert(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues userData = getUserContentValues(user);
        db.insert("USERS",null,userData);

    }

    private ContentValues getUserContentValues(User user) {
        ContentValues userData = new ContentValues();
        userData.put("email", user.getEmail());
        userData.put("password",user.getPassword());
        userData.put("number",user.getNumber());
        return userData;

    }

    public List<User> dbSearch(){
     String sql="SELECT * FROM USERS;";
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery(sql,null);

        List<User> userList = new ArrayList<User>();
        while(c.moveToNext()){
            User user=new User();
            user.setEmail(c.getString(c.getColumnIndex("email")));
            user.setPassword(c.getString(c.getColumnIndex("password")));
            user.setNumber(c.getString(c.getColumnIndex("number")));
            userList.add(user);
        }
        c.close();
        return userList;
    }

}