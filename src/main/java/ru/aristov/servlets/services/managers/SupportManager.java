package ru.aristov.servlets.services.managers;

import ru.aristov.servlets.services.supportService.SupportPhrase;

public interface SupportManager {
    SupportPhrase provideSupport();
    SupportPhrase addSupportPhrase(SupportPhrase phrase);
}
