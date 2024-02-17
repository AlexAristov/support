package ru.aristov.servlets;

import ru.aristov.servlets.routing.CommonMappingProviderImpl;
import ru.aristov.servlets.routing.HttpCallDispatcher;
import ru.aristov.servlets.routing.MappingProvider;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class DispatcherServlet extends HttpServlet {
    private ApplicationContext context;
    private MappingProvider mappingProvider;
    private HttpCallDispatcher httpCallDispatcher;


    @Override
    public void init() {
        try {
            ApplicationContext context = new ApplicationContext("ru.aristov.servlets");
            mappingProvider = new CommonMappingProviderImpl(context);
            httpCallDispatcher = context.getInstance(HttpCallDispatcher.class);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        invokeMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        invokeMethod(request, response);
    }

    private void invokeMethod(HttpServletRequest request, HttpServletResponse response) {
        final var handler = mappingProvider.getMapping(request);
        httpCallDispatcher.dispatch(handler, request, response);
    }
}
