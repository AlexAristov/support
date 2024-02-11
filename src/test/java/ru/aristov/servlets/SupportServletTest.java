package ru.aristov.servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @BeforeEach
    void setUp() {

    }

    @Mock
    private SupportServlet supportServlet = new SupportServlet();

    @Test
    void doGet() throws IOException {
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        supportServlet.doGet(request, response);
        assertTrue(phrases.containsValue(sw.toString()));
    }

    @Test
    void doPostWithPhrase() throws IOException {
        String phrase = "Ты все сможешь";
        when(request.getParameter("phrase")).thenReturn(phrase);

        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        supportServlet.doPost(request, response);
        assertEquals(sw.toString(), phrase + " - фраза добавлена");
    }

    @Test
    void doPostWithoutPhrase() throws IOException {
        String phrase = "";
        when(request.getParameter("phrase")).thenReturn(phrase);

        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        supportServlet.doPost(request, response);
        assertEquals("Ничего не добавлено!", sw.toString());
    }
}