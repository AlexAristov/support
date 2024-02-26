package ru.aristov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class SupportServiceImpl implements SupportService {
    @Autowired
    SupportRepository phrases;

    public SupportPhrase getSupportPhrase() {
        if (phrases.getAllSupportPhrase().isEmpty()) {
            return new SupportPhrase("Sorry! Нет слов поддержки");
        }
        return new SupportPhrase(phrases.getSupportPhrase());
    };
    public Map<String, String> getAllSupportPhrase() {
        return phrases.getAllSupportPhrase();
    };
    public void addSupportPhrase(SupportPhrase supportPhrase) {
        if (!isPhraseEmpty(supportPhrase)) {
            phrases.addSupportPhrase(supportPhrase);
        }
    }

    private boolean isPhraseEmpty (SupportPhrase supportPhrase) {
        return supportPhrase == null || supportPhrase.phrase().isEmpty();
    }
}
