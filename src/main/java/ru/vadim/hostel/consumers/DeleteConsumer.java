package ru.vadim.hostel.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.vadim.hostel.consumers.events.DeleteUtils.DeleteEvent;
import ru.vadim.hostel.consumers.events.Events;
import ru.vadim.hostel.consumers.events.SaveUtils.SaveEvent;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.CategoryService;
import ru.vadim.hostel.service.GuestService;

@Component
@RequiredArgsConstructor
public class DeleteConsumer {

    private final ApartmentService service;
    private final CategoryService categoryService;
    private final GuestService guestService;
    private final ObjectMapper mapper;

    @JmsListener(destination = "deleteTopic")
    public void delete(String message) {
        try {
            DeleteEvent deleteMessage = mapper.readValue(message, DeleteEvent.class);
            Events event = deleteMessage.getEvents();
            switch (event) {
                case APARTMENT -> service.delete(Long.parseLong(deleteMessage.getNumber()));
                case CATEGORY -> categoryService.deleteCategory(Long.parseLong(deleteMessage.getNumber()));
                case GUEST -> guestService.delete(deleteMessage.getNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}