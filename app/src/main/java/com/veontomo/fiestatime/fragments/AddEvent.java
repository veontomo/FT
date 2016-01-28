package com.veontomo.fiestatime.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.EventDBProvider;
import com.veontomo.fiestatime.api.MonthEvent;
import com.veontomo.fiestatime.api.SingleEvent;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.api.WeekEvent;
import com.veontomo.fiestatime.api.YearEvent;
import com.veontomo.fiestatime.presenters.AddEventPresenter;
import com.veontomo.fiestatime.views.AddEventView;


public class AddEvent extends Fragment implements AddEventView {
    private final AddEventPresenter mPresenter = new AddEventPresenter(this);
    private BootstrapEditText mEventNameView;
    private AwesomeTextView mNextOccurrenceView;
    private Spinner mPeriodicityView;
    private BootstrapButton mConfirmButton;
    private BootstrapButton mCancelButton;
    private onActions hostingActivity;

    public AddEvent() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restoreState(savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        mEventNameView = (BootstrapEditText) getActivity().findViewById(R.id.frag_add_event_name);
        mNextOccurrenceView = (AwesomeTextView) getActivity().findViewById(R.id.frag_add_holiday_next);
        mPeriodicityView = (Spinner) getActivity().findViewById(R.id.frag_add_holiday_periodicity);
        mConfirmButton = (BootstrapButton) getActivity().findViewById(R.id.frag_add_event_confirm);
        mCancelButton = (BootstrapButton) getActivity().findViewById(R.id.frag_add_holiday_cancel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.periodicity, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPeriodicityView.setAdapter(adapter);

        mPresenter.setEventTypes(new String[]{SingleEvent.class.getCanonicalName(),
                WeekEvent.class.getCanonicalName(),
                MonthEvent.class.getCanonicalName(),
                YearEvent.class.getCanonicalName()});
        // TODO: make the presenter use a task in order to load holiday info (if any) into
        // the edit view. See how it is done in {@link AllEvents#onActivityCreated}
        mPresenter.bindView(this);

        attachListeners();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hostingActivity = (onActions) getActivity();
        Storage storage = new Storage(getActivity().getApplicationContext());
        mPresenter.setEventProvider(new EventDBProvider(storage));
    }

    @Override
    public void onDestroyView() {
        detachListeners();
        mCancelButton = null;
        mConfirmButton = null;
        mPeriodicityView.setAdapter(null);
        mPeriodicityView = null;
        mEventNameView = null;
        super.onDestroyView();
    }


    private void attachListeners() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onConfirm(mEventNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
                mEventNameView.setText(null);
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onCancel(mEventNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
                mEventNameView.setText(null);
            }
        });
        mNextOccurrenceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateClick(v);

            }
        });
    }


    @Override
    public void onPause() {
        mPresenter.onPause(mEventNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
        super.onPause();

    }

    private void detachListeners() {
        mNextOccurrenceView.setOnClickListener(null);
        mCancelButton.setOnClickListener(null);
        mConfirmButton.setOnClickListener(null);
    }


    @Override
    public void onSaveInstanceState(Bundle b) {
        saveState(b);
        super.onSaveInstanceState(b);
    }


    @Override
    public void onDateClick(View v) {
        mPresenter.onDateClick(v, getActivity().getFragmentManager());
    }

    @Override
    public void setDate(String date) {
        this.mNextOccurrenceView.setText(date);
    }


    @Override
    public void onEventAdded(Event h) {
        hostingActivity.onEventAdded(h);
        mEventNameView.setText(null);
    }

    @Override
    public void onEventUpdated(Event h) {
        hostingActivity.onEventUpdated(h);
    }

    @Override
    public void load(Event h) {
        this.mPresenter.load(h);
        updateViews();
    }

    /**
     * Disables "confirm" and "cancel" buttons
     *
     * @param status true to enable buttons, false to disable them
     */
    @Override
    public void setEnableButtons(boolean status) {
        mConfirmButton.setClickable(status);
        mCancelButton.setClickable(status);
    }

    @Override
    public void updateViews() {
        String date = mPresenter.getNextOccurrence();
        String name = mPresenter.getEventName();
        if (name != null) {
            this.mEventNameView.setText(name);
        }
        if (date != null) {
            this.mNextOccurrenceView.setText(date);
        }
        this.mPeriodicityView.setSelection(mPresenter.getPeriodicity());
    }


    @Override
    public void saveState(Bundle b) {
        mPresenter.saveState(b);
    }

    @Override
    public void restoreState(Bundle b) {
        mPresenter.restoreState(b);
    }

    /**
     * Displays a short message
     *
     * @param msg
     */
    @Override
    public void showMessage(int msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public interface onActions {
        void onEventAdded(Event h);

        void onEventUpdated(Event h);
    }

}
