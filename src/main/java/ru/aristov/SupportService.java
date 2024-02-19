package ru.aristov;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class SupportService {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));

    public SupportPhrase getSupportPhrase() {
        if (phrases.isEmpty()) {
            return new SupportPhrase("Sorry! Нет слов поддержки");
        }
        List<String> keys = new ArrayList<>(phrases.keySet());
        Random random = new Random();
        int index = random.nextInt(keys.size());
        return new SupportPhrase(phrases.get(keys.get(index)));
    };
    public void addSupportPhrase(SupportPhrase supportPhrase) {
        if (!isPhraseEmpty(supportPhrase)) {
            Date date = new Date();
            phrases.put(date.toString(), supportPhrase.phrase());
        }
    }

    private boolean isPhraseEmpty (SupportPhrase supportPhrase) {
        return supportPhrase == null || supportPhrase.phrase().isEmpty();
    }
}
