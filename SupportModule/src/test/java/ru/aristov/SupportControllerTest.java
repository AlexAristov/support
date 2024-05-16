package ru.aristov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.aristov.configurations.AppConfig;
import ru.aristov.consumers.KafkaClient;
import ru.aristov.controllers.SupportController;
import ru.aristov.models.SupportPhrase;
import ru.aristov.repositories.SupportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(AppConfig.class)
class SupportControllerTest {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));
    @Autowired
    private KafkaClient kafkaClient;
    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private SupportController supportController;

    @Test
    void getSupportPhrase() {
        SupportPhrase supportPhrase = supportController.getSupportPhrase();
        assertTrue(phrases.containsValue(supportPhrase.phrase()));
    }

    @Test
    void addSupportPhrase() {
        String phrase = "Ты все сможешь";
        supportController.addSupportPhrase(new SupportPhrase(phrase));
        List<SupportPhrase> list = new ArrayList<>();
        list.add(new SupportPhrase(phrase));
        kafkaClient.listen(list);
        assertTrue(supportRepository.getAllSupportPhrase().containsValue(phrase));
    }
}