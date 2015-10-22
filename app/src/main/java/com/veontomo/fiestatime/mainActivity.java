package com.veontomo.fiestatime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;

import java.io.IOException;
import java.util.ArrayList;

public class mainActivity extends AppCompatActivity {

//    private GoogleAccountCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(Config.APP_NAME, "mainActivity onCreate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        com.google.api.services.calendar.Calendar client = null;
//        ArrayList l = new ArrayList<String>();
//        l.add(CalendarScopes.CALENDAR);
//        credential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), l);
//        credential.setSelectedAccountName("Ukraine");
//        client = getCalendarService(credential);
//        String pageToken = null;
//        com.google.api.services.calendar.model.Events events;
//        do {
//            try {
//                events = client.events().list("en.usa#holiday@group.v.calendar.google.com").setPageToken(pageToken).execute();
//                pageToken = events.getNextPageToken();
//                java.util.List<com.google.api.services.calendar.model.Event> list = events.getItems();
//                Log.i(Config.APP_NAME, "event list contains " + list.size() + " elements");
//                for (com.google.api.services.calendar.model.Event event : list) {
//                    Log.i(Config.APP_NAME, "description: " + event.getDescription());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } while (pageToken != null);


    }

//    private com.google.api.services.calendar.Calendar getCalendarService(GoogleAccountCredential credential) {
//        return new com.google.api.services.calendar.Calendar.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
