package com.veontomo.fiestatime.api;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import static org.assertj.core.api.Assertions.*;


/**
 *
 *
 */
public class StorageTest extends AndroidTestCase {

    private Storage storage;

    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        storage = new Storage(context);
    }

    public void tearDown() throws Exception {
        storage.close();
        storage = null;
        super.tearDown();
    }

    public void testSaveSingleEvent(){
        SingleEvent e = new SingleEvent("single event", 1234567000L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
    }


    public void testSaveTwoEventsWithTheSameName(){
        String name = "plain event";
        SingleEvent e1 = new SingleEvent(name, 9934567000L);
        SingleEvent e2 = new SingleEvent(name, 4454567000L);
        long id1 = storage.save(e1);
        long id2 = storage.save(e2);
        assertThat(id1).isNotEqualTo(-1);
        assertThat(id2).isNotEqualTo(-1);
    }

    public void testSaveTheSameEventTwice(){
        Event e = new SingleEvent("an event", 3489547858L);
        long id1 = storage.save(e);
        long id2 = storage.save(e);
        assertThat(id1).isNotEqualTo(-1);
        assertThat(id2).isEqualTo(-1);
    }

    public void testRestoreSingleEventByIdIfExists(){
        Event e = new SingleEvent("seminar", 3489547858L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
        Event e2 = storage.getEventById(id);
        assertThat(e2).isNotNull();
        assertThat(e2.getName()).isEqualTo("seminar");
        assertThat(e2.getNextOccurrence()).isEqualTo(3489547858L);
        assertThat(e2.getId()).isEqualTo(id);
    }

    public void testRestoreSingleEventByIdIfNotExists() {
        Event e2 = storage.getEventById(123456);
        assertThat(e2).isNull();

    }

}