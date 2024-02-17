package ru.aristov.servlets.routing;

import ru.aristov.servlets.routing.CommonMappingProviderImpl.Handler;
import javax.servlet.http.HttpServletRequest;

public interface MappingProvider {
    Handler getMapping(HttpServletRequest request);
}
