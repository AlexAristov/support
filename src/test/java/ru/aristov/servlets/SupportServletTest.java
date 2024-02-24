package ru.aristov.servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.aristov.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SupportServletTest {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));

    @BeforeEach
    void setUp() {

    }
    @Mock
    private SupportMessageQueue queue = new SupportMessageQueue();
    @Mock
    private Publisher publisher = new SupportPublisher(queue);
    @Mock
    private SupportService supportService = new SupportService();

    @Mock
    private SupportController supportController = new SupportController(publisher, supportService);

    @Test
    void doGet() throws IOException {
        SupportPhrase supportPhrase = supportController.getSupportPhrase();
        assertTrue(phrases.containsValue(supportPhrase.phrase()));
    }

    @Test
    void doPostWithPhrase() throws IOException {
        String phrase = "Ты все сможешь";
        SupportPhrase supportPhrase = supportController.addSupportPhrase(new SupportPhrase(phrase));
        assertEquals(phrase, supportPhrase.phrase());
    }
}