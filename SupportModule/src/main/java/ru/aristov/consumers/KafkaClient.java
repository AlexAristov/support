package ru.aristov.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import ru.aristov.models.SupportPhrase;

import java.util.List;

public class KafkaClient {
    private final SupportPhraseConsumer stringValueConsumer;

    public KafkaClient(SupportPhraseConsumer stringValueConsumer) {
        this.stringValueConsumer = stringValueConsumer;
    }

    @KafkaListener(
            topics = "${application.kafka.topic}",
            containerFactory = "listenerContainerFactory"
    )
    public void listen(@Payload List<SupportPhrase> values) {
        stringValueConsumer.accept(values);
    }
}
