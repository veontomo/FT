package com.veontomo.fiestatime.views;

import android.os.Bundle;

/**
 * View of MVP architectural pattern
 */
public interface MVPView {

    /**
     * Fills in views (i.e., text views, image views, etc) present in current MVP-like view (that is
     * in Android-like Activity, Fragment etc) with data stored in corresponding presenter
     */
    void updateViews();

    /**
     * Saves the state of the view in the bundle.
     * @param b
     */
    void saveState(Bundle b);

    /**
     * Restores the state of the view from the bundle
     * @param b
     */
    void restoreState(Bundle b);

    /**
     * Displays a message by its resource id
     * @param msg string resource id
     */
    void showMessage(int msg);

}
