package ru.aristov.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class SupportServlet extends HttpServlet {
    private ApplicationContext context;
    private SupportManager supportManager = new ApplicationContext().getInstance(SupportManager.class);
    private SupportController supportControllers = new ApplicationContext().getInstance(SupportController.class);

    public SupportServlet() throws InvocationTargetException, IllegalAccessException {
    }

    @Override
    public void init() {
        // this method don't run
        try {
            ApplicationContext context = new ApplicationContext();
            supportManager = context.getInstance(SupportManagerImpl.class);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        String message = supportManager.provideSupport();
        writer.write(message);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        String phrase = request.getParameter("phrase");
        String addPhrase = supportManager.addSupportPhrase(phrase);
        if (!this.isPhraseEmpty(addPhrase)) {
            writer.write(addPhrase + " - фраза добавлена");
        } else {
            writer.write("Ничего не добавлено!");
        }
    }

    private boolean isPhraseEmpty (String phrase) {
        return phrase == null || phrase.isEmpty();
    }
}
