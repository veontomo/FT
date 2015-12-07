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
    private final static String[] classes = new String[]{"com.veontomo.fiestatime.api.SingleEvent",
            "com.veontomo.fiestatime.api.WeekEvent",
            "com.veontomo.fiestatime.api.MonthEvent",
            "com.veontomo.fiestatime.api.YearEvent"};
    /**
     * current version of the database
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Name of database that contains tables of the application
     */
    private static final String DATABASE_NAME = "Events";


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
        String createEventTypes = "CREATE TABLE " +
                EventTypeEntry.TABLE_NAME + " (" +
                EventTypeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EventTypeEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE)";
        String createEventTable = "CREATE TABLE " +
                EventEntry.TABLE_NAME + " (" +
                EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EventEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                EventEntry.COLUMN_NEXT + " INTEGER NOT NULL, " +
                EventEntry.COLUMN_TYPE + " INT, " +
                " FOREIGN KEY(" + EventEntry.COLUMN_TYPE + ") REFERENCES " +
                EventTypeEntry.TABLE_NAME + "(" + EventTypeEntry._ID + ")" +
                ")";
        db.execSQL(createEventTypes);
        db.execSQL(createEventTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            // empty task: nothing to do
        }

    }

    /**
     * Returns index at which given class is  present in {@link #classes}.
     * <br>
     * If nothing is found, -1 is returned.
     *
     * @param name
     * @return
     */
    public int indexOf(String name) {
        int len = classes.length;
        for (int i = 0; i < len; i++) {
            if (name.equals(classes[i])) {
                return i;
            }
        }
        return -1;
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
        int periodicity = indexOf(event.getClass().getCanonicalName());
        ContentValues values;
        long id;
        values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME, event.name);
        values.put(EventEntry.COLUMN_NEXT, event.nextOccurrence);
        values.put(EventEntry.COLUMN_TYPE, periodicity);
        id = db.insert(EventEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Returns a list of mEvents that are present in the storage in chronological order
     * (the first list elements corresponds to a holiday that occurs first, etc)
     */
    public List<Event> getHolidays() {
        String query = "SELECT * FROM " + EventEntry.TABLE_NAME + " ORDER BY " + EventEntry.COLUMN_NEXT + " ASC";
        return getHolidaysByQuery(query, null);
    }

    /**
     * Execute given query against the database
     */
    private List<Event> getHolidaysByQuery(String query, String[] args) {
        Factory<Event> factory = new Factory<>();
        SQLiteDatabase db = getReadableDatabase();
        List<Event> items = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, args);
        int columnID = cursor.getColumnIndex(EventEntry._ID);
        int columnName = cursor.getColumnIndex(EventEntry.COLUMN_NAME);
        int columnNext = cursor.getColumnIndex(EventEntry.COLUMN_NEXT);
        int columnPeriod = cursor.getColumnIndex(EventEntry.COLUMN_TYPE);

        if (cursor.moveToFirst()) {
            Event item;
            int id;
            String className;
            do {
                id = (int) cursor.getLong(columnID);
                className = classes[cursor.getInt(columnPeriod)];
                item = factory.produce(className, id, cursor.getString(columnName), cursor.getLong(columnNext));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    /**
     * Returns the nearest holiday that occurs after given time
     *
     * @param time time in milliseconds
     * @return
     */
    public Event getNearest(long time) {
        String query = "SELECT * FROM " + EventEntry.TABLE_NAME + " WHERE " + EventEntry.COLUMN_NEXT + " > ? ORDER BY " + EventEntry.COLUMN_NEXT + " ASC LIMIT 1";
        List<Event> first = getHolidaysByQuery(query, new String[]{String.valueOf(time)});
        if (first != null && first.size() > 0) {
            return first.get(0);
        }
        return null;
    }

    /**
     * Returns mEvents that turn out to be before the given time
     *
     * @param time time in milliseconds
     */
    public List<Event> getHolidaysBefore(long time) {
        String query = "SELECT * FROM " + EventEntry.TABLE_NAME + " WHERE " + EventEntry.COLUMN_NEXT + " < ?";
        return getHolidaysByQuery(query, new String[]{String.valueOf(time)});
    }

    /**
     * Updates a record that is already present in the storage
     *
     * @param item
     */
    public boolean update(Event item) {
        int periodicity = indexOf(item.getClass().getCanonicalName());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values;
        int rows;
        values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME, item.name);
        values.put(EventEntry.COLUMN_NEXT, item.nextOccurrence);
        values.put(EventEntry.COLUMN_TYPE, periodicity);
        rows = db.update(EventEntry.TABLE_NAME, values, EventEntry._ID + " = ?", new String[]{String.valueOf(item.id)});
        db.close();
        return rows == 1;

    }

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Events";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NEXT = "next";
        public static final String COLUMN_TYPE = "typeId";
    }

    public static abstract class EventTypeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Types";
        public static final String COLUMN_NAME = "name";
    }
}