package ru.aristov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aristov.models.SupportPhrase;
import ru.aristov.services.SupportService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SupportController {
    private final SupportService supportService;

    @GetMapping(path = "/support")
    public SupportPhrase getSupportPhrase () {
        return supportService.getSupportPhrase();
    }

    @GetMapping(path = "/supportAll")
    public Map getAllSupportPhrase () {
        return supportService.getAllSupportPhrase();
    }

    @PostMapping(path = "/support")
    public void addSupportPhrase (SupportPhrase supportPhrase) {
        supportService.addSupportPhrase(supportPhrase);
    }
}
