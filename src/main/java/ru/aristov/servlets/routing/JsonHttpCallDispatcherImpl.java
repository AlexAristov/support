package ru.aristov.servlets.routing;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aristov.servlets.routing.CommonMappingProviderImpl.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

public class JsonHttpCallDispatcherImpl implements HttpCallDispatcher {
    private ObjectMapper objectMapper;

    public JsonHttpCallDispatcherImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void dispatch(Handler handler, HttpServletRequest request, HttpServletResponse response) {
        Object result;
        response.setContentType("application/json");
        try {
            if (handler.method().getParameters().length == 0) {
                result = handler.method().invoke(handler.target());
                response.getWriter().append(objectMapper.writeValueAsString(result));
            } else {
                final var parameterType = handler.method().getParameters()[0].getType();
                String content = request.getReader().lines().collect(Collectors.joining());
                result = handler.method().invoke(handler.target(), objectMapper.readValue(content, parameterType));
                response.getWriter().append(objectMapper.writeValueAsString(result));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
