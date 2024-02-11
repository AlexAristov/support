package ru.aristov.servlets;

public class LoggingSupportManager implements SupportManager {
    private final SupportManager supportManager;
    public LoggingSupportManager(SupportManager supportManager) {
        this.supportManager = supportManager;
    }

    @Override
    public String provideSupport() {
        System.out.println("Начало метода");
        String phrase = supportManager.provideSupport();
        System.out.println("Конец метода");
        return phrase;
    }

    @Override
    public String addSupportPhrase(String phrase) {
        return null;
    }
}
