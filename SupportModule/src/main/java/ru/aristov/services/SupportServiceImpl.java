package ru.aristov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aristov.models.SupportPhrase;
import ru.aristov.repositories.SupportRepository;

import java.util.*;
@Component
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
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
