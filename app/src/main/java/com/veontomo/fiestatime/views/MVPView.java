package com.veontomo.fiestatime.views;

/**
 * View of MVP architectural pattern
 */
public interface MVPView {

    /**
     * Fills in views (i.e., text views, image views, etc) present in current MVP-like view (that is
     * in Android-like Activity, Fragment etc)
     */
    void initializeViews();

}
