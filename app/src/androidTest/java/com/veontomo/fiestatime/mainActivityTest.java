package com.veontomo.fiestatime;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by Mario Rossi on 15/12/2015 at 14:04.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public class mainActivityTest extends ActivityInstrumentationTestCase2<mainActivity> {

    private mainActivity mActivity;
    private EditText mEventField;
    private Button mConfirmButton;
    private View mAllEvents;

    /**
     * Creates an {@link ActivityInstrumentationTestCase2}.
     *
     */
    public mainActivityTest() {
        super(mainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mEventField = (EditText) mActivity.findViewById(R.id.frag_add_event_name);
        mConfirmButton = (Button) mActivity.findViewById(R.id.frag_add_event_confirm);
        mAllEvents = mActivity.findViewById(R.id.act_main_all_events);
    }

    public void testPreconditions() {
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("Edit button is not found", mEventField);
    }

    @UiThreadTest
    public void testEditButtonMustRedirectToEditPage() {
        mEventField.requestFocus();
        mEventField.setText("event");
        mConfirmButton.performClick();

    }




}