package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.views.MVPView;

/**
 * Presenter of MVP architectural pattern
 */
public interface MVPPresenter {
    void bindView(final MVPView v);

    void saveState(Bundle b);

    void restoreState(Bundle b);

}
