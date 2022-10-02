package com.sven.crudapp;

//import android.annotation.SuppressLint;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "data";
    public static final String COL_1 = "name";
    public static final String COL_2 = "section";
    public static final String COL_3 = "course";
    public static final String COL_4 = "year";
    public static final String COL_5 = "id";
    public static Cursor cursor = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME + " ("+ COL_5 + " INTEGER PRIMARY KEY, " +
                COL_1 +" TEXT, " + COL_2 +" TEXT, " + COL_3 +" TEXT, " + COL_4 + " TEXT) ");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean createData(String Name, String Section, String Course, String Year){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Name);
        contentValues.put(COL_2, Section);
        contentValues.put(COL_3, Course);
        contentValues.put(COL_4, Year);
        long results = sqLiteDatabase.insert(TABLE_NAME,null , contentValues);
        return results != -1;
    }

    public boolean updateData(String Name, String Section, String Course, String Year){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Name);
        contentValues.put(COL_2, Section);
        contentValues.put(COL_3, Course);
        contentValues.put(COL_4, Year);
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE name=?", new String[]{Name});
        if (cursor.getCount() > 0){
            long results = sqLiteDatabase.update(TABLE_NAME, contentValues, "name=?", new String[]{Name});
            return results !=-1;
        }else {
            return false;
        }
    }

    public boolean deleteData(String Name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE name=?", new String[]{Name});
        if (cursor.getCount() > 0){
            long results = sqLiteDatabase.delete(TABLE_NAME, "name=?", new String[]{Name});
            return results !=-1;
        }else {
            return false;
        }
    }

    public Cursor readData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

}
