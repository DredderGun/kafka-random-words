package dev.avyguzov.randomwordsproducer.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WordsGenerator {
    public String generateWord() {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        long stringLength = Math.round(Math.random() * 100);

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
