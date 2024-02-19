package ru.aristov.servlets.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aristov.servlets.proxies.ProxyApplier;
import ru.aristov.servlets.proxies.loggingProxy.LoggingProxyApplierImpl;
import ru.aristov.servlets.controllers.supportController.SupportController;
import ru.aristov.servlets.controllers.supportController.SupportControllerImpl;
import ru.aristov.servlets.routing.HttpCallDispatcher;
import ru.aristov.servlets.routing.JsonHttpCallDispatcherImpl;
import ru.aristov.servlets.services.managers.SupportManager;
import ru.aristov.servlets.services.managers.SupportManagerImpl;
import ru.aristov.servlets.services.supportService.SupportService;
import ru.aristov.servlets.services.supportService.SupportServiceImpl;

@Configuration
public class SupportConfiguration {

    @Instance(priority = Integer.MAX_VALUE)
    public ProxyApplier controllerProxyApplier () {
        return new LoggingProxyApplierImpl();
    }

    @Instance
    public SupportManager supportManager (SupportService supportService) {
        return new SupportManagerImpl(supportService);
    }

    @Instance
    public SupportService supportService () {
        return new SupportServiceImpl();
    }

    @Instance
    public ObjectMapper objectMapper () {
        return new ObjectMapper();
    }

    @Instance
    public HttpCallDispatcher httpCallDispatcher (ObjectMapper objectMapper) {
        return new JsonHttpCallDispatcherImpl(objectMapper);
    }

    @Instance
    public SupportController supportController (SupportManager supportManager) {
        return new SupportControllerImpl(supportManager);
    }
}
