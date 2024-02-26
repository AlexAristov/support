package ru.aristov;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SupportController {
    private final SupportService supportService;
//    private final DataSender dataSenderKafka;

    @GetMapping(path = "/support")
    public SupportPhrase getSupportPhrase () {
        return supportService.getSupportPhrase();
    }

    @GetMapping(path = "/supportAll")
    public Map getAllSupportPhrase () {
        return supportService.getAllSupportPhrase();
    }

    @PostMapping(path = "/support")
    public SupportPhrase addSupportPhrase (SupportPhrase supportPhrase) {
//        dataSenderKafka.send(supportPhrase);
        supportService.addSupportPhrase(supportPhrase);
        return supportPhrase;
    }
}
