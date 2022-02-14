package ru.vadim.hostel.consumers.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import ru.vadim.hostel.configuration.JMSConfig;
import ru.vadim.hostel.consumers.events.DeleteUtils.DeleteEvent;
import ru.vadim.hostel.consumers.events.SaveUtils.SaveEvent;

import javax.jms.Queue;
import javax.jms.Topic;


public class Sender {

    public static void topicSender(String id, DeleteEvent deleteEvent, JmsTemplate jmsTemplate) {
        Topic topic = new JMSConfig().deleteTopic();
        deleteEvent.setNumber(id);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(deleteEvent);
            jmsTemplate.convertAndSend("deleteTopic", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void queueSender(String object, SaveEvent saveEvent, JmsTemplate jmsTemplate) {
        Queue queue = new JMSConfig().savedQueue();
        saveEvent.setObject(object);
        try{
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(saveEvent);
            jmsTemplate.convertAndSend("saveQueue", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
