package ru.aristov.consumers;

import ru.aristov.models.SupportPhrase;
import ru.aristov.repositories.SupportRepository;

import java.util.Collection;
import java.util.List;

public class SupportPhraseConsumerImpl implements SupportPhraseConsumer {
    private final SupportRepository supportRepository;

    public SupportPhraseConsumerImpl(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    @Override
    public void accept(List<SupportPhrase> values) {
        Collection<String> phrases = supportRepository.getAllSupportPhrase().values();
        for (var value : values) {
            if (!phrases.contains(value.phrase())) {
                supportRepository.addSupportPhrase(value);
            } else {
                System.out.println("==> already exist: " + value);
            }
        }
    }
}
