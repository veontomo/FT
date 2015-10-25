package com.veontomo.fiestatime.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

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
     * Application context
     */
    private final Context mContext;


    /**
     * Constructor
     *
     * @param context application context
     * @since 0.1
     */
    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
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
         * Various Proverbs-table related queries
         */
    public static abstract class HolidayQueries {
        public static final String CREATE_TABLE = "CREATE TABLE " +
                HolidayEntry.TABLE_NAME + " (" +
                HolidayEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HolidayEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE, " +
                HolidayEntry.COLUMN_NEXT + " DATE NOT NULL, " +
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