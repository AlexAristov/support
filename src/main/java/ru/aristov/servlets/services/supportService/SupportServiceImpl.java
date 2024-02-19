package ru.aristov.servlets.services.supportService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupportServiceImpl implements SupportService {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));

    @Override
    public SupportPhrase getPhrase() {
        if (phrases.isEmpty()) {
            return new SupportPhrase("Sorry! Нет слов поддержки");
        }
        List<String> keys = new ArrayList<>(phrases.keySet());
        Random random = new Random();
        int index = random.nextInt(keys.size());
        return new SupportPhrase(phrases.get(keys.get(index)));
    }

    @Override
    public SupportPhrase setPhrase(SupportPhrase supportPhrase) {
        if (!isPhraseEmpty(supportPhrase)) {
            Date date = new Date();
            phrases.put(date.toString(), supportPhrase.phrase().toString());
        }
        return supportPhrase;
    }

    private boolean isPhraseEmpty (SupportPhrase supportPhrase) {
        return supportPhrase == null || supportPhrase.phrase().isEmpty();
    }
}
