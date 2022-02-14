package ru.vadim.hostel.consumers.events.DeleteUtils;

import ru.vadim.hostel.consumers.events.Events;
import java.io.Serializable;

public class DeleteEvent implements Serializable {

    private Events events;
    private String number;


    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
