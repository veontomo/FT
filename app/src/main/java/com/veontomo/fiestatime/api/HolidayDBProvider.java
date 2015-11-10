package com.veontomo.fiestatime.api;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.veontomo.fiestatime.Config;
import com.veontomo.fiestatime.HolidayLoader;
import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;
import com.veontomo.fiestatime.views.MVPView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Provider of holidays: retrieves the holidays (either from internet or from calendar).
 */
public class HolidayDBProvider implements IHolidayProvider {

    private final Storage mStorage;

    public HolidayDBProvider(Storage storage){
        this.mStorage = storage;
    }

    public void save(final String name, final long next, final int periodicity){
        Logger.log("saving " + name + ", " + next + ", " + periodicity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mStorage.save(name, next, periodicity);
            }
        }).start();
    }
    /**
     * gives the next nearest holiday
     *
     * @return
     */
    public Holiday next() {
        return new Holiday("Next holiday", System.currentTimeMillis(), Holiday.WEEKLY);

    }




    public java.util.List<com.google.api.services.calendar.model.Event> events(final Context context) {
        com.google.api.services.calendar.Calendar client = null;
        ArrayList<String> l = new ArrayList<>();
        l.add(CalendarScopes.CALENDAR);
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(context, l);
        credential.setSelectedAccountName(null);
        client = new com.google.api.services.calendar.Calendar.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
        String pageToken = null;
        com.google.api.services.calendar.model.Events events;
        java.util.List<com.google.api.services.calendar.model.Event> list = null;
        do {
            try {
                events = client.events().list("en.usa#holiday@group.v.calendar.google.com").setPageToken(pageToken).execute();
                pageToken = events.getNextPageToken();
                list = events.getItems();
                Log.i(Config.APP_NAME, "event list contains " + list.size() + " elements");
                for (com.google.api.services.calendar.model.Event event : list) {
                    Log.i(Config.APP_NAME, "description: " + event.getDescription());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (pageToken != null);
        return list;
    }


    @Override
    public void loadInto(AllHolidaysPresenter presenter) {
        HolidayLoader loader = new HolidayLoader(this.mStorage, presenter);

    }
}
