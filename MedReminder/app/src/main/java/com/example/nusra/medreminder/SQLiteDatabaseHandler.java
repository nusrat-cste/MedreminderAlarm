package com.example.nusra.medreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MedicineAlarm";
    private static final String TABLE_NAME = "AlarmTasks";
    private static final String KEY_ID = "alarmTaskId";
    private static final String KEY_NAME = "alarmTaskName";
    private static final String KEY_FREQUENCY = "alarmFrequency";
    private static final String KEY_DATE = "alarmSetDate";
    private static final String KEY_DAYS = "alarmDays";
    private static final String KEY_HOURS = "alarmSetHour";
    private static final String KEY_MINUTES = "alarmSetMinutes";
    private static final String KEY_SECONDS = "alarmSetSeconds";
    private static final String KEY_MILLIS = "alarmSetMilliSec";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_FREQUENCY,
            KEY_DATE, KEY_DAYS, KEY_HOURS, KEY_MINUTES, KEY_SECONDS, KEY_MILLIS};

    private static SQLiteDatabaseHandler db;

    public static synchronized SQLiteDatabaseHandler getHelper(Context context)
    {
        if (db == null)
            db = new SQLiteDatabaseHandler(context);
        return db;
    }

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE AlarmTasks ( "
                + "alarmTaskId INTEGER PRIMARY KEY AUTOINCREMENT, " + "alarmTaskName TEXT, "
                + "alarmFrequency TEXT, "+ "alarmSetDate TEXT, "+ "alarmDays TEXT, "+ "alarmSetHour INTEGER, "
                + "alarmSetMinutes INTEGER, "+ "alarmSetSeconds INTEGER, "+ "alarmSetMilliSec INTEGER )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // you can implement here migration process
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            this.onCreate(db);
    }

    public void deleteOne(AnAlarmTask analarmTask) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(analarmTask.getAlarmTaskId()) });
        db.close();
    }

    public AnAlarmTask getAnAlarmTask(int alarmTaskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " alarmTaskId = ?", // c. selections
                new String[] { String.valueOf(alarmTaskId) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        AnAlarmTask anAlarmTask = new AnAlarmTask();
        anAlarmTask.setAlarmTaskId(Integer.parseInt(cursor.getString(0)));
        anAlarmTask.setAlarmTaskName(cursor.getString(1));
        anAlarmTask.setAlarmFrequency(cursor.getString(2));
        anAlarmTask.setAlarmSetDate(cursor.getString(3));
        anAlarmTask.setAlarmDays(cursor.getString(4));
        anAlarmTask.setAlarmSetHour(Integer.parseInt(cursor.getString(5)));
        anAlarmTask.setAlarmSetMinutes(Integer.parseInt(cursor.getString(6)));
        anAlarmTask.setAlarmSetSeconds(Integer.parseInt(cursor.getString(7)));
        anAlarmTask.setAlarmSetMillis(Integer.parseInt(cursor.getString(8)));
        return anAlarmTask;
    }

    public ArrayList<AnAlarmTask> getAllAlarmTasks() {

        ArrayList<AnAlarmTask> allAlarmTasks = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        AnAlarmTask anAlarmTask = null;

        if (cursor.moveToFirst()) {
            do {
                anAlarmTask = new AnAlarmTask();
                anAlarmTask.setAlarmTaskId(Integer.parseInt(cursor.getString(0)));
                anAlarmTask.setAlarmTaskName(cursor.getString(1));
                anAlarmTask.setAlarmFrequency(cursor.getString(2));
                anAlarmTask.setAlarmSetDate(cursor.getString(3));
                anAlarmTask.setAlarmDays(cursor.getString(4));
                anAlarmTask.setAlarmSetHour(Integer.parseInt(cursor.getString(5)));
                anAlarmTask.setAlarmSetMinutes(Integer.parseInt(cursor.getString(6)));
                anAlarmTask.setAlarmSetSeconds(Integer.parseInt(cursor.getString(7)));
                anAlarmTask.setAlarmSetMillis(Integer.parseInt(cursor.getString(8)));

                allAlarmTasks.add(anAlarmTask);
            } while (cursor.moveToNext());
        }
        return allAlarmTasks;
    }

    public void addAnAlarmTask(AnAlarmTask alarmTask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmTask.getAlarmTaskName());
        values.put(KEY_FREQUENCY, alarmTask.getAlarmFrequency());
        values.put(KEY_DATE, alarmTask.getAlarmSetDate());
        values.put(KEY_DAYS, alarmTask.getAlarmDays());
        values.put(KEY_HOURS, alarmTask.getAlarmSetHour());
        values.put(KEY_MINUTES, alarmTask.getAlarmSetMinutes());
        values.put(KEY_SECONDS, alarmTask.getAlarmSetSeconds());
        values.put(KEY_MILLIS, alarmTask.getAlarmSetMilliSec());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

//    public synchronized SQLiteDatabase getDBInstance(Context mContext) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        if (db == null) {
//            Log.e("Data","DB already exists");
//            SQLiteDatabaseHandler mdb = new SQLiteDatabaseHandler(mContext);
//            SQLiteDatabase mDB = this.getWritableDatabase();
//            db = mDB;
//        }
//        return db;
//    }
}
