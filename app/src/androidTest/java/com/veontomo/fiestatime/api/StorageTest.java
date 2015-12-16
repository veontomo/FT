package com.veontomo.fiestatime.api;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

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
        Event e = new SingleEvent("Conference", 3489547858L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
        SingleEvent e2 = (SingleEvent) storage.getEventById(id);
        assertThat(e2).isNotNull();
        assertThat(e2.getName()).isEqualTo("Conference");
        assertThat(e2.getNextOccurrence()).isEqualTo(3489547858L);
        assertThat(e2.getId()).isEqualTo(id);
    }

    public void testRestoreSingleEventByIdIfNotExists() {
        Event e2 = storage.getEventById(123456);
        assertThat(e2).isNull();
    }

    public void testRestoreWeekEventByIdIfExists(){
        Event e = new WeekEvent("week seminar", 3489547858L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
        WeekEvent e2 = (WeekEvent) storage.getEventById(id);
        assertThat(e2).isNotNull();
        assertThat(e2.getName()).isEqualTo("week seminar");
        assertThat(e2.getNextOccurrence()).isEqualTo(3489547858L);
        assertThat(e2.getId()).isEqualTo(id);
    }

    public void testRestoreWeekEventByIdIfNotExists() {
        WeekEvent e2 = (WeekEvent) storage.getEventById(123456);
        assertThat(e2).isNull();
    }


    public void testRetrieveAllEventsIfEmpty(){
        List<Event> events = storage.getEvents();
        assertThat(events).isEmpty();
    }


    public void testRetrieveAllEventIfOnlyOneEventIsPresent(){
        SingleEvent e = new SingleEvent("single event", 8888L);
        storage.save(e);
        List<Event> events = storage.getEvents();
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getName()).isEqualTo("single event");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(8888L);
    }

    public void testRetrieveAllEventIfTwoEventsArePresent(){
        SingleEvent e1 = new SingleEvent("second event", 654321L);
        SingleEvent e2 = new SingleEvent("first event", 123456L);
        storage.save(e1);
        storage.save(e2);
        List<Event> events = storage.getEvents();
        assertThat(events).hasSize(2);
        assertThat(events.get(0).getName()).isEqualTo("first event");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(123456L);
        assertThat(events.get(1).getName()).isEqualTo("second event");
        assertThat(events.get(1).getNextOccurrence()).isEqualTo(654321L);
    }


    public void testUpdateEventWithoutChangeOfItsType(){
        // create an event and save it
        SingleEvent e = new SingleEvent("an event", 654321L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
        // restore the event by its id
        Event event = storage.getEventById(id);
        // change event parameters
        event.name = "another name";
        event.nextOccurrence = 11111L;
        // update the corresponding record in the storage
        long id2 = storage.save(event);
        assertThat(id2).isEqualTo(id);
        // retrieve the event in order to check its parameters
        Event event2 = storage.getEventById(id);
        assertThat(event2).isNotNull();
        assertThat(event2.getName()).isEqualTo("another name");
        assertThat(event2.getNextOccurrence()).isEqualTo(11111L);
    }

    public void testUpdateEventChangingItsType(){
        // create an event and save it
        SingleEvent e = new SingleEvent("an event", 654321L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
        // create an event of another type but with previous event id
        WeekEvent event = new WeekEvent(id, "new week event", 12345L);
        // updating the event
        long id2 = storage.save(event);
        assertThat(id2).isEqualTo(id);
        // retrieve the event in order to check its parameters
        WeekEvent event2 = (WeekEvent) storage.getEventById(id);
        assertThat(event2).isNotNull();
        assertThat(event2.getName()).isEqualTo("new week event");
        assertThat(event2.getNextOccurrence()).isEqualTo(12345L);
    }

    public void testGetNearestEventsFromEmptyStorage() {
        List<Event> events = storage.getNearest(123L);
        assertThat(events).isEmpty();
    }

    public void testGetNearestEventsSingleEvent() {
        Event e1 = new SingleEvent("event 1", 11111L);
        storage.save(e1);
        List<Event> events = storage.getNearest(123L);
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getName()).isEqualTo("event 1");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(11111L);
    }

    public void testGetNearestEventsThreeSimultaneous() {
        Event e1 = new SingleEvent("event 1", 11111L);
        Event e2 = new SingleEvent("event 2", 11111L);
        storage.save(e1);
        storage.save(e2);
        List<Event> events = storage.getNearest(1L);
        assertThat(events).hasSize(2);
        assertThat(events.get(0).getName()).isEqualTo("event 1");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(11111L);
        assertThat(events.get(1).getName()).isEqualTo("event 2");
        assertThat(events.get(1).getNextOccurrence()).isEqualTo(11111L);
    }

    public void testGetNearestEventsIfThereIsNoEventsAfterGivenTime() {
        Event e1 = new SingleEvent("event 1", 11111L);
        Event e2 = new SingleEvent("event 2", 22222L);
        storage.save(e1);
        storage.save(e2);
        List<Event> events = storage.getNearest(33333L);
        assertThat(events).isEmpty();
    }

    public void testGetNearestEventsIfAllEventsComeAfterGivenTime() {
        Event e1 = new SingleEvent("event 1", 11111L);
        Event e2 = new SingleEvent("event 2", 22222L);
        storage.save(e1);
        storage.save(e2);
        List<Event> events = storage.getNearest(1L);
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getName()).isEqualTo("event 1");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(11111L);
    }

    public void testGetNearestEventsIfTwoEventsOccurAtTheSameTime() {
        Event e1 = new SingleEvent("event 1", 10L);
        Event e2 = new SingleEvent("event 2", 20L);
        Event e3 = new SingleEvent("event 3", 30L);
        Event e4 = new SingleEvent("event 4", 30L);
        Event e5 = new SingleEvent("event 5", 40L);
        Event e6 = new SingleEvent("event 6", 40L);
        storage.save(e1);
        storage.save(e2);
        storage.save(e3);
        storage.save(e4);
        storage.save(e5);
        storage.save(e6);
        List<Event> events = storage.getNearest(25L);
        assertThat(events).hasSize(2);
        assertThat(events.get(0).getName()).isEqualTo("event 3");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(30L);
        assertThat(events.get(1).getName()).isEqualTo("event 4");
        assertThat(events.get(1).getNextOccurrence()).isEqualTo(30L);
    }

    public void testGetEventsBeforeIfStorageIsEmpty(){
        List<Event> events = storage.getEventsBefore(888999000L);
        assertThat(events).isEmpty();
    }

    public void testGetEventsBeforeIfThereIsNoEventsBeforeGivenTime() {
        Event e1 = new SingleEvent("event 1", 56789000L);
        Event e2 = new SingleEvent("event 2", 78970000L);
        storage.save(e1);
        storage.save(e2);
        List<Event> events = storage.getEventsBefore(33333L);
        assertThat(events).isEmpty();
    }

    public void testGetEventsBeforeIfJustOneEventOccursEarlier() {
        Event e1 = new SingleEvent("event 1", 10L);
        Event e2 = new SingleEvent("event 2", 20L);
        Event e3 = new SingleEvent("event 3", 30L);
        storage.save(e1);
        storage.save(e2);
        storage.save(e3);
        List<Event> events = storage.getEventsBefore(15L);
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getName()).isEqualTo("event 1");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(10L);
    }

    public void testGetEventsBeforeIfThreeEventsOccurEarlier() {
        Event e1 = new SingleEvent("event 1", 10L);
        Event e2 = new SingleEvent("event 2", 20L);
        Event e3 = new SingleEvent("event 3", 30L);
        Event e4 = new SingleEvent("event 4", 40L);
        Event e5 = new SingleEvent("event 5", 50L);
        storage.save(e1);
        storage.save(e2);
        storage.save(e3);
        storage.save(e4);
        storage.save(e5);
        List<Event> events = storage.getEventsBefore(35L);
        assertThat(events).hasSize(3);
        assertThat(events.get(0).getName()).isEqualTo("event 1");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(10L);
        assertThat(events.get(1).getName()).isEqualTo("event 2");
        assertThat(events.get(1).getNextOccurrence()).isEqualTo(20L);
        assertThat(events.get(2).getName()).isEqualTo("event 3");
        assertThat(events.get(2).getNextOccurrence()).isEqualTo(30L);
    }




}