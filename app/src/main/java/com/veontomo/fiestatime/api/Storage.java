package com.veontomo.fiestatime.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs operations with saving and retrieving holidays from database.
 */
public class Storage extends SQLiteOpenHelper {
    /**
     * current version of the database
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Name of database that contains tables of the application
     */
    private static final String DATABASE_NAME = "Holidays";



    /**
     * Constructor
     *
     * @param context application context
     * @since 0.1
     */
    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates database.
     * <p>
     * This code is called if the database has not been created yet.
     * </p>
     *
     * @param db reference to database
     * @since 0.1
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // version number 1
        db.execSQL(HolidayQueries.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            // empty task: nothing to do
        }

    }

    /**
     * Saves the holiday.
     * <p/>
     * Returns id of the record that corresponds to the holiday, or -1 in case of failure.
     *
     * @param holiday
     * @return id of the record or -1
     */
    public long save(Holiday holiday) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values;
        long id;
        values = new ContentValues();
        values.put(HolidayEntry.COLUMN_NAME, holiday.name);
        values.put(HolidayEntry.COLUMN_NEXT, holiday.nextOccurrence);
        values.put(HolidayEntry.COLUMN_PERIODICITY, holiday.periodicity);
        id = db.insert(HolidayEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Returns a list of holidays that are present in the storage in chronological order
     * (the first list elements corresponds to a holiday that occurs first, etc)
     */
    public List<Holiday> getHolidays() {
        String query = "SELECT * FROM " + HolidayEntry.TABLE_NAME + " ORDER BY " + HolidayEntry.COLUMN_NEXT + " ASC";
        return getHolidaysByQuery(query, null);
    }

    /**
     * Execute given query against the database
     */
    private List<Holiday> getHolidaysByQuery(String query, String[] args) {
        SQLiteDatabase db = getReadableDatabase();
        List<Holiday> items = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, args);
        int columnID = cursor.getColumnIndex(HolidayEntry._ID);
        int columnName = cursor.getColumnIndex(HolidayEntry.COLUMN_NAME);
        int columnNext = cursor.getColumnIndex(HolidayEntry.COLUMN_NEXT);
        int columnPeriod = cursor.getColumnIndex(HolidayEntry.COLUMN_PERIODICITY);

        if (cursor.moveToFirst()) {
            Holiday item;
            int id;
            do {
                id = (int) cursor.getLong(columnID);
                item = new Holiday(id, cursor.getString(columnName), cursor.getLong(columnNext), cursor.getInt(columnPeriod));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    /**
     * Returns the nearest holiday that occurs after given time
     * @param time time in milliseconds
     * @return
     */
    public Holiday getNearest(long time) {
        String query = "SELECT * FROM " + HolidayEntry.TABLE_NAME + " WHERE "  + HolidayEntry.COLUMN_NEXT + " > ? ORDER BY " + HolidayEntry.COLUMN_NEXT + " ASC LIMIT 1";
        List<Holiday> first = getHolidaysByQuery(query, new String[]{String.valueOf(time)});
        if (first != null && first.size() > 0){
            return first.get(0);
        }
        return null;
    }

    /**
     * Returns holidays that turn out to be before the given time
     * @param time time in milliseconds
     */
    public List<Holiday> getHolidaysBefore(long time) {
        String query = "SELECT * FROM " + HolidayEntry.TABLE_NAME + " WHERE " + HolidayEntry.COLUMN_NEXT + " < ?";
        return getHolidaysByQuery(query, new String[]{String.valueOf(time)});
    }

    /**
     * Updates a record that is already present in the storage
     * @param item
     */
    public boolean update(Holiday item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values;
        int rows;
        values = new ContentValues();
        values.put(HolidayEntry.COLUMN_NAME, item.name);
        values.put(HolidayEntry.COLUMN_NEXT, item.nextOccurrence);
        values.put(HolidayEntry.COLUMN_PERIODICITY, item.periodicity);
        rows = db.update(HolidayEntry.TABLE_NAME, values, HolidayEntry._ID + " = ?", new String[]{String.valueOf(item.id)});
        db.close();
        return rows == 1;

    }


    /**
     * Various Proverbs-table related queries
     */
    public static abstract class HolidayQueries {
        public static final String CREATE_TABLE = "CREATE TABLE " +
                HolidayEntry.TABLE_NAME + " (" +
                HolidayEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HolidayEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE, " +
                HolidayEntry.COLUMN_NEXT + " INTEGER NOT NULL, " +
                HolidayEntry.COLUMN_PERIODICITY + " INT "
                + ")";
    }

    public static abstract class HolidayEntry implements BaseColumns {
        public static final String TABLE_NAME = "Holidays";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NEXT = "nextOccurrence";
        public static final String COLUMN_PERIODICITY = "periodicity";
    }
}