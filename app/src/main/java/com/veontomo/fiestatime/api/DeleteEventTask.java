package com.veontomo.fiestatime.api;

import android.os.AsyncTask;

import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.IProvider;
import com.veontomo.fiestatime.presenters.ManageEventPresenter;

/**
 * A task for deletion of an event with given identifier
 */
public class DeleteEventTask extends AsyncTask<Void, Void, Void> {

    /**
     * event identifier
     */
    private final long mId;

    /**
     * reference to the presenter that has requested the deletion
     */
    private final ManageEventPresenter mPresenter;

    /**
     * outcome of the deletion
     */
    private boolean isSuccessful = false;


    /**
     * Event provider
     */
    private final IProvider<Event> mProvider;

    public DeleteEventTask(long id, ManageEventPresenter addEventPresenter, IProvider<Event> provider) {
        this.mId = id;
        this.mPresenter = addEventPresenter;
        this.mProvider = provider;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(Void... params) {
        isSuccessful = mProvider.delete(mId);
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        if (isSuccessful) {
            this.mPresenter.onDeleted();
        } else {
//            this.mPresenter.onFailure();
        }
    }
}
