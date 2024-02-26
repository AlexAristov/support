package ru.aristov.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("kafka-broker")
public record KafkaConfigurationProperties(
        boolean enabled
) {
}
