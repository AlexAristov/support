package ru.aristov;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

public class KafkaClient {
    private final StringValueConsumer stringValueConsumer;

    public KafkaClient(StringValueConsumer stringValueConsumer) {
        this.stringValueConsumer = stringValueConsumer;
    }

    @KafkaListener(
            topics = "${application.kafka.topic}",
            containerFactory = "listenerContainerFactory"
    )
    public void listen(@Payload List<SupportPhrase> values) {
        System.out.println("==> values: " + values);
        stringValueConsumer.accept(values);
    }
}
