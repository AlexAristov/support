package ru.aristov.consumers;

import ru.aristov.models.SupportPhrase;

import java.util.List;

public interface SupportPhraseConsumer {
    void accept(List<SupportPhrase> values);
}
