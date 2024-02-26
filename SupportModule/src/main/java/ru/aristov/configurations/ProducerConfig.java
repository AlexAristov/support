package ru.aristov.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.aristov.DataSender;
import ru.aristov.DataSenderKafka;
import ru.aristov.KafkaConfigurationProperties;
import ru.aristov.SupportPhrase;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
@ConditionalOnProperty(prefix = "kafka-broker", name = "enabled", havingValue = "true")
public class ProducerConfig {
    public final String topicName;

    public ProducerConfig(@Value("${application.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, SupportPhrase> producerFactory(
            KafkaProperties kafkaProperties,
            ObjectMapper objectMapper
    ) {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<String, SupportPhrase> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(properties);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, SupportPhrase> kafkaTemplate(
            ProducerFactory<String, SupportPhrase> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }

    @Bean
    public DataSender dataSender(
            NewTopic topic,
            KafkaTemplate<String, SupportPhrase> kafkaTemplate
    ) {
        return new DataSenderKafka(
                topic.name(),
                kafkaTemplate,
                supportPhrase -> System.out.println("==> !!!" + supportPhrase)
        );
    }
}
