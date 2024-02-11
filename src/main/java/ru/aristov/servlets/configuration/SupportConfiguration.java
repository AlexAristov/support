package ru.aristov.servlets.configuration;

import ru.aristov.servlets.SupportManager;
import ru.aristov.servlets.SupportManagerImpl;
import ru.aristov.servlets.SupportService;
import ru.aristov.servlets.SupportServiceImpl;

@Configuration
public class SupportConfiguration {
    @Instance
    public SupportManager supportManager () {
        return new SupportManagerImpl(supportService());
    }

    @Instance
    public SupportService supportService () {
        return new SupportServiceImpl();
    }
}
