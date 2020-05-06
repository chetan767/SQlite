package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "PASSWORD";

    public DBhelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DDROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
 
    public boolean insertData(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor showData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT* FROM " + TABLE_NAME, null);
        return cursor;
    }

    public int delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int x = db.delete(TABLE_NAME, "ID=?", new String[]{id});
        return x;
    }

    public boolean update(ContentValues values,String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME,values,"ID=?",new String[]{id});
        return true;
	    }

    public void clearDatabase() {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        sqlDB.execSQL(clearDBQuery);
    }
}
