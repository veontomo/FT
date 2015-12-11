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
     * Factory that is able to create instances of requested classes
     */
    private final Factory<Event> factory;


    /**
     * Constructor
     *
     * @param context application context
     * @since 0.1
     */
    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        factory = new Factory<>();
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
                EventEntry.COLUMN_TYPE + " INT NOT NULL, " +
                " FOREIGN KEY(" + EventEntry.COLUMN_TYPE + ") REFERENCES " +
                EventTypeEntry.TABLE_NAME + "(" + EventTypeEntry._ID + "), " +
                "UNIQUE(" + EventEntry.COLUMN_NAME + ", " + EventEntry.COLUMN_NEXT + ", " + EventEntry.COLUMN_TYPE + ")" +
                ")";
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(createEventTypes);
        db.execSQL(createEventTable);

//        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onOpen(db);
        if (oldVersion < 1) {
            // empty task: nothing to do
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        super.onOpen(db);
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
        ContentValues values;
        long id;
        String className = event.getClass().getCanonicalName();
        short typeId = getType(db, event.getClass().getCanonicalName());
        if (typeId == -1) {
            typeId = saveType(db, className);
        }
        values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME, event.name);
        values.put(EventEntry.COLUMN_NEXT, event.nextOccurrence);
        values.put(EventEntry.COLUMN_TYPE, typeId);
        id = db.insert(EventEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Saves given class name in the table that stores the event types and
     * returns id of the corresponding record.
     * <br>
     * The number of types is supposed to be quite limited so that the
     * underlying table is supposed to have Java "short" type
     * should be enough to parametrize
     *
     * @param db
     * @param className
     * @return
     */
    private short saveType(final SQLiteDatabase db, String className) {
        ContentValues values = new ContentValues();
        values.put(EventTypeEntry.COLUMN_NAME, className);
        return (short) db.insert(EventTypeEntry.TABLE_NAME, null, values);
    }

    /**
     * Returns id under which an event of given name is stored
     *
     * @param name canonical name of the class
     */
    private short getType(final SQLiteDatabase db, String name) {
        String query = "SELECT " + EventTypeEntry._ID + " FROM " + EventTypeEntry.TABLE_NAME + " WHERE " + EventTypeEntry.COLUMN_NAME + " = ? LIMIT 1;";
        Cursor cursor = db.rawQuery(query, new String[]{name});
        short typeId = -1;
        if (cursor.getCount() == 1) {
            if (cursor.moveToFirst()) {
                typeId = cursor.getShort(cursor.getColumnIndex(EventTypeEntry._ID));
            }
        }
        cursor.close();
        return typeId;
    }


    /**
     * Returns a list of events that are present in the storage in chronological order
     * (the first list elements corresponds to a holiday that occurs first, etc)
     */
    public List<Event> getEvents() {
        String query = "SELECT " +
                EventEntry.TABLE_NAME + "." + EventEntry._ID + " AS id," +
                EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_NAME + " AS name," +
                EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_NEXT + " AS next, " +
                EventTypeEntry.TABLE_NAME + "." + EventTypeEntry.COLUMN_NAME + " AS type " +
                "FROM " + EventEntry.TABLE_NAME + ", " + EventTypeEntry.TABLE_NAME + " WHERE " +
                EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_TYPE + " = " +
                EventTypeEntry.TABLE_NAME + "." + EventTypeEntry._ID + " ORDER BY next ASC";
//        String query = "SELECT * FROM " + EventEntry.TABLE_NAME + " ORDER BY " + EventEntry.COLUMN_NEXT + " ASC";
        return getEventsByQuery(query, null);
    }

    /**
     * Execute given query against the database
     */
    private List<Event> getEventsByQuery(String query, String[] args) {
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
            String type;
            do {
                id = (int) cursor.getLong(columnID);
                type = classes[cursor.getInt(columnPeriod)];
                item = factory.produce(type, id, cursor.getString(columnName), cursor.getLong(columnNext));
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
        List<Event> first = getEventsByQuery(query, new String[]{String.valueOf(time)});
        if (first != null && first.size() > 0) {
            return first.get(0);
        }
        return null;
    }

    /**
     * Returns events that occur before the given time
     *
     * @param time time in milliseconds
     */
    public List<Event> getEventsBefore(long time) {
        String query = "SELECT * FROM " + EventEntry.TABLE_NAME + " WHERE " + EventEntry.COLUMN_NEXT + " < ?";
        return getEventsByQuery(query, new String[]{String.valueOf(time)});
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

    /**
     * Returns an event that is stored under the given id.
     *
     * @param id event id
     */
    public Event getEventById(long id) {
        String query = "SELECT " +
                EventEntry.COLUMN_NAME + ", " +
                EventEntry.COLUMN_NEXT + ", " +
                EventTypeEntry.COLUMN_NAME + " " +
                "FROM " + EventEntry.TABLE_NAME + ", " + EventTypeEntry.TABLE_NAME + " WHERE " +
                EventEntry.TABLE_NAME + "." + EventEntry._ID + " = ? AND " +
                EventEntry.COLUMN_TYPE + " = " +
                EventTypeEntry.TABLE_NAME + "." + EventTypeEntry._ID;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        Event event;
        if (cursor.getCount() != 1) {
            event = null;
        } else {
            cursor.moveToFirst();
            int nameIndex = cursor.getColumnIndex(EventEntry.COLUMN_NAME);
            int nextIndex = cursor.getColumnIndex(EventEntry.COLUMN_NEXT);
            int typeIndex = cursor.getColumnIndex(EventTypeEntry.COLUMN_NAME);

            long next = cursor.getLong(nextIndex);
            String type = cursor.getString(typeIndex);
            String name = cursor.getString(nameIndex);
            event = factory.produce(type, id, name, next);
        }
        cursor.close();
        db.close();

        return event;
    }

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Events";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NEXT = "next";
        public static final String COLUMN_TYPE = "typeId";
    }

    public static abstract class EventTypeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Types";
        public static final String COLUMN_NAME = "type";
    }
}