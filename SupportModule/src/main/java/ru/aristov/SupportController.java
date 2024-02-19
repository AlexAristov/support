package ru.aristov;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SupportController {
    private final Publisher publisher;
    private final SupportService supportService;

    @GetMapping(path = "/support")
    public SupportPhrase getSupportPhrase () {
        return supportService.getSupportPhrase();
    }

    @PostMapping(path = "/support")
    public SupportPhrase addSupportPhrase (SupportPhrase supportPhrase) {
        return new SupportPhrase(publisher.publishMessage(supportPhrase.phrase()));
    }
}
