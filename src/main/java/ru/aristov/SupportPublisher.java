package ru.aristov;

import org.springframework.stereotype.Component;

@Component
public class SupportPublisher implements Publisher{
    private final SupportMessageQueue queue;

    public SupportPublisher(SupportMessageQueue queue) {
        this.queue = queue;
    }

    public String publishMessage(String message) {
        return queue.publish(message);
    }
}
