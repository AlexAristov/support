package ru.aristov.servlets.services.managers;


import ru.aristov.servlets.services.supportService.SupportPhrase;
import ru.aristov.servlets.services.supportService.SupportService;

public class SupportManagerImpl implements SupportManager{

    private final SupportService supportService;

    public SupportManagerImpl(SupportService supportService) {
        this.supportService = supportService;
    }

    public SupportPhrase provideSupport() {
        return supportService.getPhrase();
    }

    public SupportPhrase addSupportPhrase(SupportPhrase phrase) {
        return supportService.setPhrase(phrase);
    }
}
