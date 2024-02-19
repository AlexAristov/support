package ru.aristov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupportSubscriber implements Runnable{
    @Autowired
    private SupportMessageQueue queue;
    @Autowired
    private  SupportService supportService;

    @Subscriber
    @Override
    public void run() {
        while (true) {
            if (queue == null) {
                try {
                    System.out.println("==> Queue is not defined");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String message = queue.poll();
                if (message != null) {
                    supportService.addSupportPhrase(new SupportPhrase(message));
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
