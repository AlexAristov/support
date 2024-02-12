package ru.aristov.servlets;

public class SupportController implements Controller{

//    @PostMapping("/support")
//    public void addSupportPhrase (SupportPhraseRequest request) {
//
//    }


    @Override
    public String path() {
        return null;
    }

    @Override
    public ControllerMethod getControllerMethod() {
        return ControllerMethod.GET;
    }
}
