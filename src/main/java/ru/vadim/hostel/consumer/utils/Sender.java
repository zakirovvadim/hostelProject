package ru.vadim.hostel.consumer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import ru.vadim.hostel.consumer.events.DeleteEvent.DeleteEvent;
import ru.vadim.hostel.consumer.events.SaveUtil.SaveEvent;

@RequiredArgsConstructor
public class Sender {

    public static void deleteThrowBroker(ObjectMapper mapper, JmsTemplate jmsTemplate, DeleteEvent event) {
        try{
            jmsTemplate.convertAndSend("bridgingcode-topic",  mapper.writeValueAsString(event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveThrowBroker(ObjectMapper mapper, JmsTemplate jmsTemplate, SaveEvent event) {
        try {
            jmsTemplate.convertAndSend("bridgingcode-queue", mapper.writeValueAsString(event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
