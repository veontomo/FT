package com.veontomo.fiestatime;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.RetrieveFromCalendarTask;
import com.veontomo.fiestatime.fragments.ManageEvent;
import com.veontomo.fiestatime.fragments.MultiEvents;
import com.veontomo.fiestatime.views.MultiEventView;

public class mainActivity extends AppCompatActivity implements ManageEvent.OnActions, MultiEvents.OnActions {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TypefaceProvider.registerDefaultIconSets();
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

        if (id == R.id.action_load_from_calendar) {
            Logger.log("loading from the calendar");
            RetrieveFromCalendarTask task = new RetrieveFromCalendarTask(getApplicationContext(), this);
            task.execute();
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

        Intent intent = new Intent(getApplicationContext(), CountdownWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
    }

    @Override
    public void onEventClicked(Event h) {
        ManageEvent manageEvent = (ManageEvent) getFragmentManager().findFragmentById(R.id.act_main_add_event);
        Logger.log("main activity: " + h.serialize());
        manageEvent.load(h);
    }

    public void onEventsImported(){
        Toast.makeText(getApplicationContext(), "events are loaded", Toast.LENGTH_LONG).show();
    }
}
