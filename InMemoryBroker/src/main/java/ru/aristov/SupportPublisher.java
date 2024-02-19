package ru.aristov;

public class SupportPublisher implements Publisher{
    private final MessageQueue queue;

    public SupportPublisher(MessageQueue queue) {
        this.queue = queue;
    }

    public String publishMessage(String message) {
        return queue.publish(message);
    }
}
