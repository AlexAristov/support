package ru.aristov;

import org.springframework.kafka.core.KafkaTemplate;

import java.util.function.Consumer;

public class DataSenderKafka implements DataSender {
    private final KafkaTemplate<String, SupportPhrase> kafkaTemplate;
    private final Consumer<SupportPhrase> phraseConsumer;
    private final String topic;

    public DataSenderKafka(String topic, KafkaTemplate<String, SupportPhrase> kafkaTemplate, Consumer<SupportPhrase> phraseConsumer) {
        this.kafkaTemplate = kafkaTemplate;
        this.phraseConsumer = phraseConsumer;
        this.topic = topic;
    }

    @Override
    public void send(SupportPhrase supportPhrase) {
        kafkaTemplate.send(topic, supportPhrase)
                .whenComplete(
                        (result, ex) -> {
                            if (ex == null) {
                                // extra logic
                                // System.out.println("==> message was send:" + supportPhrase.phrase() + " == " + result.getRecordMetadata().offset());
                            } else {
                                System.out.println("==> message was NOT send: " +  ex);
                            }
                        }
                );
    }
}
