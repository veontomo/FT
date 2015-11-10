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


    @Override
    public void loadInto(AllHolidaysPresenter presenter) {
        HolidayLoader loader = new HolidayLoader(this.mStorage, presenter);
        loader.execute();

    }
}
