package ru.aristov;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Random;


public record SupportPhrase (
        String phrase
) {

}

//@Getter
//public class SupportPhrase {
//    @Getter(AccessLevel.PRIVATE)
//    private final Random random;
//
//    private final int result;
//
//    public SupportPhrase() {
//        this.random = new Random();
//        this.result = random.nextInt();
//    }
//}
