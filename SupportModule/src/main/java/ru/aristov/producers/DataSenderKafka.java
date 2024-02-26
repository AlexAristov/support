package ru.aristov.producers;

import org.springframework.kafka.core.KafkaTemplate;
import ru.aristov.models.SupportPhrase;

public class DataSenderKafka implements DataSender {
    private final KafkaTemplate<String, SupportPhrase> kafkaTemplate;
    private final String topic;

    public DataSenderKafka(
            String topic,
            KafkaTemplate<String, SupportPhrase> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void send(SupportPhrase supportPhrase) {
        kafkaTemplate.send(topic, supportPhrase)
            .whenComplete(
                (result, ex) -> {
                    if (ex != null) {
                        System.out.println("==> message was NOT send: " +  ex);
                    }
                }
            );
    }
}
