package ru.vadim.hostel.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.vadim.hostel.consumer.events.DeleteEvent.DeleteEvent;
import ru.vadim.hostel.consumer.events.Events;
import ru.vadim.hostel.consumer.events.SaveUtil.SaveEvent;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.CategoryService;
import ru.vadim.hostel.service.GuestService;

import java.io.StringReader;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final ApartmentService service;
    private final CategoryService categoryService;
    private final GuestService guestService;
    private final ObjectMapper mapper;

    @JmsListener(destination = "bridgingcode-queue")
    public void listenQueue(String message) {
        try {
            SaveEvent saveMessage = mapper.readValue(message, SaveEvent.class);
            final ObjectNode node = mapper.readValue(message, ObjectNode.class);
            String jsonObject = null;
            if(node.has("object")) {
                jsonObject = node.get("object").toString();
            }
            Events event = saveMessage.getEvents();
            switch (event) {
                case APARTMENT -> service.save(mapper.readValue(jsonObject, ApartmentDto.class));
                case CATEGORY -> categoryService.save(mapper.readValue(jsonObject, CategoryDto.class));
                case GUEST -> guestService.save(mapper.readValue(jsonObject, GuestDto.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Message received by queue consumer" + message);
    }

    @JmsListener(destination = "bridgingcode-topic")
    public void listenTopic(String message) {
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
        log.info("Message received by topic consumer " + message);
    }
}
