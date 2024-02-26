package ru.aristov;

public class SupportSubscriber implements Runnable{
    private final MessageQueue queue;
    private final SupportService supportService;

    public SupportSubscriber(MessageQueue queue, SupportService supportService) {
        this.queue = queue;
        this.supportService = supportService;
    }

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
                    supportService.addSupportPhrase(new SupportPhrase2(message));
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
