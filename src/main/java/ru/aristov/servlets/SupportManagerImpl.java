package ru.aristov.servlets;

@Logged
public class SupportManagerImpl implements SupportManager{

    private final SupportService supportService;

    public SupportManagerImpl(SupportService supportService) {
        this.supportService = supportService;
    }

    public String provideSupport() {
        return supportService.getPhrase();
    }

    public String addSupportPhrase(String phrase) {
        return supportService.setPhrase(phrase);
    }
}
