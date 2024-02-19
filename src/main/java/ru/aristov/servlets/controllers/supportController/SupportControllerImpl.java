package ru.aristov.servlets.controllers.supportController;

import ru.aristov.servlets.services.managers.SupportManager;
import ru.aristov.servlets.services.supportService.SupportPhrase;

public class SupportControllerImpl implements SupportController {
    private SupportManager supportManager;

    public SupportControllerImpl(SupportManager supportManager) {
        this.supportManager = supportManager;
    }

    @Override
    public SupportPhrase getSupportPhrase() {
        return supportManager.provideSupport();
    }

    @Override
    public SupportPhrase writeSupportPhrase(SupportPhrase supportPhrase) {
        return supportManager.addSupportPhrase(supportPhrase);
    }
}
