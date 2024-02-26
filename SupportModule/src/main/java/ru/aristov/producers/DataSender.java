package ru.aristov.producers;

import ru.aristov.models.SupportPhrase;

public interface DataSender {
    void send(SupportPhrase supportPhrase);
}
