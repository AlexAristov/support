package ru.aristov.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import ru.aristov.consumers.KafkaClient;
import ru.aristov.consumers.SupportPhraseConsumer;
import ru.aristov.consumers.SupportPhraseConsumerImpl;
import ru.aristov.models.SupportPhrase;
import ru.aristov.repositories.SupportRepository;

import java.util.Map;
import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
@ConditionalOnProperty(prefix = "kafka-broker", name = "enabled", havingValue = "true")
public class ConsumerConfig {
    public final String topicName;

    public ConsumerConfig(@Value("${application.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ConsumerFactory<String, SupportPhrase> consumerFactory(
            KafkaProperties kafkaProperties,
            ObjectMapper objectMapper
    ) {
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TYPE_MAPPINGS, "ru.aristov.models.SupportPhrase:ru.aristov.models.SupportPhrase");
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3000);

        DefaultKafkaConsumerFactory<String, SupportPhrase> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(properties);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(objectMapper));
        return kafkaConsumerFactory;
    }

    @Bean("listenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SupportPhrase>>
    listenerContainerFactory(ConsumerFactory<String, SupportPhrase> consumerFactory) {
        int concurrency = 1;
        var factory = new ConcurrentKafkaListenerContainerFactory<String, SupportPhrase>();
        factory.setConsumerFactory(consumerFactory);
        // получить пачку сообщений
        factory.setBatchListener(true);

        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setIdleBetweenPolls(1000);
        factory.getContainerProperties().setPollTimeout(1000);

        var executor = Executors.newFixedThreadPool(concurrency, task -> new Thread(task, "kafka-task"));
        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);
        return factory;
    }

    @Bean
    public SupportPhraseConsumer stringValueConsumerLogger(SupportRepository supportRepository) {
        return new SupportPhraseConsumerImpl(supportRepository);
    }

    @Bean
    public KafkaClient kafkaClient(SupportPhraseConsumer stringValueConsumer) {
        return new KafkaClient(stringValueConsumer);
    }
}
