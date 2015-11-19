package com.veontomo.fiestatime.presenters;

import android.content.Context;
import android.os.Bundle;

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.views.MVPView;

import java.util.List;

/**
 * Presenter of MVP architectural pattern
 */
public interface MVPPresenter {
    void bindView(final MVPView v);

    void saveState(Bundle b);

    void restoreState(Bundle b);


}
