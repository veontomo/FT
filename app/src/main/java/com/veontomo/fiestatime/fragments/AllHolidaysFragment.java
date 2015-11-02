package com.veontomo.fiestatime.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllHolidaysFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AllHolidaysFragment extends Fragment {



    private OnFragmentInteractionListener mHostActivity;

    public AllHolidaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_holidays, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mHostActivity != null) {
            mHostActivity.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart(){
        try {
            mHostActivity = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            Logger.log("AllHolidaysFragment is embedded to an activity that does not support interaction");
            mHostActivity = null;
        }

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
