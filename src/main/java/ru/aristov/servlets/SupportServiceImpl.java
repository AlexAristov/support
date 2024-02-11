package ru.aristov.servlets;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupportServiceImpl implements SupportService {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));

    @Override
    public String getPhrase() {
        if (phrases.isEmpty()) {
            return "Sorry! Нет слов поддержки";
        }
        List<String> keys = new ArrayList<>(phrases.keySet());
        Random random = new Random();
        int index = random.nextInt(keys.size());
        return phrases.get(keys.get(index));
    }

    @Override
    public String setPhrase(String phrase) {
        if (!isPhraseEmpty(phrase)) {
            Date date = new Date();
            phrases.put(date.toString(), phrase);
        }
        return phrase;
    }

    private boolean isPhraseEmpty (String phrase) {
        return phrase == null || phrase.isEmpty();
    }
}
