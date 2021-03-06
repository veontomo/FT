package com.veontomo.fiestatime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

//    private RemoteViews mRemoteViews;

    private AppWidgetManager mWidgetManager;

    /**
     * Set of widget ids which views should be updated
     */
    private int[] mWidgetIds;

    private Context mContext;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Logger.log("CountdownWidgetProvider: on update");
        this.mWidgetManager = appWidgetManager;
        this.mContext = context;
        this.mWidgetIds = appWidgetIds;
        this.mPresenter.setItemProvider(new EventDBProvider(new Storage(this.mContext)));
        this.mPresenter.update();


    }

    /**
     * Fills in views (i.e., name views, image views, etc) present in current MVP-like mView (that is
     * in Android-like Activity, Fragment etc) with data stored in corresponding presenter
     */
    @Override
    public void updateViews() {
        Logger.log("CountdownWidgetProvider: on update views");
        RemoteViews mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout_2);
        mRemoteViews.setTextViewText(R.id.primaryEventCountdown, mPresenter.getCountdownPhrase(mContext));
        mRemoteViews.setTextViewText(R.id.primaryEventDescr, mPresenter.getNearestEventsDescription(mContext));
        mRemoteViews.setTextViewText(R.id.secondaryEventDescr, mPresenter.getNextNearestEventsDescription(mContext));

        Intent intent = new Intent(mContext, mainActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, mWidgetIds);

        // use method getActivity (and not getBroadcast) in order to start mainActivity.
        // previously, getBroadcast() was used because a click on the widget was starting
        // CountdownWidgetProvider which after all inherits from BroadcastReceiver and not from Activity.
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.primaryEventCountdown, pendingIntent);
        mWidgetManager.updateAppWidget(mWidgetIds, mRemoteViews);
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        this.mContext = context;
        showMessage(R.string.widget_removed);
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * Saves the state of the mView in the bundle.
     *
     * @param b
     */
    @Override
    public void saveState(Bundle b) {
        // TODO
    }

    /**
     * Restores the state of the mView from the bundle
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

    /**
     * A broadcast receiver.
     * <br> If the broadcast action corresponds to an update action, then call {@link #onUpdate(Context, AppWidgetManager, int[])} method
     * with properly set arguments.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = (intent == null) ? null : intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, CountdownWidgetProvider.class);
            int[] appWidgetIds = mgr.getAppWidgetIds(cn);
            onUpdate(context, mgr, appWidgetIds);
        }
        super.onReceive(context, intent);
    }
}
