package ru.vadim.hostel.consumers.events.SaveUtils;


import ru.vadim.hostel.consumers.events.Events;

import java.io.Serializable;

public class SaveEvent implements Serializable {

    private Events events;
    private Object object;


    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
