package com.veontomo.fiestatime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.veontomo.fiestatime.api.EventDBProvider;
import com.veontomo.fiestatime.api.Storage;
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
        this.mPresenter.setItemProvider(new EventDBProvider(new Storage(this.mContext)));
        this.mPresenter.update();
    }

    /**
     * Fills in views (i.e., text views, image views, etc) present in current MVP-like view (that is
     * in Android-like Activity, Fragment etc) with data stored in corresponding presenter
     */
    @Override
    public void updateViews() {
        int daysToNearest = mPresenter.getNearest();
        // TODO: clean it up!
        if (daysToNearest >= 0) {
            if (daysToNearest == 0){
                mRemoteViews.setTextViewText(R.id.background, mContext.getString(R.string.today));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mRemoteViews.setTextViewTextSize(R.id.background, TypedValue.COMPLEX_UNIT_SP, 25.0f);
                }
            } else if (daysToNearest == 1){
                mRemoteViews.setTextViewText(R.id.background, mContext.getString(R.string.tomorrow));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mRemoteViews.setTextViewTextSize(R.id.background, TypedValue.COMPLEX_UNIT_SP, 25.0f);
                }
            } else {
                mRemoteViews.setTextViewText(R.id.foreground, String.valueOf(daysToNearest));
            }

            if (mPresenter.getNextNearest() > 0) {
                mRemoteViews.setTextViewText(R.id.background, String.valueOf(mPresenter.getNextNearest()));
            }
            mRemoteViews.setTextViewText(R.id.widget_text, String.valueOf(mPresenter.getDescription()));
        }

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
        showMessage(R.string.widget_removed);
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
     * Displays a message by its resource id
     *
     * @param msg string resource id
     */
    @Override
    public void showMessage(int msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
