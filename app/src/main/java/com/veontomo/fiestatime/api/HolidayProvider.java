package com.veontomo.fiestatime.api;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.veontomo.fiestatime.Config;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Retrieve, finds out information about a holiday
 *
 */
public class HolidayProvider {

    private GoogleAccountCredential credential;


    public Holiday next(Context context){
        return new Holiday("A holiday", System.currentTimeMillis() + 1000*60*10);
    }

    private com.google.api.services.calendar.Calendar getCalendarService(GoogleAccountCredential credential) {
        return new com.google.api.services.calendar.Calendar.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
    }


}
