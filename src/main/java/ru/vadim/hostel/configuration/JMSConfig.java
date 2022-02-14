package ru.vadim.hostel.configuration;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
@EnableJms
public class JMSConfig {
    @Bean
    public Topic topic() {
        return new ActiveMQTopic("bridgingcode-topic");
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("bridgingcode-queue");
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory =
                new DefaultJmsListenerContainerFactory();

        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        defaultJmsListenerContainerFactory.setConcurrency("5-10");
        return defaultJmsListenerContainerFactory;
    }
}
