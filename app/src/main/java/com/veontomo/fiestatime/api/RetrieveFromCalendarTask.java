package com.veontomo.fiestatime.api;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.mainActivity;

/**
 * Retrieves the events from the calendar and stores them into the storage
 */
public class RetrieveFromCalendarTask extends AsyncTask<Void, Float, Void> {

    private final Context mContext;
    private final mainActivity mCaller;

    public RetrieveFromCalendarTask(Context context, mainActivity caller) {
        this.mContext = context;
        this.mCaller = caller;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Logger.log("RetrieveFromCalendarTask background");
        ContentResolver contentResolver = this.mContext.getContentResolver();
//        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
//                (new String[]{"calendar_id", "title", "description", "dtstart", "dtend", "eventTimezone", "eventLocation"}), "(" + "dtstart" + "> 0)", null, "dtstart ASC");
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
                null, "(" + "dtstart" + "> 0)", null, "dtstart ASC");
        int rows = cursor.getCount();
        int currentRowIndex = 1;
        if (cursor != null) {
            Logger.log("cursor size = " + cursor.getCount());
            int col = cursor.getColumnCount();
            StringBuilder builder;
            while (cursor.moveToNext()) {
                builder = new StringBuilder();

                for (int i = 0; i < col; i++) {
                    builder.append(cursor.getColumnName(i));
                    builder.append(": ");
                    builder.append(cursor.getString(i));
                    builder.append(" ");
                }
                builder.append(System.getProperty("line.separator", " "));
                Logger.log("title: " + cursor.getString(cursor.getColumnIndex("title")));
                Logger.log("description: " + cursor.getString(cursor.getColumnIndex("description")));
                Logger.log("dtstart: " + cursor.getString(cursor.getColumnIndex("dtstart")));
                Logger.log(builder.toString());
                publishProgress(((float) currentRowIndex)/rows);
                currentRowIndex++;
            }
            cursor.close();

        }
        return null;
    }

    protected void onProgressUpdate(Float... progress) {
        Logger.log("progress updates" + String.valueOf(progress[0]));
    }

    @Override
    public void onPostExecute(Void v){
        Logger.log("loaded");
        this.mCaller.onEventsImported();
    }

}
