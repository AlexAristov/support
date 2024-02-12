package ru.aristov.servlets;

public interface Controller {
    String path ();
    ControllerMethod getControllerMethod ();
}
