package ru.vadim.hostel.consumer.events.SaveUtil;

import lombok.Data;
import ru.vadim.hostel.consumer.events.Events;

@Data
public class SaveEvent {
    private Events events;
    private Object object;
}
