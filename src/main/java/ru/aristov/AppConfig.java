package ru.aristov;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Bean
    public SupportService supportService() {
        return new SupportService();
    }

//    @Bean
//    public MessageQueue messageQueue() {
//        return new MessageQueue();
//    }
}
