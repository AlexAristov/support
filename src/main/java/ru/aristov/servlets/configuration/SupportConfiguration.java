package ru.aristov.servlets.configuration;

import ru.aristov.servlets.*;

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
}
