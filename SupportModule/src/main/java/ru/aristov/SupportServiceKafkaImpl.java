package ru.aristov;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class SupportServiceKafkaImpl implements SupportService {

    SupportRepository phrases;
    DataSender dataSenderKafka;

    public SupportServiceKafkaImpl(SupportRepository phrases, DataSender dataSenderKafka) {
        this.phrases = phrases;
        this.dataSenderKafka = dataSenderKafka;
    }

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
//            phrases.addSupportPhrase(supportPhrase);
            dataSenderKafka.send(supportPhrase);
        }
    }

    private boolean isPhraseEmpty (SupportPhrase supportPhrase) {
        return supportPhrase == null || supportPhrase.phrase().isEmpty();
    }
}
