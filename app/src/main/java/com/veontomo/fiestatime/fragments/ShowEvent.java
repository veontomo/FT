package com.veontomo.fiestatime.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowEventActions} interface
 * to handle interaction events.
 * Use the {@link ShowEvent#newInstance} factory method to
 * produce an instance of this fragment.
 */
public class ShowEvent extends Fragment {
    private static final String NAME_TOKEN = "name";
    private static final String DESCR_TOKEN = "descr";


    /**
     * Event's name
     */
    @NonNull
    private String mName;

    /**
     * Event description
     */
    private String mDescription;

    /**
     * next nearest occurrence of the holiday
     */
    private Date mNextOccurrence;

    /**
     * Activity that hosts this fragment being cast to {@link ShowEventActions} interface.
     * Might be null, if the activity does not implement the above interface.
     */
    @Nullable
    private ShowEventActions mHostActivity;

    public ShowEvent() {
        // Required empty public constructor
    }

    /**
     * Factory method that creates a new instance of
     * this fragment using the provided parameters.
     *
     * @param name  holiday name
     * @param descr holiday description.
     * @return A new instance of fragment ShowEvent.
     */
    public static ShowEvent newInstance(String name, String descr) {
        ShowEvent fragment = new ShowEvent();
        Bundle args = new Bundle();
        args.putString(NAME_TOKEN, name);
        args.putString(DESCR_TOKEN, descr);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes fragment's fields.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = savedInstanceState;
        if (b == null) {
            b = getArguments();
        }
        if (b != null) {
            String rawName = b.getString(NAME_TOKEN);
            mName = rawName == null ? "no name" : rawName;
            mDescription = b.getString(DESCR_TOKEN);
            try {
                mHostActivity = (ShowEventActions) getActivity();
            } catch (ClassCastException e) {
                Logger.log("hosting activity does not support ShowEventActions interface");
                mHostActivity = null;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_event, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mHostActivity = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface ShowEventActions {
        void onFragmentInteraction();
    }

}
