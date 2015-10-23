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
        com.google.api.services.calendar.Calendar client = null;
        ArrayList<String> l = new ArrayList<>();
        l.add(CalendarScopes.CALENDAR);
        credential = GoogleAccountCredential.usingOAuth2(context, l);
//        credential.setSelectedAccountName(null);
        client = getCalendarService(credential);
        String pageToken = null;
        com.google.api.services.calendar.model.Events events;
        do {
            try {
                events = client.events().list("en.usa#holiday@group.v.calendar.google.com").execute();
//                events = client.events().list("en.usa#holiday@group.v.calendar.google.com").setPageToken(pageToken).execute();
                pageToken = events.getNextPageToken();
                java.util.List<com.google.api.services.calendar.model.Event> list = events.getItems();
                Log.i(Config.APP_NAME, "event list contains " + list.size() + " elements");
                for (com.google.api.services.calendar.model.Event event : list) {
                    Log.i(Config.APP_NAME, "description: " + event.getDescription());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (pageToken != null);
        return new Holiday("A holiday", System.currentTimeMillis() + 1000*60*10);
    }

    private com.google.api.services.calendar.Calendar getCalendarService(GoogleAccountCredential credential) {
        return new com.google.api.services.calendar.Calendar.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
    }


}
