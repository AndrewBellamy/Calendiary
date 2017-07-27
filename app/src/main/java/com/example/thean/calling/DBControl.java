package com.example.thean.calling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew Bellamy for SIT207 Assignment 1
 * Student ID : 215240036
 * 24/07/2017
 */

public class DBControl extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String NOTES_ID = "id";
    public static final String NOTES_DATE = "entrydate";
    public static final String NOTES_ENTRY = "entry";

    public DBControl(Context context) {
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
    public boolean insertNote(Editable entry, long entrylongdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date entrydate = new Date(entrylongdate);
        ContentValues contentValues = new ContentValues();
        contentValues.put("entrydate", String.valueOf(entrydate));
        contentValues.put("entry", String.valueOf(entry));
        db.insert("notes", null, contentValues);
        return true;
    }

    public boolean updateNote(Integer id, Editable entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("entry", String.valueOf(entry));
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
        Cursor response = db.rawQuery("SELECT * FROM notes WHERE id="+id+"", null);
        return response;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "notes");
        return numRows;
    }

    public Bundle getDataByDate(long selectedDateLong) {
        ArrayList<String> array_entries = new ArrayList<String> ();
        ArrayList<String> array_ids = new ArrayList<String> ();
        Bundle responseBundle = new Bundle();
        Date selectedDate = new Date(selectedDateLong);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM notes WHERE entrydate=Date('"+String.valueOf(selectedDate)+"')", null);
        response.moveToFirst();

        while(response.isAfterLast() == false) {
            array_entries.add(response.getString(response.getColumnIndex("entry")));
            array_ids.add(response.getString(response.getColumnIndex("id")));
            response.moveToNext();
        }
        responseBundle.putStringArrayList("ids", array_ids);
        responseBundle.putStringArrayList("entries", array_entries);
        return responseBundle;
    }
}
