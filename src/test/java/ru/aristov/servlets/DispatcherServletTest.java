package ru.aristov.servlets;

import org.junit.jupiter.api.Test;
import ru.aristov.servlets.services.managers.SupportManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

public class DispatcherServletTest {
    private StringWriter stringWriter = new StringWriter();
    private PrintWriter printWriter = new PrintWriter(stringWriter);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private DispatcherServlet dispatcherServlet = new DispatcherServlet();
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "{\"phrase\":\"Все будет отлично\"}",
            "2", "{\"phrase\":\"Никогда не сдавайся\"}",
            "3", "{\"phrase\":\"Держись\"}"
    ));
    @Test
    public void getSupportPhrase () throws IOException {
        when(request.getPathInfo()).thenReturn("/support");
        when(request.getMethod()).thenReturn("GET");

        when(response.getWriter()).thenReturn(printWriter);

        dispatcherServlet.init();

        dispatcherServlet.doGet(request, response);

        assertTrue(phrases.containsValue(stringWriter.toString()));
    }

    @Test
    public void setNewPhrase () throws IOException {
        final var requestContent = "{\"phrase\":\"Ты сможешь\"}";

        when(request.getPathInfo()).thenReturn("/support");
        when(request.getMethod()).thenReturn("POST");

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestContent)));

        dispatcherServlet.init();

        dispatcherServlet.doPost(request, response);
        assertEquals(requestContent, stringWriter.toString());
    }

    @Test
    public void supportServletShouldReturnSupportPhrase () throws InvocationTargetException, IllegalAccessException {
        ApplicationContext context = new ApplicationContext("ru.aristov.servlets");
        SupportManager supportService = context.getInstance(SupportManager.class);
        assertTrue(phrases.containsValue(supportService.provideSupport()));
    }
}
