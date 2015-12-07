package com.veontomo.fiestatime.api;

import android.test.AndroidTestCase;


/**
 *
 *
 */
public class StorageTest extends AndroidTestCase {

    private Storage storage;

    public void setUp() throws Exception {
        super.setUp();
        storage = new Storage(getContext());
    }

    public void tearDown() throws Exception {

    }

    public void testSaveSingleEvent(){
        SingleEvent e = new SingleEvent("single event", 1234567000L);
        long id = storage.save(e);
        assertTrue(id != -1);
    }


}