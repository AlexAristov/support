package ru.aristov.servlets.controllers.supportController;

import ru.aristov.servlets.controllers.Controller;
import ru.aristov.servlets.controllers.HttpMethod;
import ru.aristov.servlets.controllers.Logged;
import ru.aristov.servlets.controllers.RequestMapping;
import ru.aristov.servlets.services.supportService.SupportPhrase;

@Controller
public interface SupportController {
    @Logged
    @RequestMapping(method = HttpMethod.GET, path = "/support")
    SupportPhrase getSupportPhrase();

    @Logged
    @RequestMapping(method = HttpMethod.POST, path = "/support")
    SupportPhrase writeSupportPhrase(SupportPhrase supportPhrase);
}
