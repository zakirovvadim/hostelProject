package ru.vadim.hostel.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import ru.vadim.hostel.consumers.events.Events;
import ru.vadim.hostel.consumers.events.SaveUtils.SaveEvent;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.CategoryService;
import ru.vadim.hostel.service.GuestService;

@RequiredArgsConstructor
public class SaveConsumer {

    private final ApartmentService service;
    private final CategoryService categoryService;
    private final GuestService guestService;
    private final ObjectMapper mapper;

    @JmsListener(destination = "saveQueue")
    public void save(String message) {
        System.out.println(message);
        mapper.registerModule(new JSR310Module());
        try {
            SaveEvent saveMessage = mapper.readValue(message, SaveEvent.class);
            Events event = saveMessage.getEvents();
            switch (event) {
                case APARTMENT -> service.save((ApartmentDto)saveMessage.getObject());
                case CATEGORY -> categoryService.save((CategoryDto)saveMessage.getObject());
                case GUEST -> guestService.save((GuestDto)saveMessage.getObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}