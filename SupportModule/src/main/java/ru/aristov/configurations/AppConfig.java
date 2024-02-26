package ru.aristov.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.aristov.producers.DataSender;
import ru.aristov.repositories.SupportRepository;
import ru.aristov.services.SupportService;
import ru.aristov.services.SupportServiceKafkaImpl;

@Configuration
@ConditionalOnProperty(prefix = "kafka-broker", name = "enabled", havingValue = "true")
public class AppConfig {
    @Bean
    @Primary
    public SupportService supportService(SupportRepository supportRepository, DataSender dataSender) {
        return new SupportServiceKafkaImpl(supportRepository, dataSender);
    }
}
