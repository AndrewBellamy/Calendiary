import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
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
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
          "CREATE TABLE notes (id INTEGER PRIMARY KEY, entrydate DATE, entry TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }
}
