package ru.aristov;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SupportMessageQueue implements MessageQueue {
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public String publish(String message) {
        queue.add(message);
        return message;
    }

    public String poll() {
        return queue.poll();
    }
}
