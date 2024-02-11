package ru.aristov.servlets;

public interface SupportService {
    @Logged
    String getPhrase ();
    String setPhrase (String phrase);
}
