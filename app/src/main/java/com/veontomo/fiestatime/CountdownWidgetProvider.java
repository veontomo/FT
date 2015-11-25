package com.veontomo.fiestatime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.veontomo.fiestatime.views.MVPView;

/**
 * Example of widget from
 * http://www.vogella.com/tutorials/AndroidWidgets/article.html
 */
public class CountdownWidgetProvider extends AppWidgetProvider implements MVPView {

    private final WidgetPresenter mPresenter = new WidgetPresenter(this);

    private RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                CountdownWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        Log.i(Config.APP_NAME, "size of appWidgetIds = " + String.valueOf(appWidgetIds.length));
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        for (int widgetId : allWidgetIds) {
            Log.i(Config.APP_NAME, "widgetId = " + String.valueOf(widgetId));
            // create some random data
            mPresenter.update();
            updateView();



            // Register an onClickListener
            Intent intent = new Intent(context, CountdownWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    /**
     * Updates the views of the widget.
     */
    private void updateView() {
        remoteViews.setTextViewText(R.id.update, String.valueOf(mPresenter.getNearest()));
        remoteViews.setTextViewText(R.id.afternext, String.valueOf(mPresenter.getAfterNearest()));
        remoteViews.setTextViewText(R.id.widget_text, String.valueOf(mPresenter.getDescription()));

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.i(Config.APP_NAME, "widget is deleted");
    }

    /**
     * Fills in views (i.e., text views, image views, etc) present in current MVP-like view (that is
     * in Android-like Activity, Fragment etc) with data stored in corresponding presenter
     */
    @Override
    public void updateViews() {
        // TODO
    }

    /**
     * Saves the state of the view in the bundle.
     *
     * @param b
     */
    @Override
    public void saveState(Bundle b) {
        // TODO
    }

    /**
     * Restores the state of the view from the bundle
     *
     * @param b
     */
    @Override
    public void restoreState(Bundle b) {

    }

    /**
     * Displays a short message
     *
     * @param msg
     */
    @Override
    public void showMessage(String msg) {

    }
}
