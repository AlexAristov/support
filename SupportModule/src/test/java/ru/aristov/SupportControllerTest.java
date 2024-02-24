package ru.aristov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

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
    private MessageQueue queue;
    @Autowired
    private Publisher publisher;
    @Autowired
    private SupportService supportService;
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
        SupportPhrase supportPhrase = supportController.addSupportPhrase(new SupportPhrase(phrase));
        assertEquals(phrase, supportPhrase.phrase());
    }
}