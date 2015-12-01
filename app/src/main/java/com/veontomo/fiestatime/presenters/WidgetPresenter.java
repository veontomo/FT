package com.veontomo.fiestatime.presenters;

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.IProvider;
import com.veontomo.fiestatime.api.WidgetUpdateTask;
import com.veontomo.fiestatime.views.MVPView;

/**
 * Presenter for the countdown widget.
 */
public class WidgetPresenter {
    private final MVPView view;
    /**
     * number of days to the nearest holiday
     */
    private int nearest;

    /**
     * number of days to the holiday after the nearest
     */
    private int nextNearest;

    /**
     * String representation of the forthcoming holiday(s)
     */
    private String setDescription;
    private IProvider<Holiday> mItemProvider;

    public WidgetPresenter(MVPView view){
        this.view = view;
    }


    /**
     * Updates the presenter data
     */
    public void update() {
        WidgetUpdateTask worker = new WidgetUpdateTask(this, mItemProvider);
        worker.execute();
    }

    /**
     * Gets called once the presenter state is updated.
     * <br>
     * It gets called from {@link  WidgetUpdateTask#onPostExecute(Void)}.
     */
    public void onUpdated(){
        view.updateViews();
    }

    public int getNearest() {
        return nearest;
    }

    public int getNextNearest() {
        return nextNearest;
    }

    public String getDescription() {
        return setDescription;
    }

    public void setNearest(int nearest) {
        this.nearest = nearest;
    }

    public void setNextNearest(int nextNearest) {
        this.nextNearest = nextNearest;
    }

    public void setDescription(String text) {
        setDescription = text;
    }

    public void setItemProvider(IProvider<Holiday> itemProvider) {
        mItemProvider = itemProvider;
    }
}
