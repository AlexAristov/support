package ru.aristov;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SupportRepository {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));

    public String getSupportPhrase() {
        List<String> keys = new ArrayList<>(phrases.keySet());
        Random random = new Random();
        int index = random.nextInt(keys.size());
        return phrases.get(keys.get(index));
    };
    public Map<String, String> getAllSupportPhrase() {
        return phrases;
    };
    public void addSupportPhrase(SupportPhrase supportPhrase) {
        Date date = new Date();
        phrases.put(date.toString(), supportPhrase.phrase());
    }
}
