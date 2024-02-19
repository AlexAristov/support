package ru.aristov;

public interface MessageQueue {
    String publish(String message);
    String poll();
}
