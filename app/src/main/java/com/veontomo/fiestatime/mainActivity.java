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

import com.veontomo.fiestatime.api.HolidayProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.fragments.AddHoliday;
import com.veontomo.fiestatime.fragments.AllHolidays;

public class mainActivity extends AppCompatActivity  {
    /**
     * Fragment that displays holidays
     */
    private AllHolidays allHolidays;

    /**
     * Storage by means of which the holidays are saved and retrieved
     */
    private Storage storage;

    private HolidayProvider hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(Config.APP_NAME, "mainActivity onCreate");
        storage = new Storage(getApplicationContext());
        hp = new HolidayProvider(storage);

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
        allHolidays = (AllHolidays) getFragmentManager().findFragmentById(R.id.act_main_all_holiday);
        HolidayLoader loader = new HolidayLoader(allHolidays, storage);
        loader.execute();

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

}
