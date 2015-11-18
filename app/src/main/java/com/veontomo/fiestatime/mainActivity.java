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

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.HolidayDBProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.fragments.AddHoliday;
import com.veontomo.fiestatime.fragments.AllHolidays;
import com.veontomo.fiestatime.views.AllHolidaysView;

public class mainActivity extends AppCompatActivity implements AddHoliday.onActions, AllHolidays.onActions {
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
    public void onHolidayAdded(Holiday h) {
        AllHolidaysView allHolidays = (AllHolidaysView) getFragmentManager().findFragmentById(R.id.act_main_all_holiday);
        allHolidays.addHoliday(h);

    }

    @Override
    public void onHolidayClicked(Holiday h) {
        AddHoliday addHoliday = (AddHoliday) getFragmentManager().findFragmentById(R.id.act_main_add_holiday);
        Logger.log("main activity: " + h.serialize());
        addHoliday.load(h);
    }
}
