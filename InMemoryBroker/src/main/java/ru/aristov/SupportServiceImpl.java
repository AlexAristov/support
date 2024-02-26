package ru.aristov;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupportServiceImpl implements SupportService {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));

    public SupportPhrase2 getSupportPhrase() {
        if (phrases.isEmpty()) {
            return new SupportPhrase2("Sorry! Нет слов поддержки");
        }
        List<String> keys = new ArrayList<>(phrases.keySet());
        Random random = new Random();
        int index = random.nextInt(keys.size());
        return new SupportPhrase2(phrases.get(keys.get(index)));
    };
    public void addSupportPhrase(SupportPhrase2 supportPhrase) {
        if (!isPhraseEmpty(supportPhrase)) {
            Date date = new Date();
            phrases.put(date.toString(), supportPhrase.phrase());
        }
    }

    private boolean isPhraseEmpty (SupportPhrase2 supportPhrase) {
        return supportPhrase == null || supportPhrase.phrase().isEmpty();
    }
}
