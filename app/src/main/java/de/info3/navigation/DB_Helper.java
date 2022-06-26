package de.info3.navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;


//ist datenbank für die tracks aus der Tracing activity

public class DB_Helper extends SQLiteOpenHelper {

    //private static final String Tag = "DBHelper";

    private String TABLE_NAME = "mJournal_table";


    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_MODI = "Modi";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_DISTANCE = "Distance";
    private static final String COLUMN_CALENDAR = "Calendar";
    private static final String COLUMN_LOCATION = "Location";
    private SQLiteDatabase sqLiteDatabase;

    int old;

    private static final String _DB_FILE_NAME = "locations.db";
    public DB_Helper (Context context) { super(context, _DB_FILE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MODI + " Text, " + COLUMN_TIME + " Text, " + COLUMN_DISTANCE + " Text, " + COLUMN_CALENDAR + " Text, " + COLUMN_LOCATION + " Text)" ;
        try{
        sqLiteDatabase.execSQL(createTable);}
        catch(SQLiteException fail){}



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.delete(TABLE_NAME, null, null);
        TABLE_NAME = "mJournal_table" + i1;
        onCreate(sqLiteDatabase);
    }



    public void deleteAll(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        old = 1;
        int newer = old+1;
        onUpgrade(sqLiteDatabase, old, newer);

    }




    public void addData(String modi, String time, String distance, String calendar, String location){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_MODI, modi);
        contentValues.put(COLUMN_TIME, time);
        contentValues.put(COLUMN_DISTANCE, distance);
        contentValues.put(COLUMN_CALENDAR, calendar);
        contentValues.put(COLUMN_LOCATION, location);

        sqLiteDatabase.insert(TABLE_NAME,null , contentValues);

    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sSQL = "SELECT * FROM " + TABLE_NAME;
        Cursor data = sqLiteDatabase.rawQuery(sSQL, null);
        return data;

    }



}
