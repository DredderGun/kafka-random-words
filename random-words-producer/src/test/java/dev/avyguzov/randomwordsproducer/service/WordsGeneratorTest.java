package dev.avyguzov.randomwordsproducer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordsGeneratorTest {
    private WordsGenerator wordsGenerator;

    @BeforeEach
    public void init() {
        wordsGenerator = new WordsGenerator();
    }

    @Test
    public void mustGenerateOnlyLetters() {
        for (int i = 0; i < 500; i++) {
            String word = wordsGenerator.generateWord();
            Assertions.assertTrue(word.length() <= 100);
            Assertions.assertTrue(word.matches("[A-Za-z]*"));
        }
    }
}
