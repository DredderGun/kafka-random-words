package dev.avyguzov.randomwordsproducer.domain;

public class RandomWordMessage {
    private String word;

    public RandomWordMessage(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
