package ru.aristov.servlets.routing;

import ru.aristov.servlets.routing.CommonMappingProviderImpl.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpCallDispatcher {
    void dispatch(Handler handler, HttpServletRequest request, HttpServletResponse response);
}
