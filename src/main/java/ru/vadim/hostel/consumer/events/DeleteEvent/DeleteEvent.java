package ru.vadim.hostel.consumer.events.DeleteEvent;

import lombok.Data;
import ru.vadim.hostel.consumer.events.Events;

@Data
public class DeleteEvent {
    private Events events;
    private String number;
}
