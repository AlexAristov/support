package ru.aristov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SupportService supportService() {
        return new SupportServiceImpl();
    }

    @Bean
    public MessageQueue messageQueue() {
        return new SupportMessageQueue();
    }

    @Bean
    public Publisher supportPublisher(MessageQueue messageQueue) {
        return new SupportPublisher(messageQueue);
    }

    @Bean
    public SubscriberAnnotationBeenPostProcessor subscriberAnnotationBeenPostProcessor(){
        return new SubscriberAnnotationBeenPostProcessor();
    }

    @Bean
    public SupportSubscriber supportSubscriber(MessageQueue queue, SupportService supportService) {
        return new SupportSubscriber(queue, supportService);
    }
}
