package com.veontomo.fiestatime.views;

import android.os.Bundle;

/**
 * View of MVP architectural pattern
 */
public interface MVPView {

    /**
     * Fills in views (i.e., text views, image views, etc) present in current MVP-like view (that is
     * in Android-like Activity, Fragment etc)
     */
    void onLoadFields();

    /**
     * Saves the state of the view in the bundle.
     * @param b
     */
    void onSaveState(Bundle b);

    /**
     * Restores the state of the view from the bundle
     * @param b
     */
    void onRestoreState(Bundle b);

}
