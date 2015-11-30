package com.veontomo.fiestatime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.veontomo.fiestatime.presenters.WidgetPresenter;
import com.veontomo.fiestatime.views.MVPView;

/**
 * Example of widget from
 * http://www.vogella.com/tutorials/AndroidWidgets/article.html
 */
public class CountdownWidgetProvider extends AppWidgetProvider implements MVPView {

    private final WidgetPresenter mPresenter = new WidgetPresenter(this);

    private RemoteViews mRemoteViews;

    private AppWidgetManager mWidgetManager;

    /**
     * Set of widget ids which views should be updated
     */
    private int[] mWidgetIds;

    private Context mContext;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        this.mWidgetManager = appWidgetManager;
        this.mWidgetIds = appWidgetIds;
        this.mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        this.mContext = context;
        this.mPresenter.update();
        updateViews();
    }

    /**
     * Fills in views (i.e., text views, image views, etc) present in current MVP-like view (that is
     * in Android-like Activity, Fragment etc) with data stored in corresponding presenter
     */
    @Override
    public void updateViews() {
        mRemoteViews.setTextViewText(R.id.foreground, String.valueOf(mPresenter.getNearest()));
        mRemoteViews.setTextViewText(R.id.background, String.valueOf(mPresenter.getAfterNearest()));
        mRemoteViews.setTextViewText(R.id.widget_text, String.valueOf(mPresenter.getDescription()));

        for (int widgetId : mWidgetIds) {
            Intent intent = new Intent(mContext, CountdownWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, mWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mRemoteViews.setOnClickPendingIntent(R.id.foreground, pendingIntent);
            mWidgetManager.updateAppWidget(widgetId, mRemoteViews);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        this.mContext = context;
        showMessage(context.getString(R.string.widget_removed));
        super.onDeleted(context, appWidgetIds);
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
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
