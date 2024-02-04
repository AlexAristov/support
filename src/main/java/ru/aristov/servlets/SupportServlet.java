package ru.aristov.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupportServlet extends HttpServlet {
    private final Map<String, String> phrases = new ConcurrentHashMap<>(Map.of(
            "1", "Все будет отлично",
            "2", "Никогда не сдавайся",
            "3", "Держись"
    ));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        String message = getPhrase();
        writer.write(message);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        String phrase = request.getParameter("phrase");
        String addPhrase = setPhrase(phrase);
        if (!isPhraseEmpty(addPhrase)) {
            writer.write(addPhrase + " - фраза добавлена");
        } else {
            writer.write("Ничего не добавлено!");
        }
    }

    private String getPhrase() {
        if (phrases.isEmpty()) {
            return "Sorry! Нет слов поддержки";
        }
        List<String> keys = new ArrayList<>(phrases.keySet());
        Random random = new Random();
        int index = random.nextInt(keys.size());
        return phrases.get(keys.get(index));
    }

    private String setPhrase(String phrase) {
        if (!isPhraseEmpty(phrase)) {
            Date date = new Date();
            phrases.put(date.toString(), phrase);
        }
        return phrase;
    }

    private boolean isPhraseEmpty (String phrase) {
        return phrase == null || phrase.isEmpty();
    }
}
