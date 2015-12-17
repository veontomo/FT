package com.veontomo.fiestatime;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.fragments.AddEvent;
import com.veontomo.fiestatime.fragments.AllEvents;
import com.veontomo.fiestatime.views.MultiEventView;

public class mainActivity extends AppCompatActivity implements AddEvent.onActions, AllEvents.onActions {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.log("mainActivity onCreate");

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

    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.log("mainActivity onStart");
        ContentResolver contentResolver  = getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
                (new String[]{"calendar_id", "title", "description", "dtstart", "dtend", "eventTimezone", "eventLocation"}), "(" + "dtstart" + "> 0)", null, "dtstart ASC");
        Logger.log("cursor size = " + cursor.getCount());
        while(cursor.moveToNext()){
            Logger.log("title: " + cursor.getString(cursor.getColumnIndex("title")));
            Logger.log("description: " + cursor.getString(cursor.getColumnIndex("description")));
            Logger.log("dtstart: " + cursor.getString(cursor.getColumnIndex("dtstart")));
        }

    }

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

    @Override
    public void onEventAdded(Event h) {
        MultiEventView allEvents = (MultiEventView) getFragmentManager().findFragmentById(R.id.act_main_all_events);
        allEvents.addEvent(h);



    }

    @Override
    public void onEventUpdated(Event h) {
        MultiEventView allHolidays = (MultiEventView) getFragmentManager().findFragmentById(R.id.act_main_all_events);
        allHolidays.updateEvent(h);
    }

    @Override
    public void onEventClicked(Event h) {
        AddEvent addEvent = (AddEvent) getFragmentManager().findFragmentById(R.id.act_main_add_event);
        Logger.log("main activity: " + h.serialize());
        addEvent.load(h);
    }
}
