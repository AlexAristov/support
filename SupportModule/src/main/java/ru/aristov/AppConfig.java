package ru.aristov;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnProperty(prefix = "kafka-broker", name = "enabled", havingValue = "true")
public class AppConfig {
    @Bean
    @Primary
    public SupportService supportService(SupportRepository supportRepository, DataSender dataSender) {
        return new SupportServiceKafkaImpl(supportRepository, dataSender);
    }
}
