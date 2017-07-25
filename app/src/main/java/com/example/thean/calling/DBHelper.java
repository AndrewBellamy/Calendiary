package com.example.thean.calling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew Bellamy for SIT207 Assignment 1
 * Student ID : 215240036
 * 24/07/2017
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String NOTES_ID = "id";
    public static final String NOTES_DATE = "entrydate";
    public static final String NOTES_ENTRY = "entry";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
          "CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, entrydate DATE, entry TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    //modification methods
    public boolean insertNote(String entry, Date entrydate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("entrydate", String.valueOf(entrydate));
        contentValues.put("entry", entry);
        db.insert("notes", null, contentValues);
        return true;
    }

    public boolean updateNote(Integer id, String entry, Date entrydate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("entrydate", String.valueOf(entrydate));
        contentValues.put("entry", entry);
        db.update("notes", contentValues, "id = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public Integer deleteNote (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("notes", "id = ? ", new String[] {Integer.toString(id)});
    }

    //general predicate methods
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM notes WHERE id="+id+"", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "notes");
        return numRows;
    }

    public ArrayList<String> getDataByDate(Date selectedDate) {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM notes WHERE entrydate="+selectedDate+"", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("entry")));
            res.moveToNext();
        }
        return array_list;
    }
}
