package ru.aristov;

import java.util.Collection;
import java.util.List;

public class StringValueConsumerLogger implements StringValueConsumer {
    private final SupportRepository supportRepository;

    public StringValueConsumerLogger(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    @Override
    public void accept(List<SupportPhrase> values) {
        Collection<String> phrases = supportRepository.getAllSupportPhrase().values();
        for (var value : values) {
            if (!phrases.contains(value.phrase())) {
                System.out.println("==> value: " + value);
                supportRepository.addSupportPhrase(value);
            } else {
                System.out.println("==> already exist: " + value);
            }
        }
    }
}
