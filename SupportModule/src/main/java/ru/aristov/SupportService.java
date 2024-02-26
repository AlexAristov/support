package ru.aristov;

import java.util.Map;

public interface SupportService {
    SupportPhrase getSupportPhrase();

    void addSupportPhrase(SupportPhrase supportPhrase);

    Map<String, String> getAllSupportPhrase();
}
