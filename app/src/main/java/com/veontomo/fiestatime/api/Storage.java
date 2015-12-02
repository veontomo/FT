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
 * Performs operations with saving and retrieving mEvents from database.
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
     * Saves the event.
     * <p/>
     * Returns id of the record that corresponds to the event, or -1 in case of failure.
     *
     * @param event
     * @return id of the record or -1
     */
    public long save(Event event) {
        SQLiteDatabase db = getWritableDatabase();
        EventFactory factory = new EventFactory();
        int periodicity = factory.indexOf(event.getClass().getCanonicalName());
        ContentValues values;
        long id;
        values = new ContentValues();
        values.put(HolidayEntry.COLUMN_NAME, event.name);
        values.put(HolidayEntry.COLUMN_NEXT, event.nextOccurrence);
        values.put(HolidayEntry.COLUMN_PERIODICITY, periodicity);
        id = db.insert(HolidayEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Returns a list of mEvents that are present in the storage in chronological order
     * (the first list elements corresponds to a holiday that occurs first, etc)
     */
    public List<Event> getHolidays() {
        String query = "SELECT * FROM " + HolidayEntry.TABLE_NAME + " ORDER BY " + HolidayEntry.COLUMN_NEXT + " ASC";
        return getHolidaysByQuery(query, null);
    }

    /**
     * Execute given query against the database
     */
    private List<Event> getHolidaysByQuery(String query, String[] args) {
        EventFactory factory = new EventFactory();
        SQLiteDatabase db = getReadableDatabase();
        List<Event> items = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, args);
        int columnID = cursor.getColumnIndex(HolidayEntry._ID);
        int columnName = cursor.getColumnIndex(HolidayEntry.COLUMN_NAME);
        int columnNext = cursor.getColumnIndex(HolidayEntry.COLUMN_NEXT);
        int columnPeriod = cursor.getColumnIndex(HolidayEntry.COLUMN_PERIODICITY);

        if (cursor.moveToFirst()) {
            Event item;
            int id;
            do {
                id = (int) cursor.getLong(columnID);
                item = factory.produce(cursor.getInt(columnPeriod), id, cursor.getString(columnName), cursor.getLong(columnNext));
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
    public Event getNearest(long time) {
        String query = "SELECT * FROM " + HolidayEntry.TABLE_NAME + " WHERE "  + HolidayEntry.COLUMN_NEXT + " > ? ORDER BY " + HolidayEntry.COLUMN_NEXT + " ASC LIMIT 1";
        List<Event> first = getHolidaysByQuery(query, new String[]{String.valueOf(time)});
        if (first != null && first.size() > 0){
            return first.get(0);
        }
        return null;
    }

    /**
     * Returns mEvents that turn out to be before the given time
     * @param time time in milliseconds
     */
    public List<Event> getHolidaysBefore(long time) {
        String query = "SELECT * FROM " + HolidayEntry.TABLE_NAME + " WHERE " + HolidayEntry.COLUMN_NEXT + " < ?";
        return getHolidaysByQuery(query, new String[]{String.valueOf(time)});
    }

    /**
     * Updates a record that is already present in the storage
     * @param item
     */
    public boolean update(Event item) {
        EventFactory factory = new EventFactory();
        int periodicity = factory.indexOf(item.getClass().getCanonicalName());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values;
        int rows;
        values = new ContentValues();
        values.put(HolidayEntry.COLUMN_NAME, item.name);
        values.put(HolidayEntry.COLUMN_NEXT, item.nextOccurrence);
        values.put(HolidayEntry.COLUMN_PERIODICITY, periodicity);
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