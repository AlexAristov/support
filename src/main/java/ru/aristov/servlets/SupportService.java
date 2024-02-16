package ru.aristov.servlets;

@ControllerAnnotation
public interface SupportService {
    @Logged
    String getPhrase ();
    String setPhrase (String phrase);
}
